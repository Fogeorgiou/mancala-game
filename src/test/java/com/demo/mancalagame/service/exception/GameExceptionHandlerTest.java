package com.demo.mancalagame.service.exception;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameExceptionHandlerTest {

    private static GameExceptionHandler gameExceptionHandler;

    @BeforeAll
    public static void init() {

        gameExceptionHandler = new GameExceptionHandler();
    }

    @Test
    public void Should_HandleGameException_And_ReturnResponseWithAppropriateMessage() {

        ResponseEntity<String> responseEntity =
                gameExceptionHandler.handleGameException(new WrongPlayerTurnException(ExceptionMessage.WRONG_PLAYER_TURN));

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Bad request: " + ExceptionMessage.WRONG_PLAYER_TURN, responseEntity.getBody());
    }

    @Test
    public void Should_HandleGameNotFoundException_And_ReturnResponseWithAppropriateMessage() {

        ResponseEntity<String> responseEntity =
                gameExceptionHandler.handleGameNotFoundException(new GameNotFoundException(ExceptionMessage.GAME_NOT_FOUND));

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(ExceptionMessage.GAME_NOT_FOUND, responseEntity.getBody());
    }
}
