package com.demo.mancalagame.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    private static int counter = 1;

    private int id;

    private String username;

    public Player(String username) {
        this.id = counter++;
        this.username = username;
    }
}
