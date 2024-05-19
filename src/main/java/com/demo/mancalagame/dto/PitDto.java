package com.demo.mancalagame.dto;

public record PitDto(int id, int numberOfStones, int playerId, boolean largePit) {

    // Builder
    public static final class Builder {

        int id;
        int numberOfStones;
        int playerId;
        boolean largePit;

        public Builder() {

        }

        public PitDto.Builder id(int id) {
            this.id = id;
            return this;
        }

        public PitDto.Builder numberOfStones(int numberOfStones) {
            this.numberOfStones = numberOfStones;
            return this;
        }

        public PitDto.Builder playerId(int playerId) {
            this.playerId = playerId;
            return this;
        }

        public PitDto.Builder largePit(boolean largePit) {
            this.largePit = largePit;
            return this;
        }

        public PitDto build() {
            return new PitDto(id, numberOfStones, playerId, largePit);
        }
    }
}
