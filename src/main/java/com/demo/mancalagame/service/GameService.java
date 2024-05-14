package com.demo.mancalagame.service;

import com.demo.mancalagame.dto.GameDto;

public interface GameService {

    GameDto generateNewGame(int numberOfStonesPerPit);

    GameDto playGame(String gameId, int playerId, int pitIndex);

    GameDto getGame(String gameId);
}
