INSERT INTO tb_user (email, nickname, password) VALUES ('rodrigo@gmail.com', 'Rodrigo', '$2a$10$RxTylgGR/e.3ernvqJxPTOCbRROqLOQB8vpG/y.6mFwX2ylIMt.8G');
INSERT INTO tb_user (email, nickname, password) VALUES ('eliane@gmail.com', 'Eliane', '$2a$10$RxTylgGR/e.3ernvqJxPTOCbRROqLOQB8vpG/y.6mFwX2ylIMt.8G');
INSERT INTO tb_user (email, nickname, password) VALUES ('luiza@gmail.com', 'Luiza', '$2a$10$RxTylgGR/e.3ernvqJxPTOCbRROqLOQB8vpG/y.6mFwX2ylIMt.8G');

INSERT INTO tb_sweepstake (name, is_private) VALUES ('Carioca 2023', false);
INSERT INTO tb_sweepstake (name, is_private) VALUES ('Paulistão 2023', false);

INSERT INTO tb_participant (user_id, sweepstake_id, role, last_access) VALUES (1, 1, 2, TIMESTAMP WITH TIME ZONE '2023-01-10T16:00:00Z');
INSERT INTO tb_participant (user_id, sweepstake_id, role, last_access) VALUES (2, 1, 0, TIMESTAMP WITH TIME ZONE '2023-01-10T12:00:00Z');
INSERT INTO tb_participant (user_id, sweepstake_id, role, last_access) VALUES (3, 1, 0, TIMESTAMP WITH TIME ZONE '2023-01-10T16:00:00Z');
INSERT INTO tb_participant (user_id, sweepstake_id, role, last_access) VALUES (2, 2, 2, TIMESTAMP WITH TIME ZONE '2023-01-10T12:00:00Z');

INSERT INTO tb_rule (sweepstake_id, name, exact_score, winner_score, score_difference, loser_score, winner) VALUES (1,'Fase de Grupos', 25, 18, 15, 12, 10);
INSERT INTO tb_rule (sweepstake_id, name, exact_score, winner_score, score_difference, loser_score, winner) VALUES (1,'Eliminatórias', 25, 18, 15, 12, 10);

INSERT INTO tb_team (sweepstake_id, name, img_uri) VALUES (1, 'Vasco', 'VAS');
INSERT INTO tb_team (sweepstake_id, name, img_uri) VALUES (1, 'Flamengo', 'FLA');
INSERT INTO tb_team (sweepstake_id, name, img_uri) VALUES (1, 'Botafogo', 'BOT');
INSERT INTO tb_team (sweepstake_id, name, img_uri) VALUES (1, 'Fluminense', 'FLU');

INSERT INTO tb_match (sweepstake_id, rule_id, type, home_team_id, away_team_id, home_team_score, away_team_score, start_moment) VALUES (1,1,0,1,2,2,1, TIMESTAMP WITH TIME ZONE '2023-01-20T16:00:00Z');
INSERT INTO tb_match (sweepstake_id, rule_id, type, home_team_id, away_team_id, home_team_score, away_team_score, start_moment) VALUES (1,1,0,3,4,1,1, TIMESTAMP WITH TIME ZONE '2023-01-21T16:00:00Z');
INSERT INTO tb_match (sweepstake_id, rule_id, type, home_team_id, away_team_id, home_team_score, away_team_score, start_moment) VALUES (1,1,0,1,3,3,0, TIMESTAMP WITH TIME ZONE '2023-01-22T16:00:00Z');
INSERT INTO tb_match (sweepstake_id, rule_id, type, home_team_id, away_team_id, home_team_score, away_team_score, start_moment) VALUES (1,1,0,2,4,2,0, TIMESTAMP WITH TIME ZONE '2023-01-23T16:00:00Z');


INSERT INTO tb_bet (user_id, sweepstake_id, match_id, home_team_score, away_team_score, status) VALUES (1,1,1,4,1,2);
INSERT INTO tb_bet (user_id, sweepstake_id, match_id, home_team_score, away_team_score, status) VALUES (2,1,1,2,1,2);
INSERT INTO tb_bet (user_id, sweepstake_id, match_id, home_team_score, away_team_score, status) VALUES (1,1,2,1,1,2);
INSERT INTO tb_bet (user_id, sweepstake_id, match_id, home_team_score, away_team_score, status) VALUES (2,1,2,1,1,2);

