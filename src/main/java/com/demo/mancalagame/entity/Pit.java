package com.demo.mancalagame.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pit {

    private int index;
    private int numberOfStones;
    private int playerId;

//    public int getNumberOfStones() {
//        return numberOfStones;
//    }
//
//    public int getPlayerId() {
//        return playerId;
//    }
}
