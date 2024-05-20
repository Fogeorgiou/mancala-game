DROP TABLE IF EXISTS `game`;

CREATE TABLE `game` (
  `game_id` varchar(50) NOT NULL UNIQUE,
  `game_status` varchar(30) NOT NULL,
  `board` varchar(10000) NOT NULL,
  `players` varchar(10000) NOT NULL,
  `score_per_player` varchar(10000) NOT NULL,
  `player_turn` int,
  `game_winner` int,
  `last_updated_pit` varchar(10000),
  `last_rule_applied` varchar(1000),
  PRIMARY KEY (`game_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;