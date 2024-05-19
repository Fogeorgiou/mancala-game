package com.demo.mancalagame.service.gamecomponents.gameroundvalidation;

import com.demo.mancalagame.entity.Game;
import com.demo.mancalagame.service.gamecomponents.GameRoundSelectionParameters;

public interface GameRoundParametersValidation {

    void validate(GameRoundSelectionParameters gameRoundSelectionParameters, Game game);
}
