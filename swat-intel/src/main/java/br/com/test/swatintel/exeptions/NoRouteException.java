package br.com.test.swatintel.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="no route available to this destination")
public class NoRouteException extends Exception{
	
	public NoRouteException() {
		super("No route available to this destination");
	}

}
