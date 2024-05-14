package com.demo.mancalagame.service.gamecomponents.gameroundvalidation;

import com.demo.mancalagame.entity.Game;
import com.demo.mancalagame.util.GameRoundSelectionParameters;

public interface GameRoundParametersValidation {

    void validate(GameRoundSelectionParameters gameRoundSelectionParameters, Game game);
}
