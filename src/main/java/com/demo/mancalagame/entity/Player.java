package com.demo.mancalagame.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a player of the game
 */
@Data
@AllArgsConstructor
public class Player {

    private int id;
    private String username;
}
