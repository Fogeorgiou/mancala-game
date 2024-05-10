package com.demo.mancalagame.service;

import com.demo.mancalagame.dto.GameDto;
import com.demo.mancalagame.entity.Game;
import com.demo.mancalagame.entity.Pit;
import com.demo.mancalagame.mapper.GameMapper;
import com.demo.mancalagame.repository.GameRepository;
import com.demo.mancalagame.service.gameroundvalidation.PitValidation;
import com.demo.mancalagame.service.gameroundvalidation.PlayerValidation;
import com.demo.mancalagame.service.exception.ExceptionMessage;
import com.demo.mancalagame.service.exception.GameNotFoundException;
import com.demo.mancalagame.service.gamerules.GameRule;
import com.demo.mancalagame.util.GameRoundParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @Override
    public GameDto generateNewGame(int numberOfStonesPerPit) {

        // Convert Game to GameDto
//        ModelMapper modelMapper = new ModelMapper();

        Game game = new Game(numberOfStonesPerPit);
        gameRepository.save(game);

        GameMapper gameMapper = new GameMapper();
        GameDto gameDto = gameMapper.map(game);

        return gameDto;
    }

    @Override
    public void playGame(String gameId, int playerId, int pitIndex) {

        // Get game
        Optional<Game> gameOptional = gameRepository.findById(gameId);
        Game game = gameOptional.isPresent() ? gameOptional.get() : null;

        if (isNull(game)) {
            throw new GameNotFoundException(ExceptionMessage.GAME_NOT_FOUND);
        }

        GameRoundParameters gameRoundParameters = new GameRoundParameters(playerId, pitIndex);

        // Checks related to the specified player
        PlayerValidation playerValidation = new PlayerValidation();
        playerValidation.validate(gameRoundParameters, game);

        // Checks related to the specified pit
        PitValidation pitValidation = new PitValidation();
        pitValidation.validate(gameRoundParameters, game);

        // Get specified pit from board. This is the pit from where the player will move their stones.
        Pit pitFromRequest = game.getBoard().getPitByIndex(gameRoundParameters.getPitIndex());

        playNextMove(game, pitFromRequest);
    }

    private void playNextMove(Game game, Pit pitFromRequest) {

        if (pitFromRequest.getNumberOfStones() == 0) {
            // There are no stones in the pit, so nothing happens. Maybe return a warning?
        } else {
            // Generate list of game rules
            List<GameRule> gameRules = new ArrayList<>();
//            gameRules.add();

            for (GameRule gameRule : gameRules) {
                gameRule.applyRule(game, pitFromRequest);
            }
        }
    }
}
