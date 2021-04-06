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

import br.com.test.swatintel.entity.UberRoute;

@Service
public class UberRouteService {
	
	@Autowired
	private ObjectMapper mapper;
	
	@Value("classpath:data/uberOnRails.json")
	private Resource uberOnRailsFile;
	
	@Cacheable("UberRouteService.findAll")
	public List<UberRoute> findAll() throws IOException{		
		return mapper.readValue(uberOnRailsFile.getInputStream(),new TypeReference<List<UberRoute>>() {});
	}
	
}
