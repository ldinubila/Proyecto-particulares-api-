package ar.edu.unlam.proyecto.entidades;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "Token")
public class Token {
	
	  	@Id  
	    @GeneratedValue(strategy=GenerationType.AUTO)  
	    private Long tokenID;        
	    private Long userID;      
	    private String authenticationToken;    
	    private String secretKey;    
	    private String emailId;  

}
