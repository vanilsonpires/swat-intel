package br.com.test.swatintel.entity.interfaces;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public interface Route {
	
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	public LocalDateTime getExit();
	
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	public LocalDateTime getArrival();
	
	@JsonProperty(access = Access.WRITE_ONLY)
	public String getOrigin();
	
	@JsonProperty(access = Access.WRITE_ONLY)
	public String getDestiny();
	
	public Double getPrice();
	
	public String getCompany();

}
