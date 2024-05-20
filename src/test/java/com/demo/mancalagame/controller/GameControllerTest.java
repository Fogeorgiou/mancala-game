package com.demo.mancalagame.controller;

import com.demo.mancalagame.service.GameService;
import com.demo.mancalagame.service.exception.*;
import com.demo.mancalagame.util.GameTestUtilities;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StreamUtils;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameController.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @Test
    void Should_Return201Response_When_AGameGetsGenerated() throws Exception {
        int numberOfStonesPerPit = 6;

        String expectedGame = StreamUtils.copyToString(getClass().getClassLoader().getResourceAsStream("startedGameDto.json"), Charset.defaultCharset());

        when(gameService.generateNewGame(numberOfStonesPerPit))
                .thenReturn(GameTestUtilities.createStartedGameDto(numberOfStonesPerPit));

        this.mockMvc.perform(post(new StringBuilder().append("/games/new").toString()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(is(expectedGame)));

        verify(gameService).generateNewGame(eq(numberOfStonesPerPit));
    }

    @Test
    void Should_Return200Response_When_AGameRoundIsPlayed_And_GameGetsUpdated() throws Exception {
        String gameId = "123";
        int playerId = 1;
        int pitId = 1;

        String expectedGame = StreamUtils.copyToString(getClass().getClassLoader().getResourceAsStream("inProgressGameDto.json"), Charset.defaultCharset());

        when(gameService.playGame(gameId, playerId, pitId))
                .thenReturn(GameTestUtilities.createInProgressGameDto());

        this.mockMvc.perform(
                    put(new StringBuilder().append("/games/play")
                            .append(String.format("?game_id=%s&player_id=%s&pit_id=%s", gameId, playerId, pitId))
                            .toString()
                    )
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(is(expectedGame)));

        verify(gameService).playGame(eq(gameId), eq(playerId), eq(pitId));
    }

    @Test
    void Should_Return404Response_When_RequestedGameToPlayWithIsNotFound() throws Exception {
        String gameId = "123";
        int playerId = 1;
        int pitId = 1;

        doThrow(new GameNotFoundException(ExceptionMessage.GAME_NOT_FOUND))
                .when(gameService).playGame(gameId, playerId, pitId);

        this.mockMvc.perform(
                        put(new StringBuilder().append("/games/play")
                                .append(String.format("?game_id=%s&player_id=%s&pit_id=%s", gameId, playerId, pitId))
                                .toString()
                        )
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(is(ExceptionMessage.GAME_NOT_FOUND)));

        verify(gameService).playGame(eq(gameId), eq(playerId), eq(pitId));
    }

    @Test
    void Should_Return400Response_When_RequestedPlayerIdIsInvalid() throws Exception {
        String gameId = "123";
        int playerId = 5;
        int pitId = 1;

        doThrow(new InvalidPlayerException(ExceptionMessage.INVALID_PLAYER_ID))
                .when(gameService).playGame(gameId, playerId, pitId);

        this.mockMvc.perform(
                        put(new StringBuilder().append("/games/play")
                                .append(String.format("?game_id=%s&player_id=%s&pit_id=%s", gameId, playerId, pitId))
                                .toString()
                        )
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(
                        is(
                            new StringBuilder()
                                    .append("Bad request: ")
                                    .append(ExceptionMessage.INVALID_PLAYER_ID)
                                    .toString()
                        )
                ));

        verify(gameService).playGame(eq(gameId), eq(playerId), eq(pitId));
    }

    @Test
    void Should_Return400Response_When_ItIsNotTheRequestedPlayerTurnToPlay() throws Exception {
        String gameId = "123";
        int playerId = 2;
        int pitId = 1;

        doThrow(new WrongPlayerTurnException(ExceptionMessage.WRONG_PLAYER_TURN))
                .when(gameService).playGame(gameId, playerId, pitId);

        this.mockMvc.perform(
                        put(new StringBuilder().append("/games/play")
                                .append(String.format("?game_id=%s&player_id=%s&pit_id=%s", gameId, playerId, pitId))
                                .toString()
                        )
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(
                        is(
                                new StringBuilder()
                                        .append("Bad request: ")
                                        .append(ExceptionMessage.WRONG_PLAYER_TURN)
                                        .toString()
                        )
                ));

        verify(gameService).playGame(eq(gameId), eq(playerId), eq(pitId));
    }

    @Test
    void Should_Return400Response_When_RequestedPitIdIsInvalid() throws Exception {
        String gameId = "123";
        int playerId = 1;
        int pitId = 20;

        doThrow(new InvalidPitException(ExceptionMessage.INVALID_PIT_ID))
                .when(gameService).playGame(gameId, playerId, pitId);

        this.mockMvc.perform(
                        put(new StringBuilder().append("/games/play")
                                .append(String.format("?game_id=%s&player_id=%s&pit_id=%s", gameId, playerId, pitId))
                                .toString()
                        )
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(
                        is(
                                new StringBuilder()
                                        .append("Bad request: ")
                                        .append(ExceptionMessage.INVALID_PIT_ID)
                                        .toString()
                        )
                ));

        verify(gameService).playGame(eq(gameId), eq(playerId), eq(pitId));
    }

    @Test
    void Should_Return400Response_When_RequestedPitIdIsZero() throws Exception {
        String gameId = "123";
        int playerId = 1;
        int pitId = 0;

        this.mockMvc.perform(
                        put(new StringBuilder().append("/games/play")
                                .append(String.format("?game_id=%s&player_id=%s&pit_id=%s", gameId, playerId, pitId))
                                .toString()
                        )
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(
                        is(
                                new StringBuilder()
                                        .append("Bad request: playGame.pitId: must be greater than or equal to 1")
                                        .toString()
                        )
                ));

        verify(gameService, never()).playGame(anyString(), anyInt(), anyInt());
    }

    @Test
    void Should_Return200Response_When_RequestedGameIsReturned() throws Exception {
        String gameId = "123";
        int numberOfStonesPerPit = 6;

        String expectedGame = StreamUtils.copyToString(getClass().getClassLoader().getResourceAsStream("startedGameDto.json"), Charset.defaultCharset());

        when(gameService.getGame(gameId))
                .thenReturn(GameTestUtilities.createStartedGameDto(numberOfStonesPerPit));

        this.mockMvc.perform(get(new StringBuilder().append("/games/").append(gameId).toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(is(expectedGame)));

        verify(gameService).getGame(eq(gameId));
    }

    @Test
    void Should_Return404Response_When_RequestedGameIsNotFound() throws Exception {
        String gameId = "123";

        doThrow(new GameNotFoundException(ExceptionMessage.GAME_NOT_FOUND))
                .when(gameService).getGame(gameId);

        this.mockMvc.perform(get(new StringBuilder().append("/games/").append(gameId).toString()))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(is(ExceptionMessage.GAME_NOT_FOUND)));

        verify(gameService).getGame(eq(gameId));
    }
}