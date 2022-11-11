package it.iad.demofabrick.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class NaturalPersonBeneficiary implements Serializable{
	private static final long serialVersionUID = 4765225669618199999L;
	
	@NotNull(message="fiscalCode1 obbligatorio")
	private String fiscalCode1;
	
	private String fiscalCode2;
	
	private String fiscalCode3;
	
	private String fiscalCode4;
	
	private String fiscalCode5;
}
