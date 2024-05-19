package com.demo.mancalagame.service;

import com.demo.mancalagame.dto.GameDto;
import com.demo.mancalagame.entity.Game;
import com.demo.mancalagame.entity.GameStatus;
import com.demo.mancalagame.mapper.GameMapper;
import com.demo.mancalagame.repository.GameRepository;
import com.demo.mancalagame.service.exception.ExceptionMessage;
import com.demo.mancalagame.service.exception.GameFinishedException;
import com.demo.mancalagame.service.exception.GameNotFoundException;
import com.demo.mancalagame.service.gamecomponents.GameRoundExecutor;
import com.demo.mancalagame.service.gamecomponents.GameRoundSelectionParameters;
import com.demo.mancalagame.service.gamecomponents.gameroundvalidation.PitValidation;
import com.demo.mancalagame.service.gamecomponents.gameroundvalidation.PlayerValidation;
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

    @Autowired
    private GameMapper gameMapper;

    @Override
    public GameDto generateNewGame(int numberOfStonesPerPit) {

        Game game = new Game(numberOfStonesPerPit);

        gameRepository.save(game);

        GameDto gameDto = gameMapper.toDto(game);

        return gameDto;
    }

    @Override
    public GameDto playGame(String gameId, int playerId, int pitId) {

        // Get game specified in the request
        Optional<Game> gameOptional = gameRepository.findById(gameId);
        Game game = gameOptional.isPresent() ? gameOptional.get() : null;

        if (isNull(game)) {
            throw new GameNotFoundException(ExceptionMessage.GAME_NOT_FOUND);
        }

        // Check if game is finished
        if (GameStatus.FINISHED.equals(game.getGameStatus())) {
            throw new GameFinishedException(ExceptionMessage.GAME_ALREADY_FINISHED);
        }

        GameRoundSelectionParameters gameRoundSelectionParameters = new GameRoundSelectionParameters(playerId, pitId);

        // Checks related to the player specified in the request
        PlayerValidation playerValidation = new PlayerValidation();
        playerValidation.validate(gameRoundSelectionParameters, game);

        // Checks related to the pit specified in the request
        PitValidation pitValidation = new PitValidation();
        pitValidation.validate(gameRoundSelectionParameters, game);

//        // Get specified pit from board. This is the pit from where the player will move their stones.
//        Pit pitFromRequest = game.getBoard().getPitByIndex(gameRoundSelectionParameters.getPitIndex());

        gameRoundExecutor.play(game, gameRoundSelectionParameters);

//        game.setGameStatus(GameStatus.IN_PROGRESS);

        gameRepository.save(game);

        GameDto gameDto = gameMapper.toDto(game);

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

        GameDto gameDto = gameMapper.toDto(game);

        return gameDto;
    }
}
