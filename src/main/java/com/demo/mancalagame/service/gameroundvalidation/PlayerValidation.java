package com.demo.mancalagame.service.gameroundvalidation;

import com.demo.mancalagame.entity.Game;
import com.demo.mancalagame.service.exception.ExceptionMessage;
import com.demo.mancalagame.service.exception.InvalidPlayerException;
import com.demo.mancalagame.service.exception.WrongPlayerTurnException;
import com.demo.mancalagame.util.GameRoundParameters;

public class PlayerValidation implements GameRoundParametersValidation {

    @Override
    public void validate(GameRoundParameters gameRoundParameters, Game game) {

        // 1. Is the specified player valid? If not, throw error with suitable message.
        if (!(gameRoundParameters.getPlayerId() == 1 || gameRoundParameters.getPlayerId() == 2)) {
            throw new InvalidPlayerException(ExceptionMessage.INVALID_PLAYER_ID);
        }

        // 2. Is it the specified player's turn? If not, throw error with suitable message.
        if (gameRoundParameters.getPlayerId() != game.getPlayerTurn()) {
            throw new WrongPlayerTurnException(ExceptionMessage.WRONG_PLAYER_TURN);
        }
    }
}
