package br.com.test.swatintel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.test.swatintel.entity.Station;
import br.com.test.swatintel.service.StationService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/station")
public class StationController {
	
	@Autowired
	private StationService stationService;
	
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Return list all stations")
		})
	@GetMapping(produces="application/json")
	public ResponseEntity<List<Station>> list() {
		try {
			return new ResponseEntity<List<Station>>(stationService.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
