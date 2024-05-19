package com.demo.mancalagame.service;

import com.demo.mancalagame.dto.GameDto;
import com.demo.mancalagame.entity.Game;
import com.demo.mancalagame.entity.GameStatus;
import com.demo.mancalagame.mapper.GameMapper;
import com.demo.mancalagame.repository.GameRepository;
import com.demo.mancalagame.service.exception.GameFinishedException;
import com.demo.mancalagame.service.exception.GameNotFoundException;
import com.demo.mancalagame.service.gamecomponents.GameRoundExecutor;
import com.demo.mancalagame.service.gamecomponents.GameRoundSelectionParameters;
import com.demo.mancalagame.util.GameTestUtilities;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceImplTest {

    @Mock
    GameRepository gameRepository;

    @Mock
    GameRoundExecutor gameRoundExecutor;

    @Mock
    GameMapper gameMapper;

    @InjectMocks
    private GameServiceImpl gameService;

    @Test
    void Should_GenerateNewGame() {

        int numberOfStonesPerPit = 6;

        when(gameMapper.toDto(any(Game.class))).thenReturn(GameTestUtilities.createStartedGameDto(numberOfStonesPerPit));

        GameDto generatedGame = gameService.generateNewGame(numberOfStonesPerPit);

        assertNotNull(generatedGame);

        verify(gameRepository).save(any(Game.class));
        verify(gameMapper).toDto(any(Game.class));
    }

    @Test
    void Should_PlayGame() {

        int numberOfStonesPerPit = 6;
        String gameId = "123";
        int playerId = 1;
        int pitId = 1;

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(new Game(numberOfStonesPerPit)));
        when(gameMapper.toDto(any(Game.class))).thenReturn(GameTestUtilities.createStartedGameDto(numberOfStonesPerPit));

        GameDto gameDto = gameService.playGame(gameId, playerId, pitId);

        assertNotNull(gameDto);

        verify(gameRepository).findById(eq(gameId));
        verify(gameRoundExecutor).play(any(Game.class), any(GameRoundSelectionParameters.class));
        verify(gameMapper).toDto(any(Game.class));
    }

    @Test
    void Should_ThrowGameNotFoundException_When_RequestedGameToPlayWithIsNotFound() {

        String gameId = "123";
        int playerId = 1;
        int pitId = 1;

        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        GameNotFoundException exception =
                assertThrows(
                        GameNotFoundException.class,
                        () -> gameService.playGame(gameId, playerId, pitId)
                );

        assertEquals("No game with the specified id was found", exception.getMessage());

        verify(gameRepository).findById(eq(gameId));
        verify(gameRoundExecutor, never()).play(any(Game.class), any(GameRoundSelectionParameters.class));
        verify(gameMapper, never()).toDto(any(Game.class));
    }

    @Test
    void Should_ThrowGameFinishedException_When_RequestedGameToPlayWithIsFinished() {

        String gameId = "123";
        int playerId = 1;
        int pitId = 1;

        Game game = new Game();
        game.setGameStatus(GameStatus.FINISHED);

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));

        GameFinishedException exception =
                assertThrows(
                        GameFinishedException.class,
                        () -> gameService.playGame(gameId, playerId, pitId)
                );

        assertEquals("The specified game is already finished. Please try to start a new one.",
                exception.getMessage());

        verify(gameRepository).findById(eq(gameId));
        verify(gameRoundExecutor, never()).play(any(Game.class), any(GameRoundSelectionParameters.class));
        verify(gameMapper, never()).toDto(any(Game.class));
    }

    @Test
    void Should_ReturnRequestedGame() {

        int numberOfStonesPerPit = 6;
        String gameId = "123";

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(new Game()));
        when(gameMapper.toDto(any(Game.class))).thenReturn(GameTestUtilities.createStartedGameDto(numberOfStonesPerPit));

        GameDto requestedGame = gameService.getGame(gameId);

        assertNotNull(requestedGame);

        verify(gameRepository).findById(eq(gameId));
        verify(gameMapper).toDto(any(Game.class));
    }

    @Test
    void Should_ThrowGameNotFoundException_When_RequestedGameIsNotFound() {

        String gameId = "123";

        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        GameNotFoundException exception =
                assertThrows(
                        GameNotFoundException.class,
                        () -> gameService.getGame(gameId)
                );

        assertEquals("No game with the specified id was found", exception.getMessage());

        verify(gameRepository).findById(eq(gameId));
        verify(gameMapper, never()).toDto(any(Game.class));
    }
}
