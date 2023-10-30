package com.backend.li.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "utilizatori", schema = "lipoo", catalog = "")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nume", nullable = false)
    private String nume;

    @Column(name = "nr_card", nullable = false)
    private String nr_card;

    @Column(name = "pin", nullable = false)
    private String pin;

    @Column(name = "balance")
    private BigDecimal balance;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TranzactiiEntity> tranzactii;

    public UserEntity(String nume, String nr_card, String pin) {
        this.nume = nume;
        this.nr_card = nr_card;
        this.pin = pin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(nume, that.nume) && Objects.equals(nr_card, that.nr_card) && Objects.equals(pin, that.pin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nume, nr_card, pin);
    }
}
