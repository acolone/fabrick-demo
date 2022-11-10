package it.iad.demofabrick.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Address implements Serializable{
	private static final long serialVersionUID = -4127630488958932408L;
	
	private String address;
    private String city;
    private String countryCode;
}
