package br.com.test.swatintel.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.test.swatintel.entity.Travel;
import br.com.test.swatintel.exeptions.NoRouteException;
import br.com.test.swatintel.service.RouteService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping
public class TravelController {

	@Autowired
	private RouteService routeService;

	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Returns the list of available routes"),
		    @ApiResponse(code = 400, message = "No route available")
		})
	@GetMapping(produces="application/json")
	public ResponseEntity<List<Travel>> calc(@RequestParam String origin, @RequestParam String destiny,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateExit)
			throws NoRouteException {
		try {
			return new ResponseEntity<List<Travel>>(routeService.calcRoutes(origin, destiny, dateExit), HttpStatus.OK);
		} catch (NoRouteException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
