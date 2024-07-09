package com.conexia.starwars.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column(length = 15, nullable = false)
    String apiKey;
    @Column(nullable = false)
    String token;

    public <E> User(String apiKey, String token, ArrayList<E> es) {
    }
}
