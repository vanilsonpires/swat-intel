package br.com.test.swatintel.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.test.swatintel.entity.interfaces.Route;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public @Data class Travel {
	
	private String origin;
	
	private String destiny;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime exit;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime arrival;
	
	private Double price;
	
	private List<Route> routes;
	
	public Travel(List<Route> routes) {
		this.routes = routes.stream().collect(Collectors.toList());//clonning list
	}
	
	public Travel addRoute(Route route) {
		if(routes == null )
			routes = new ArrayList<Route>();
		routes.add(route);
		return this;
	}

}
