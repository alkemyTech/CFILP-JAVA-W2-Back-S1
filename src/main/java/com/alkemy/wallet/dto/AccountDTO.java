package com.alkemy.wallet.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter 
@Setter
public class AccountDTO {
    private int id;
    private int userId; // ID del usuario propietario de la cuenta
    private String cbu;
    private double balance;
    private String alias;
    private String currency;
    private String accountType; // Solo el nombre del tipo de cuenta
}

//Tipos de cuenta
//1 caja de ahorro
//2 cuenta corriente    
//3 cuenta de inversion
