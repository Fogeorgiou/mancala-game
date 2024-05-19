package com.demo.mancalagame.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PitTest {

    @Test
    public void Should_CreatePit() {

        int pitId = 1;
        int numberOfStones = 6;
        int playerId = 1;
        boolean isLargePit = false;

        Pit pit = new Pit(pitId, numberOfStones, playerId, isLargePit);

        assertThat(pit)
                .hasFieldOrPropertyWithValue("id", pitId)
                .hasFieldOrPropertyWithValue("numberOfStones", numberOfStones)
                .hasFieldOrPropertyWithValue("playerId", playerId)
                .hasFieldOrPropertyWithValue("isLargePit", false);
    }
}

