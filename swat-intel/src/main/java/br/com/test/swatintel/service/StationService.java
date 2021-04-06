package br.com.test.swatintel.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.test.swatintel.entity.Station;

@Service
public class StationService {
	
	@Autowired
	private ObjectMapper mapper;
	
	@Value("classpath:data/trainStations.json")
	private Resource trainStationsFile;
	
	@Cacheable("StationService.findAll")
	public List<Station> findAll() throws IOException{
		return mapper.readValue(trainStationsFile.getInputStream(),new TypeReference<List<Station>>() {});
	}

}
