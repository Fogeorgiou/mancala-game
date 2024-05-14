package com.demo.mancalagame.service;

import com.demo.mancalagame.dto.GameDto;
import com.demo.mancalagame.entity.Game;
import com.demo.mancalagame.entity.GameStatus;
import com.demo.mancalagame.mapper.GameMapper;
import com.demo.mancalagame.repository.GameRepository;
import com.demo.mancalagame.service.exception.ExceptionMessage;
import com.demo.mancalagame.service.exception.GameNotFoundException;
import com.demo.mancalagame.util.GameRoundExecutor;
import com.demo.mancalagame.util.GameRoundSelectionParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameRoundExecutor gameRoundExecutor;

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
    public GameDto playGame(String gameId, int playerId, int pitIndex) {

        // Get game
        Optional<Game> gameOptional = gameRepository.findById(gameId);
        Game game = gameOptional.isPresent() ? gameOptional.get() : null;

        if (isNull(game)) {
            throw new GameNotFoundException(ExceptionMessage.GAME_NOT_FOUND);
        }

        GameRoundSelectionParameters gameRoundSelectionParameters = new GameRoundSelectionParameters(playerId, pitIndex);

//        // Checks related to the specified player
//        PlayerValidation playerValidation = new PlayerValidation();
//        playerValidation.validate(gameRoundSelectionParameters, game);
//
//        // Checks related to the specified pit
//        PitValidation pitValidation = new PitValidation();
//        pitValidation.validate(gameRoundSelectionParameters, game);

//        // Get specified pit from board. This is the pit from where the player will move their stones.
//        Pit pitFromRequest = game.getBoard().getPitByIndex(gameRoundSelectionParameters.getPitIndex());

        gameRoundExecutor.play(game, gameRoundSelectionParameters);

        game.setGameStatus(GameStatus.IN_PROGRESS);

        gameRepository.save(game);

        GameMapper gameMapper = new GameMapper();
        GameDto gameDto = gameMapper.map(game);

        return gameDto;
    }

    @Override
    public GameDto getGame(String gameId) {

        // Get game
        Optional<Game> gameOptional = gameRepository.findById(gameId);
        Game game = gameOptional.isPresent() ? gameOptional.get() : null;

        if (isNull(game)) {
            throw new GameNotFoundException(ExceptionMessage.GAME_NOT_FOUND);
        }

        GameMapper gameMapper = new GameMapper();
        GameDto gameDto = gameMapper.map(game);

        return gameDto;
    }

//    private void playNextMove(Game game, Pit pitFromRequest) {
//
//        if (pitFromRequest.getNumberOfStones() == 0) {
//            // There are no stones in the pit, so nothing happens. Maybe return a warning?
//        } else {
//            // Generate list of game rules
//            List<GameRule> gameRules = new ArrayList<>();
//            gameRules.add(new StoneDistributionRule());
//
//            for (GameRule gameRule : gameRules) {
//                gameRule.apply(game, pitFromRequest);
//            }
//        }
//    }
}
