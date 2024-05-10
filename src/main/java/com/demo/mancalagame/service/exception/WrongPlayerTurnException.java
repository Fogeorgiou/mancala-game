package com.demo.mancalagame.service.exception;

public class WrongPlayerTurnException extends RuntimeException {

    public WrongPlayerTurnException(String message) {
        super(message);
    }
}
