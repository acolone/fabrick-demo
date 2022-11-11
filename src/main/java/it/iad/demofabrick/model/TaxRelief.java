package it.iad.demofabrick.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class TaxRelief implements Serializable{
	private static final long serialVersionUID = 707183999343522669L;
	
	private String taxReliefId;
	
	@NotNull(message="isCondoUpgrade obbligatorio")
	private Boolean isCondoUpgrade;
	
	@NotNull(message="creditorFiscalCode obbligatorio")
	private String creditorFiscalCode;
	
	@NotNull(message="beneficiaryType obbligatorio")
	private String beneficiaryType;
	
	private NaturalPersonBeneficiary naturalPersonBeneficiary;
	
	private LegalPersonBeneficiary legalPersonBeneficiary;
}
