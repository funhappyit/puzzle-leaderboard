CREATE TABLE daily_ranking_snapshots
(
    id            BIGINT      NOT NULL AUTO_INCREMENT,
    user_id       BIGINT      NOT NULL,
    snapshot_date DATE        NOT NULL,
    puzzle_id     VARCHAR(50) NOT NULL,
    rank          INT         NOT NULL,
    total_score   BIGINT      NOT NULL,
    created_at    DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uq_snapshot (snapshot_date, puzzle_id, user_id),
    CONSTRAINT fk_snapshot_user FOREIGN KEY (user_id) REFERENCES users (id)
);
