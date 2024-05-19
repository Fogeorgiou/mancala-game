package com.demo.mancalagame.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerTest {

    @Test
    public void Should_CreatePlayer() {

        int playerId = 1;
        String playerUsername = "john";

        Player player = new Player(playerId, playerUsername);

        assertThat(player)
                .hasFieldOrPropertyWithValue("id", playerId)
                .hasFieldOrPropertyWithValue("username", playerUsername);
    }
}
