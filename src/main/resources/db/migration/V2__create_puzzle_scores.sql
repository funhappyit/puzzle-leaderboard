CREATE TABLE puzzle_scores
(
    id           BIGINT      NOT NULL AUTO_INCREMENT,
    user_id      BIGINT      NOT NULL,
    puzzle_id    VARCHAR(50) NOT NULL,
    score        INT         NOT NULL,
    elapsed_sec  INT         NOT NULL COMMENT '풀이 소요 시간(초)',
    submitted_at DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_valid     BOOLEAN     NOT NULL DEFAULT TRUE COMMENT 'Rate Limit 위반 시 false',
    created_at   DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX idx_user_submitted (user_id, submitted_at),
    INDEX idx_puzzle_score (puzzle_id, score DESC),
    CONSTRAINT fk_scores_user FOREIGN KEY (user_id) REFERENCES users (id)
);
