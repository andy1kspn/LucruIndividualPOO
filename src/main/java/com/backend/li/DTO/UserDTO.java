package com.backend.li.DTO;

import java.math.BigDecimal;

public class UserDTO {
    private Long id;
    private String nume;
    private String pin;
    private String nr_card;
    private BigDecimal balance;

    public UserDTO() {}

    public UserDTO(Long id, String nume, String pin, BigDecimal balance, String nr_card) {
        this.id = id;
        this.nume = nume;
        this.pin = pin;
        this.balance = balance;
        this.nr_card = nr_card;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getNr_card() {
        return nr_card;
    }

    public void setNr_card(String nr_card) {
        this.nr_card = nr_card;
    }
}
