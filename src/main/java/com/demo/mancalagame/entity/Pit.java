package com.demo.mancalagame.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a small or large pit on the game board
 */
@Data
@AllArgsConstructor
public class Pit {

    private int id;
    private int numberOfStones;
    private int playerId;
    private boolean isLargePit;
}
