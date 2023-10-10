package com.backend.li.DTO;

public class UserDTO {
    private Long id;
    private String nume;
    private String pin;
    private Integer balance;
    // constructors
    public UserDTO() {}

    public UserDTO(Long id, String nume, String pin, Integer balance) {
        this.id = id;
        this.nume = nume;
        this.pin = pin;
        this.balance = balance;
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

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}