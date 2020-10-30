package liveproject.m2k8s.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ProfileAlreadyExistingException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
    
}
