package com.nttdata.apirestcreditcard.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nttdata.apirestcreditcard.dto.CustomerDTO;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Document(collection = "creditCard")
public class CreditCard {
    @Id
    private String id;

    private String nameCard;

    private String pan; //Personal Account Number

    private String cardType;

    private String cvv;

    private String monthYearExp;

    @Field( name = "date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOperation = LocalDateTime.now();

    private String cardBrand;

    private double balanceAmount;

    private double creditLimit;

    private double totalConsumption;

    private boolean active;

    private String chargeDay;

    private CustomerDTO customer;
}
