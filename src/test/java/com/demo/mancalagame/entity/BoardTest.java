package com.demo.mancalagame.entity;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardTest {

//    @BeforeEach
//    public void init() {
//        stoneDistributionRule = new StoneDistributionRule();
//    }

    @Test
    public void something() {

        int numberOfStonesPerPit = 6;
        List<Player> playerList = new ArrayList<>();
        playerList.add(new Player(1, "username1"));
        playerList.add(new Player(2, "username2"));

        Board board = new Board(6, playerList);

        assertFalse(board.getPitById(0).isLargePit());
        assertTrue(board.getPitById(6).isLargePit());
    }
}
