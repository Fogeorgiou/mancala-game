package com.demo.mancalagame.entity;

import com.demo.mancalagame.service.gamecomponents.GameConstants;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    public void Should_CreateBoardWithInitialSetup() {

        // given
        int numberOfStonesPerPit = 6;
        List<Player> playerList = new ArrayList<>();
        playerList.add(new Player(1, "username1"));
        playerList.add(new Player(2, "username2"));

        // when
        Board board = new Board(numberOfStonesPerPit, playerList);

        // then
        assertEquals(GameConstants.TOTAL_NUMBER_OF_PITS, board.getPits().size());
        for (int i=0; i<GameConstants.TOTAL_NUMBER_OF_PITS; i++) {
            Pit pit = board.getPits().get(i);
            assertEquals(i+1, pit.getId());
            if (pit.getId()<=7) {
                assertEquals(playerList.get(0).getId(), pit.getPlayerId());
            } else {
                assertEquals(playerList.get(1).getId(), pit.getPlayerId());
            }
            if (pit.getId()==7 || pit.getId()==14) {
                assertEquals(GameConstants.ZERO, pit.getNumberOfStones());
                assertTrue(pit.isLargePit());
            } else {
                assertEquals(numberOfStonesPerPit, pit.getNumberOfStones());
                assertFalse(pit.isLargePit());
            }
        }
    }

    @Test
    public void Should_ReturnPitById_When_RequestedPitIdIsValid() {

        // given
        int numberOfStonesPerPit = 6;
        List<Player> playerList = new ArrayList<>();
        playerList.add(new Player(1, "username1"));
        playerList.add(new Player(2, "username2"));
        Board board = new Board(numberOfStonesPerPit, playerList);

        // when
        Pit pit = board.getPitById(2);

        // then
        assertNotNull(pit);
        assertEquals(2, pit.getId());
    }

    @Test
    public void Should_ReturnNull_When_RequestedPitIdIsInvalid() {

        // given
        int numberOfStonesPerPit = 6;
        List<Player> playerList = new ArrayList<>();
        playerList.add(new Player(1, "username1"));
        playerList.add(new Player(2, "username2"));
        Board board = new Board(numberOfStonesPerPit, playerList);

        // when
        Pit pit = board.getPitById(20);

        // then
        assertNull(pit);
    }

    @Test
    public void Should_ReturnLargePitByPlayerId_When_RequestedPlayerIdIsValid() {

        // given
        int numberOfStonesPerPit = 6;
        List<Player> playerList = new ArrayList<>();
        playerList.add(new Player(1, "username1"));
        playerList.add(new Player(2, "username2"));
        Board board = new Board(numberOfStonesPerPit, playerList);

        // when
        Pit pit = board.getLargePitByPlayerId(playerList.get(0).getId());

        // then
        assertNotNull(pit);
        assertEquals(playerList.get(0).getId(), pit.getPlayerId());
        assertTrue(pit.isLargePit());
    }

    @Test
    public void Should_ReturnNull_When_RequestedPlayerIdIsInvalid() {

        // given
        int numberOfStonesPerPit = 6;
        List<Player> playerList = new ArrayList<>();
        playerList.add(new Player(1, "username1"));
        playerList.add(new Player(2, "username2"));
        Board board = new Board(numberOfStonesPerPit, playerList);

        // when
        Pit pit = board.getLargePitByPlayerId(3);

        // then
        assertNull(pit);
    }

    @Test
    public void Should_ReturnAllSmallPitsByPlayerId_When_RequestedPlayerIdIsValid() {

        // given
        int numberOfStonesPerPit = 6;
        List<Player> playerList = new ArrayList<>();
        playerList.add(new Player(1, "username1"));
        playerList.add(new Player(2, "username2"));
        Board board = new Board(numberOfStonesPerPit, playerList);

        // when
        List<Pit> smallPits = board.getAllSmallPitsByPlayerId(playerList.get(0).getId());

        // then
        assertEquals(6, smallPits.size());
        smallPits.forEach(pit -> {
            assertEquals(playerList.get(0).getId(), pit.getPlayerId());
            assertFalse(pit.isLargePit());
        });
    }

    @Test
    public void Should_ReturnNoSmallPitsByPlayerId_When_RequestedPlayerIdIsInvalid() {

        // given
        int numberOfStonesPerPit = 6;
        List<Player> playerList = new ArrayList<>();
        playerList.add(new Player(1, "username1"));
        playerList.add(new Player(2, "username2"));
        Board board = new Board(numberOfStonesPerPit, playerList);

        // when
        List<Pit> smallPits = board.getAllSmallPitsByPlayerId(3);

        // then
        assertEquals(0, smallPits.size());
    }

    @Test
    public void Should_ReturnAllStonesInSmallPitsByPlayerId_When_RequestedPlayerIdIsValid() {

        // given
        int numberOfStonesPerPit = 6;
        List<Player> playerList = new ArrayList<>();
        playerList.add(new Player(1, "username1"));
        playerList.add(new Player(2, "username2"));
        Board board = new Board(numberOfStonesPerPit, playerList);

        // when
        int totalNumberOfStones = board.getAllStonesInSmallPitsByPlayerId(playerList.get(0).getId());

        // then
        assertEquals(36, totalNumberOfStones);
    }

    @Test
    public void Should_ReturnNoStonesInSmallPitsByPlayerId_When_RequestedPlayerIdIsInvalid() {

        // given
        int numberOfStonesPerPit = 6;
        List<Player> playerList = new ArrayList<>();
        playerList.add(new Player(1, "username1"));
        playerList.add(new Player(2, "username2"));
        Board board = new Board(numberOfStonesPerPit, playerList);

        // when
        int totalNumberOfStones = board.getAllStonesInSmallPitsByPlayerId(3);

        // then
        assertEquals(0, totalNumberOfStones);
    }

    @Test
    public void Should_ReturnTrue_When_ThereAreNoStonesInPlayerSmallPits() {

        // given
        int numberOfStonesPerPit = 6;
        List<Player> playerList = new ArrayList<>();
        playerList.add(new Player(1, "username1"));
        playerList.add(new Player(2, "username2"));
        Board board = new Board(numberOfStonesPerPit, playerList);
        // Set the number of stones in the small pits of player 1 to 0
        for (int i=1; i<=6; i++) {
            board.getPitById(i).setNumberOfStones(GameConstants.ZERO);
        }

        // when
        boolean noStones = board.noStonesInPlayerPits(playerList.get(0).getId());

        // then
        assertTrue(noStones);
    }

    @Test
    public void Should_ReturnFalse_When_ThereAreStonesInPlayerSmallPits() {

        // given
        int numberOfStonesPerPit = 6;
        List<Player> playerList = new ArrayList<>();
        playerList.add(new Player(1, "username1"));
        playerList.add(new Player(2, "username2"));
        Board board = new Board(numberOfStonesPerPit, playerList);

        // when
        boolean noStones = board.noStonesInPlayerPits(playerList.get(0).getId());

        // then
        assertFalse(noStones);
    }
}
