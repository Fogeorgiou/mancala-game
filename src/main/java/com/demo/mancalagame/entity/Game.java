package com.demo.mancalagame.entity;

import com.demo.mancalagame.util.GameConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.*;

@Data
@NoArgsConstructor
@Entity
@Table(name="game")
public class Game {

    @Id
    @Column(name="game_id")
    private String gameId;

    @Column(name="board")
    @JdbcTypeCode(SqlTypes.JSON)
    private Board board;

    @Column(name="players")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<Player> players;

    @Column(name="score_per_player")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<Player, Integer> scorePerPlayer;

    @Column(name="player_turn")
    private int playerTurn;

    public Game(int numberOfStonesPerPit) {

        this.gameId = UUID.randomUUID().toString();

        createPlayers();

        createInitialBoardSetup(numberOfStonesPerPit);

        initialiseScorePerPlayer();

        playerTurn = this.players.get(0).getId();
    }

    private void createPlayers() {

        this.players = new ArrayList<>();
        this.players.add(new Player("username1"));
        this.players.add(new Player("username2"));
    }

    private void createInitialBoardSetup(int numberOfStonesPerPit) {

        this.board = new Board(numberOfStonesPerPit, this.players);
    }

    private void initialiseScorePerPlayer() {

        this.scorePerPlayer = new HashMap<>();
        this.players.forEach(player -> this.scorePerPlayer.put(player, GameConstants.ZERO));
    }
}
