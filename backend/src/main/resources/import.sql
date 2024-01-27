INSERT INTO tb_user (email, nickname, password) VALUES ('rodrigo@gmail.com', 'Rodrigo', '$2a$10$RxTylgGR/e.3ernvqJxPTOCbRROqLOQB8vpG/y.6mFwX2ylIMt.8G');
INSERT INTO tb_user (email, nickname, password) VALUES ('larissa@gmail.com', 'Larissa', '$2a$10$RxTylgGR/e.3ernvqJxPTOCbRROqLOQB8vpG/y.6mFwX2ylIMt.8G');
INSERT INTO tb_user (email, nickname, password) VALUES ('enzo@gmail.com', 'Enzo', '$2a$10$RxTylgGR/e.3ernvqJxPTOCbRROqLOQB8vpG/y.6mFwX2ylIMt.8G');
INSERT INTO tb_user (email, nickname, password) VALUES ('kaua@gmail.com', 'Kauã', '$2a$10$RxTylgGR/e.3ernvqJxPTOCbRROqLOQB8vpG/y.6mFwX2ylIMt.8G');
INSERT INTO tb_user (email, nickname, password) VALUES ('ana@gmail.com', 'Ana', '$2a$10$RxTylgGR/e.3ernvqJxPTOCbRROqLOQB8vpG/y.6mFwX2ylIMt.8G');

INSERT INTO tb_league (endpoint, is_custom, name, season, is_active) VALUES ('', true, 'Personalizado', '', true);
INSERT INTO tb_league (endpoint, is_custom, name, season, is_active) VALUES ('cblol', false, 'CBLOL', '2024-1', true);
INSERT INTO tb_league (endpoint, is_custom, name, season, is_active) VALUES ('ucl', false, 'Liga dos Campeões', '2023', true);
INSERT INTO tb_league (endpoint, is_custom, name, season, is_active) VALUES ('brasileiro-a', false, 'Brasileirão A', '2023', true);
INSERT INTO tb_league (endpoint, is_custom, name, season, is_active) VALUES ('premier-league', false, 'Premier League', '2023', true);
INSERT INTO tb_league (endpoint, is_custom, name, season, is_active) VALUES ('la-liga', false, 'La Liga', '2023', true);
INSERT INTO tb_league (endpoint, is_custom, name, season, is_active) VALUES ('serie-a', false, 'Serie A', '2023', true);
INSERT INTO tb_league (endpoint, is_custom, name, season, is_active) VALUES ('bundesliga', false, 'Bundesliga', '2023', true);
INSERT INTO tb_league (endpoint, is_custom, name, season, is_active) VALUES ('ligue-1', false, 'Ligue 1', '2023', true);
INSERT INTO tb_league (endpoint, is_custom, name, season, is_active) VALUES ('eredivise', false, 'Eredivise', '2023', true);
INSERT INTO tb_league (endpoint, is_custom, name, season, is_active) VALUES ('liga-portugal', false, 'Liga Portugal', '2023', true);
INSERT INTO tb_league (endpoint, is_custom, name, season, is_active) VALUES ('eurocopa', false, 'Eurocopa', '2024', true);
INSERT INTO tb_league (endpoint, is_custom, name, season, is_active) VALUES ('world-cup', false, 'Copa do Mundo', '2022', true);

INSERT INTO tb_sweepstake (name, is_private, league_id) VALUES ('UCL 23/24', false, 3);

INSERT INTO tb_participant (user_id, sweepstake_id, role, last_access) VALUES (1, 1, 2, NOW());
INSERT INTO tb_participant (user_id, sweepstake_id, role, last_access) VALUES (2, 1, 0, NOW());
INSERT INTO tb_participant (user_id, sweepstake_id, role, last_access) VALUES (3, 1, 0, NOW());
INSERT INTO tb_participant (user_id, sweepstake_id, role, last_access) VALUES (4, 1, 0, NOW());
INSERT INTO tb_participant (user_id, sweepstake_id, role, last_access) VALUES (5, 1, 0, NOW());

INSERT INTO tb_external_bet (user_id, sweepstake_id, external_match_id, home_team_score, away_team_score) VALUES (1,1,451613,1,0);
INSERT INTO tb_external_bet (user_id, sweepstake_id, external_match_id, home_team_score, away_team_score) VALUES (2,1,451613,0,1);
INSERT INTO tb_external_bet (user_id, sweepstake_id, external_match_id, home_team_score, away_team_score) VALUES (3,1,451613,2,0);
INSERT INTO tb_external_bet (user_id, sweepstake_id, external_match_id, home_team_score, away_team_score) VALUES (4,1,451613,1,2);
INSERT INTO tb_external_bet (user_id, sweepstake_id, external_match_id, home_team_score, away_team_score) VALUES (5,1,451613,1,1);

INSERT INTO tb_external_bet (user_id, sweepstake_id, external_match_id, home_team_score, away_team_score) VALUES (1,1,451615,3,1);
INSERT INTO tb_external_bet (user_id, sweepstake_id, external_match_id, home_team_score, away_team_score) VALUES (2,1,451615,3,0);
INSERT INTO tb_external_bet (user_id, sweepstake_id, external_match_id, home_team_score, away_team_score) VALUES (3,1,451615,0,1);
INSERT INTO tb_external_bet (user_id, sweepstake_id, external_match_id, home_team_score, away_team_score) VALUES (4,1,451615,2,1);
INSERT INTO tb_external_bet (user_id, sweepstake_id, external_match_id, home_team_score, away_team_score) VALUES (5,1,451615,1,0);

INSERT INTO tb_external_bet (user_id, sweepstake_id, external_match_id, home_team_score, away_team_score) VALUES (1,1,451624,1,2);
INSERT INTO tb_external_bet (user_id, sweepstake_id, external_match_id, home_team_score, away_team_score) VALUES (2,1,451624,1,0);
INSERT INTO tb_external_bet (user_id, sweepstake_id, external_match_id, home_team_score, away_team_score) VALUES (3,1,451624,1,2);
INSERT INTO tb_external_bet (user_id, sweepstake_id, external_match_id, home_team_score, away_team_score) VALUES (4,1,451624,0,3);
INSERT INTO tb_external_bet (user_id, sweepstake_id, external_match_id, home_team_score, away_team_score) VALUES (5,1,451624,2,2);

INSERT INTO tb_external_bet (user_id, sweepstake_id, external_match_id, home_team_score, away_team_score) VALUES (1,1,451629,0,2);
INSERT INTO tb_external_bet (user_id, sweepstake_id, external_match_id, home_team_score, away_team_score) VALUES (2,1,451629,1,1);
INSERT INTO tb_external_bet (user_id, sweepstake_id, external_match_id, home_team_score, away_team_score) VALUES (3,1,451629,0,1);
INSERT INTO tb_external_bet (user_id, sweepstake_id, external_match_id, home_team_score, away_team_score) VALUES (4,1,451629,0,3);
INSERT INTO tb_external_bet (user_id, sweepstake_id, external_match_id, home_team_score, away_team_score) VALUES (5,1,451629,0,0);

INSERT INTO tb_external_bet (user_id, sweepstake_id, external_match_id, home_team_score, away_team_score) VALUES (1,1,451648,0,3);
INSERT INTO tb_external_bet (user_id, sweepstake_id, external_match_id, home_team_score, away_team_score) VALUES (2,1,451648,0,2);
INSERT INTO tb_external_bet (user_id, sweepstake_id, external_match_id, home_team_score, away_team_score) VALUES (3,1,451648,0,1);
INSERT INTO tb_external_bet (user_id, sweepstake_id, external_match_id, home_team_score, away_team_score) VALUES (4,1,451648,1,4);
INSERT INTO tb_external_bet (user_id, sweepstake_id, external_match_id, home_team_score, away_team_score) VALUES (5,1,451648,0,2);



