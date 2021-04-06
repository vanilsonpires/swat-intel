package br.com.test.swatintel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.test.swatintel.entity.interfaces.Route;
import br.com.test.swatintel.service.RouteService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/routes")
public class RouteController {
	
	@Autowired
	private RouteService routeService;
	
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Return list all routes")
		})
	@GetMapping(produces="application/json")
	public ResponseEntity<List<Route>> list() {
		try {
			return new ResponseEntity<List<Route>>(routeService.findAllRoute(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
