package com.demo.mancalagame.service.gameroundvalidation;

import com.demo.mancalagame.entity.Game;
import com.demo.mancalagame.entity.LargePit;
import com.demo.mancalagame.entity.Pit;
import com.demo.mancalagame.service.exception.ExceptionMessage;
import com.demo.mancalagame.service.exception.InvalidPitException;
import com.demo.mancalagame.service.exception.InvalidPitTypeException;
import com.demo.mancalagame.service.exception.PitOwnershipException;
import com.demo.mancalagame.util.GameConstants;
import com.demo.mancalagame.util.GameRoundParameters;

public class PitValidation implements GameRoundParametersValidation {

    @Override
    public void validate(GameRoundParameters gameRoundParameters, Game game) {

        Pit pitFromRequest = game.getBoard().getPitByIndex(gameRoundParameters.getPitIndex());

        // 1. Is the specified pit valid? If not, throw error with suitable message.
        if (gameRoundParameters.getPitIndex() < 0 || gameRoundParameters.getPitIndex() >= GameConstants.TOTAL_NUMBER_OF_PITS) {
            throw new InvalidPitException(ExceptionMessage.INVALID_PIT_ID);
        }

        // 2. Does the specified pit belong to the specified player? If not, throw error with suitable message.
        if (pitFromRequest.getPlayerId() != gameRoundParameters.getPlayerId()) {
            throw new PitOwnershipException(ExceptionMessage.INVALID_PIT_OWNERSHIP);
        }

        // 3. Is the pit a small pit? If not, throw error with suitable message.
        if (pitFromRequest instanceof LargePit) {
            throw new InvalidPitTypeException(ExceptionMessage.INVALID_PIT_TYPE);
        }

        // 4. Are there any stones in the specified pit? If not, throw warning with suitable message.
    }
}
