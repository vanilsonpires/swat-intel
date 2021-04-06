package br.com.test.swatintel.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.test.swatintel.entity.interfaces.Route;
import lombok.Data;

public @Data class UberRoute implements Route {

	private String trip;

	private String origin;

	private String destiny;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate departureDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private LocalTime departure;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private LocalTime arrival;

	private Double value;

	@Override
	public LocalDateTime getExit() {
		return departureDate.atTime(departure);
	}

	@Override
	public LocalDateTime getArrival() {
		return departure.compareTo(arrival) == 1 ? departureDate.plusDays(1).atTime(arrival) : departureDate.atTime(arrival);
	}

	@Override
	public Double getPrice() {
		return value;
	}

	@Override
	public String getCompany() {
		return "uberOnRails";
	}
}
