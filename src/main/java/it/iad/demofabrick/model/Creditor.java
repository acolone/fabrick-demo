package it.iad.demofabrick.model;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * @author acolone
 * 
 */

@Data
public class Creditor implements Serializable{
	private static final long serialVersionUID = 2631359289594154854L;
	
	@NotBlank(message="name obbligatorio")
	@Size(min = 1, max = 70)
	@Pattern(regexp = "^[A-Za-z !]*$", message = "Formato del nome errato")
	private String name;
	
	@Valid
    private Account account;
    
	private Address address;
}
