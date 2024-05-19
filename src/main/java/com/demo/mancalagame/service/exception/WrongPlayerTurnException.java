package com.demo.mancalagame.service.exception;

public class WrongPlayerTurnException extends GameException {

    public WrongPlayerTurnException(String message) {
        super(message);
    }
}
