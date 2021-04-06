package br.com.test.swatintel.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.test.swatintel.entity.interfaces.Route;
import lombok.Data;

public @Data class TrainRoute implements Route {

	private String tripNumber;

	private String originStation;

	private String destinyStation;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
	private LocalDate date;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private LocalTime departureTime;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private LocalTime arrivalTime;

	private Double price;

	@Override
	public String getOrigin() {
		return originStation;
	}

	@Override
	public String getDestiny() {
		return destinyStation;
	}

	@Override
	public Double getPrice() {
		return price;
	}

	@Override
	public LocalDateTime getExit() {
		return date.atTime(departureTime);
	}

	@Override
	public LocalDateTime getArrival() {
		return departureTime.compareTo(arrivalTime) == 1 ? date.plusDays(1).atTime(arrivalTime) : date.atTime(arrivalTime);
	}

	@Override
	public String getCompany() {
		return "iTrain";
	}

}
