package com.demo.mancalagame.service.gamecomponents.gameroundvalidation;

import com.demo.mancalagame.entity.Game;
import com.demo.mancalagame.service.exception.ExceptionMessage;
import com.demo.mancalagame.service.exception.InvalidPlayerException;
import com.demo.mancalagame.service.exception.WrongPlayerTurnException;
import com.demo.mancalagame.service.gamecomponents.GameRoundSelectionParameters;

public class PlayerValidation implements GameRoundParametersValidation {

    @Override
    public void validate(GameRoundSelectionParameters gameRoundSelectionParameters, Game game) {

        // 1. Is the specified player valid? If not, throw error with suitable message.
        if (!game.getPlayers().stream()
                .map(player -> player.getId()).toList()
                .contains(gameRoundSelectionParameters.getPlayerId())) {
            throw new InvalidPlayerException(ExceptionMessage.INVALID_PLAYER_ID);
        }

        // 2. Is it the specified player's turn? If not, throw error with suitable message.
        if (gameRoundSelectionParameters.getPlayerId() != game.getPlayerTurn()) {
            throw new WrongPlayerTurnException(ExceptionMessage.WRONG_PLAYER_TURN);
        }
    }
}
