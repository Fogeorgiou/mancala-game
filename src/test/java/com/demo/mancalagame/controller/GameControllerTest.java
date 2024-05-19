package com.demo.mancalagame.controller;

import com.demo.mancalagame.service.GameService;
import com.demo.mancalagame.util.GameTestUtilities;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StreamUtils;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
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
                            .append("?game_id=123&player_id=1&pit_id=1")
                            .toString()
                    )
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(is(expectedGame)));
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
    }
}