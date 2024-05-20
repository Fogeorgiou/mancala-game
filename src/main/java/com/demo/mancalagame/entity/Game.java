package com.demo.mancalagame.entity;

import com.demo.mancalagame.service.gamecomponents.GameConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.*;

/**
 * Represents a game
 */
@Data
@NoArgsConstructor
@Entity
@Table(name="game")
public class Game {

    @Id
    @Column(name="game_id")
    private String gameId;

    @Column(name="game_status")
    @JdbcTypeCode(SqlTypes.JSON)
    private GameStatus gameStatus;

    @Column(name="board")
    @JdbcTypeCode(SqlTypes.JSON)
    private Board board;

    @Column(name="players")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<Player> players;

    @Column(name="score_per_player")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<Integer, Integer> scorePerPlayer;

    @Column(name="player_turn")
    private Integer playerTurn;

    @Column(name="game_winner")
    private Integer gameWinner;

    @JdbcTypeCode(SqlTypes.JSON)
    private Pit lastUpdatedPit;

    private String lastRuleApplied;

    public Game(int numberOfStonesPerPit) {

        this.gameId = UUID.randomUUID().toString();

        gameStatus = GameStatus.STARTED;

        createPlayers();

        createInitialBoardSetup(numberOfStonesPerPit);

        initialiseScorePerPlayer();

        playerTurn = this.players.get(0).getId();

        gameWinner = null;
    }

    private void createPlayers() {

        this.players = new ArrayList<>();
        for (int i=1; i<=GameConstants.NUMBER_OF_PLAYERS; i++) {
            this.players.add(new Player(i, new StringBuilder().append("username").append(i).toString()));
        }
    }

    private void createInitialBoardSetup(int numberOfStonesPerPit) {

        this.board = new Board(numberOfStonesPerPit, this.players);
    }

    private void initialiseScorePerPlayer() {

        this.scorePerPlayer = new HashMap<>();
        this.players.forEach(player -> this.scorePerPlayer.put(player.getId(), GameConstants.ZERO));
    }
}
