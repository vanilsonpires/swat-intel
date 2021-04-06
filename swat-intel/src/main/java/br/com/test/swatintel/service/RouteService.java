package br.com.test.swatintel.service;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.test.swatintel.entity.Travel;
import br.com.test.swatintel.entity.interfaces.Route;
import br.com.test.swatintel.exeptions.NoRouteException;

@Service
public class RouteService {

	@Autowired
	private TrainService trainService;

	@Autowired
	private UberRouteService uberRouteService;
	
	public List<Route> findAllRoute() throws IOException {
		List<Route> routes = trainService.findAll().stream().map(t -> (Route) t).collect(Collectors.toList());
		routes.addAll(uberRouteService.findAll().stream().map(t -> (Route) t).collect(Collectors.toList()));
		Collections.sort(routes,new Comparator<Route>() {
			@Override
			public int compare(Route o1, Route o2) {
				return o1.getExit().compareTo(o2.getExit());
			}
		});
		return routes;
	}
	
	private boolean verify(String stationName, LocalDateTime dateExit, Route route, boolean first) {		
		if(!route.getOrigin().trim().equalsIgnoreCase(stationName.trim()))
			return false;
		if(dateExit == null)
			return true;		
		if(!route.getExit().isAfter(dateExit) && !route.getExit().isEqual(dateExit))
			return false;		
		return first || Duration.between(dateExit, route.getArrival()).toHours() <= 12;
	}
	
	public List<Route> findByStationAndExitRoutes(String stationName, LocalDateTime dateExit) throws IOException {
		return findByStationAndExitRoutes(stationName, dateExit, false);
	}

	public List<Route> findByStationAndExitRoutes(String stationName, LocalDateTime dateExit, boolean first) throws IOException {
		List<Route> routes = trainService.findAll().stream().
				filter(v -> verify(stationName, dateExit, v, first)).
				map(t -> (Route) t).collect(Collectors.toList());
		routes.addAll(uberRouteService.findAll().stream().
				filter(v -> verify(stationName, dateExit, v, first)).
				map(t -> (Route) t).collect(Collectors.toList()));
		return routes;
	}
	
	public List<Travel> calcRoutes(String stationOrigin, String stationDestiny, LocalDate dateExit) throws IOException, NoRouteException {
		List<Travel> travels = new ArrayList<Travel>();
		if(stationOrigin.trim().equalsIgnoreCase(stationDestiny.trim()))
			return new ArrayList<Travel>();
		List<Route> routes = findByStationAndExitRoutes(stationOrigin, dateExit == null ? null : dateExit.atTime(0, 0), true);
		if(routes!=null)
			for(Route route : routes) {
				if(route.getDestiny().trim().equalsIgnoreCase(stationDestiny.trim())) {
					addTravel(new Travel().addRoute(route), travels);
				}else {
					nextPoint(route.getDestiny(), stationDestiny, route.getArrival(), travels, new Travel().addRoute(route));
				}
			}
		Collections.sort(travels,new Comparator<Travel>() {
			@Override
			public int compare(Travel o1, Travel o2) {
				return o1.getArrival().compareTo(o2.getArrival());
			}
		});
		return travels;
	}
	
	private void addTravel(Travel travel, List<Travel> list) {
		travel.setOrigin(travel.getRoutes().get(0).getOrigin());
		travel.setDestiny(travel.getRoutes().get(travel.getRoutes().size()-1).getDestiny());
		travel.setPrice(travel.getRoutes().stream().mapToDouble(t -> t.getPrice()).sum());
		travel.setExit(travel.getRoutes().get(0).getExit());
		travel.setArrival(travel.getRoutes().get(travel.getRoutes().size()-1).getArrival());
		list.add(travel);
	}
	
	private void nextPoint(String origin, String destiny, LocalDateTime exitTime, List<Travel> travels, Travel travel) throws IOException {	
		List<Route> routes = findByStationAndExitRoutes(origin, exitTime);
		if(routes!=null)
			for(Route route : routes) {
				if(route.getDestiny().trim().equalsIgnoreCase(origin))
					continue;
				if(route.getDestiny().trim().equalsIgnoreCase(destiny.trim())) {
					addTravel(travel.addRoute(route), travels);
				}else {
					nextPoint(route.getDestiny(), destiny, route.getArrival(), travels, new Travel(travel.getRoutes()).addRoute(route));
				}
			}
	}
	
}
