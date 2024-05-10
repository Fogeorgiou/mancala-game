package com.demo.mancalagame.dto;

import java.util.List;
import java.util.Map;

public record GameDto(String id, BoardDto board, List<PlayerDto> players, Map<String, Integer> scorePerPlayer) {
}
