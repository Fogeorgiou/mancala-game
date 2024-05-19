package com.demo.mancalagame.dto;

import com.demo.mancalagame.entity.GameStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

public record GameDto(String id, GameStatus status, BoardDto board, List<PlayerDto> players,
                      Map<Integer, Integer> scorePerPlayer,
                      @JsonInclude(JsonInclude.Include.NON_NULL) Integer playerTurn,
                      @JsonInclude(JsonInclude.Include.NON_NULL) Integer winner) {

    // Builder
    public static final class Builder {

        String id;
        GameStatus status;
        BoardDto board;
        List<PlayerDto> players;
        Map<Integer, Integer> scorePerPlayer;
        Integer playerTurn;
        Integer winner;

        public Builder() {

        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder status(GameStatus status) {
            this.status = status;
            return this;
        }

        public Builder board(BoardDto board) {
            this.board = board;
            return this;
        }

        public Builder players(List<PlayerDto> players) {
            this.players = players;
            return this;
        }

        public Builder scorePerPlayer(Map<Integer, Integer> scorePerPlayer) {
            this.scorePerPlayer = scorePerPlayer;
            return this;
        }

        public Builder playerTurn(Integer playerTurn) {
            this.playerTurn = playerTurn;
            return this;
        }

        public Builder winner(Integer winner) {
            this.winner = winner;
            return this;
        }

        public GameDto build() {
            return new GameDto(id, status, board, players, scorePerPlayer, playerTurn, winner);
        }
    }
}
