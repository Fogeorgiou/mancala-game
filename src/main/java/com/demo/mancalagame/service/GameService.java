package com.demo.mancalagame.service;

import com.demo.mancalagame.dto.GameDto;

public interface GameService {

    GameDto generateNewGame(int numberOfStonesPerPit);

    void playGame(String gameId, int playerId, int pitIndex);
}
