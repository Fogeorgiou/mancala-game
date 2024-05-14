package com.demo.mancalagame.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pit {

    private int id;
    private int numberOfStones;
    private int playerId;
    private boolean isLargePit;

//    public int getNumberOfStones() {
//        return numberOfStones;
//    }
//
//    public int getPlayerId() {
//        return playerId;
//    }
}
