package it.iad.demofabrick.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * @author acolone
 * 
 */

@Data
public class TransferResult implements Serializable{
	private static final long serialVersionUID = 5351611196321185042L;
	
	private String moneyTransferId;
    private String status;
    private String direction;
    private Creditor creditor;
    private Debtor debtor;
    private String cro;        
    private String uri;
    private String trn;
    private String description;
    private String createdDatetime;
    private String accountedDatetime;       
    private String debtorValueDate;  
    private String creditorValueDate;    
    private Amount amount; 
    private Boolean isUrgent; 
    private Boolean isInstant;  
    private String feeType; 
    private String feeAccountId; 
    private List<Fee> fees;
    private Boolean hasTaxRelief;
	
}
