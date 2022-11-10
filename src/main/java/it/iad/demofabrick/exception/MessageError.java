package it.iad.demofabrick.exception;

import java.util.Date;

import lombok.Data;

@Data
public class MessageError {
	private String statusCode;
	private Date timestamp;
	private String message;
	
	public MessageError() {}

	public MessageError(String statusCode, Date timestamp, String message) {
		this.statusCode = statusCode;
		this.timestamp = timestamp;
		this.message = message;
	}
}
