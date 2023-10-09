package com.example.li.DTO;

public class UserDTO {
    private Long id;
    private String nume;

    // constructors
    public UserDTO() {}


    public UserDTO(Long id, String nume) {
        this.id = id;
        this.nume = nume;

    }

    // getters and setters

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
}