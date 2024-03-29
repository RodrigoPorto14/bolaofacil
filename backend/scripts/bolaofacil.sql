-- H2 2.1.214;
;             
CREATE MEMORY TABLE "PUBLIC"."TB_BET"(
    "AWAY_TEAM_SCORE" INTEGER,
    "HOME_TEAM_SCORE" INTEGER,
    "SWEEPSTAKE_ID" BIGINT NOT NULL,
    "USER_ID" BIGINT NOT NULL,
    "MATCH_ID" BIGINT NOT NULL
);              
ALTER TABLE "PUBLIC"."TB_BET" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_9" PRIMARY KEY("MATCH_ID", "SWEEPSTAKE_ID", "USER_ID");     
-- 4 +/- SELECT COUNT(*) FROM PUBLIC.TB_BET;  
INSERT INTO "PUBLIC"."TB_BET" VALUES
(1, 4, 1, 1, 1),
(1, 2, 1, 2, 1),
(1, 1, 1, 1, 2),
(1, 1, 1, 2, 2);  
CREATE MEMORY TABLE "PUBLIC"."TB_EXTERNAL_BET"(
    "EXTERNAL_MATCH_ID" BIGINT NOT NULL,
    "AWAY_TEAM_SCORE" INTEGER,
    "HOME_TEAM_SCORE" INTEGER,
    "SWEEPSTAKE_ID" BIGINT NOT NULL,
    "USER_ID" BIGINT NOT NULL
);            
ALTER TABLE "PUBLIC"."TB_EXTERNAL_BET" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_7" PRIMARY KEY("EXTERNAL_MATCH_ID", "SWEEPSTAKE_ID", "USER_ID");   
-- 25 +/- SELECT COUNT(*) FROM PUBLIC.TB_EXTERNAL_BET;        
INSERT INTO "PUBLIC"."TB_EXTERNAL_BET" VALUES
(1184, 3, 0, 4, 1),
(1184, 2, 0, 4, 2),
(1184, 4, 1, 4, 3),
(1184, 1, 0, 4, 4),
(1184, 1, 2, 4, 5),
(1183, 1, 3, 4, 1),
(1183, 2, 2, 4, 2),
(1183, 1, 2, 4, 3),
(1183, 1, 1, 4, 4),
(1183, 1, 0, 4, 5),
(1182, 2, 1, 4, 1),
(1182, 2, 1, 4, 2),
(1182, 0, 1, 4, 3),
(1182, 1, 0, 4, 4),
(1182, 1, 1, 4, 5),
(1181, 2, 0, 4, 1),
(1181, 0, 1, 4, 2),
(1181, 1, 1, 4, 3),
(1181, 1, 0, 4, 4),
(1181, 2, 2, 4, 5),
(1180, 1, 3, 4, 1),
(1180, 0, 1, 4, 2),
(1180, 0, 1, 4, 3),
(1180, 1, 2, 4, 4),
(1180, 0, 0, 4, 5);    
CREATE MEMORY TABLE "PUBLIC"."TB_LEAGUE"(
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1 RESTART WITH 10) NOT NULL,
    "ENDPOINT" CHARACTER VARYING(255),
    "IS_CUSTOM" BOOLEAN NOT NULL,
    "NAME" CHARACTER VARYING(255)
);         
ALTER TABLE "PUBLIC"."TB_LEAGUE" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_95" PRIMARY KEY("ID");   
-- 9 +/- SELECT COUNT(*) FROM PUBLIC.TB_LEAGUE;               
INSERT INTO "PUBLIC"."TB_LEAGUE" VALUES
(1, '', TRUE, 'Personalizado'),
(2, 'cblol', FALSE, 'CBLOL'),
(3, 'ucl', FALSE, U&'Liga dos Campe\00f5es'),
(4, 'brasileiro-a', FALSE, U&'Brasileir\00e3o A'),
(5, 'premier-league', FALSE, 'Premier League'),
(6, 'la-liga', FALSE, 'La Liga'),
(7, 'serie-a', FALSE, 'Serie A'),
(8, 'bundesliga', FALSE, 'Bundesliga'),
(9, 'ligue-1', FALSE, 'Ligue 1'); 
CREATE MEMORY TABLE "PUBLIC"."TB_MATCH"(
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1 RESTART WITH 8) NOT NULL,
    "AWAY_TEAM_SCORE" INTEGER,
    "HOME_TEAM_SCORE" INTEGER,
    "START_MOMENT" TIMESTAMP,
    "TYPE" INTEGER,
    "AWAY_TEAM_ID" BIGINT,
    "HOME_TEAM_ID" BIGINT,
    "RULE_ID" BIGINT,
    "SWEEPSTAKE_ID" BIGINT
);          
ALTER TABLE "PUBLIC"."TB_MATCH" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_5" PRIMARY KEY("ID");     
-- 7 +/- SELECT COUNT(*) FROM PUBLIC.TB_MATCH;
INSERT INTO "PUBLIC"."TB_MATCH" VALUES
(1, 1, 2, TIMESTAMP '2023-01-20 13:00:00', 0, 2, 1, 1, 1),
(2, 1, 1, TIMESTAMP '2023-01-21 13:00:00', 0, 4, 3, 1, 1),
(3, 0, 3, TIMESTAMP '2023-01-22 13:00:00', 0, 3, 1, 1, 1),
(4, 0, 2, TIMESTAMP '2023-01-23 13:00:00', 0, 4, 2, 1, 1),
(5, 2, 2, TIMESTAMP '2023-01-23 13:00:00', 0, 4, 1, 1, 1),
(6, NULL, NULL, TIMESTAMP '2023-08-23 13:00:00', 0, 3, 2, 1, 1),
(7, NULL, NULL, TIMESTAMP '2023-09-23 13:00:00', 0, 2, 4, 1, 1);        
CREATE MEMORY TABLE "PUBLIC"."TB_PARTICIPANT"(
    "LAST_ACCESS" TIMESTAMP,
    "ROLE" INTEGER,
    "SWEEPSTAKE_ID" BIGINT NOT NULL,
    "USER_ID" BIGINT NOT NULL
);    
ALTER TABLE "PUBLIC"."TB_PARTICIPANT" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_2" PRIMARY KEY("SWEEPSTAKE_ID", "USER_ID");         
-- 10 +/- SELECT COUNT(*) FROM PUBLIC.TB_PARTICIPANT;         
INSERT INTO "PUBLIC"."TB_PARTICIPANT" VALUES
(TIMESTAMP '2023-01-10 13:00:00', 2, 4, 1),
(TIMESTAMP '2023-01-10 13:00:00', 0, 4, 2),
(TIMESTAMP '2023-01-10 13:00:00', 0, 4, 3),
(TIMESTAMP '2023-01-10 13:00:00', 0, 4, 4),
(TIMESTAMP '2023-01-10 13:00:00', 0, 4, 5),
(TIMESTAMP '2023-01-10 13:00:00', 2, 1, 1),
(TIMESTAMP '2023-01-10 09:00:00', 0, 1, 2),
(TIMESTAMP '2023-01-10 13:00:00', 0, 1, 3),
(TIMESTAMP '2023-01-10 13:00:00', 2, 2, 1),
(TIMESTAMP '2023-01-10 09:00:00', 2, 3, 2);
CREATE MEMORY TABLE "PUBLIC"."TB_REQUEST"(
    "SWEEPSTAKE_ID" BIGINT NOT NULL,
    "USER_ID" BIGINT NOT NULL
);           
ALTER TABLE "PUBLIC"."TB_REQUEST" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_5A" PRIMARY KEY("SWEEPSTAKE_ID", "USER_ID");            
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.TB_REQUEST;              
CREATE MEMORY TABLE "PUBLIC"."TB_RULE"(
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1 RESTART WITH 3) NOT NULL,
    "EXACT_SCORE" INTEGER,
    "LOSER_SCORE" INTEGER,
    "NAME" CHARACTER VARYING(255),
    "SCORE_DIFFERENCE" INTEGER,
    "WINNER" INTEGER,
    "WINNER_SCORE" INTEGER,
    "SWEEPSTAKE_ID" BIGINT
);             
ALTER TABLE "PUBLIC"."TB_RULE" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_D" PRIMARY KEY("ID");      
-- 2 +/- SELECT COUNT(*) FROM PUBLIC.TB_RULE; 
INSERT INTO "PUBLIC"."TB_RULE" VALUES
(1, 25, 12, 'Fase de Grupos', 15, 10, 18, 1),
(2, 25, 12, U&'Eliminat\00f3rias', 15, 10, 18, 1);      
CREATE MEMORY TABLE "PUBLIC"."TB_SWEEPSTAKE"(
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1 RESTART WITH 5) NOT NULL,
    "IS_PRIVATE" BOOLEAN NOT NULL,
    "NAME" CHARACTER VARYING(255),
    "LEAGUE_ID" BIGINT
);    
ALTER TABLE "PUBLIC"."TB_SWEEPSTAKE" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_E" PRIMARY KEY("ID");
-- 4 +/- SELECT COUNT(*) FROM PUBLIC.TB_SWEEPSTAKE;           
INSERT INTO "PUBLIC"."TB_SWEEPSTAKE" VALUES
(1, FALSE, 'Carioca 2023', 1),
(2, FALSE, 'CBLOL 2023', 2),
(3, FALSE, U&'Paulist\00e3o 2023', 1),
(4, FALSE, 'UCL 2023', 3); 
CREATE MEMORY TABLE "PUBLIC"."TB_TEAM"(
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1 RESTART WITH 5) NOT NULL,
    "IMG_URI" CHARACTER VARYING(255),
    "NAME" CHARACTER VARYING(255),
    "SWEEPSTAKE_ID" BIGINT
);   
ALTER TABLE "PUBLIC"."TB_TEAM" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_D1" PRIMARY KEY("ID");     
-- 4 +/- SELECT COUNT(*) FROM PUBLIC.TB_TEAM; 
INSERT INTO "PUBLIC"."TB_TEAM" VALUES
(1, 'https://upload.wikimedia.org/wikipedia/pt/thumb/a/ac/CRVascodaGama.png/180px-CRVascodaGama.png', 'Vasco', 1),
(2, 'https://upload.wikimedia.org/wikipedia/commons/thumb/2/2e/Flamengo_braz_logo.svg/180px-Flamengo_braz_logo.svg.png', 'Flamengo', 1),
(3, 'https://upload.wikimedia.org/wikipedia/commons/thumb/5/52/Botafogo_de_Futebol_e_Regatas_logo.svg/180px-Botafogo_de_Futebol_e_Regatas_logo.svg.png', 'Botafogo', 1),
(4, 'https://upload.wikimedia.org/wikipedia/pt/thumb/a/a3/FFC_escudo.svg/195px-FFC_escudo.svg.png', 'Fluminense', 1);          
CREATE MEMORY TABLE "PUBLIC"."TB_USER"(
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1 RESTART WITH 6) NOT NULL,
    "EMAIL" CHARACTER VARYING(255),
    "NICKNAME" CHARACTER VARYING(255),
    "PASSWORD" CHARACTER VARYING(255)
);      
ALTER TABLE "PUBLIC"."TB_USER" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_D18" PRIMARY KEY("ID");    
-- 5 +/- SELECT COUNT(*) FROM PUBLIC.TB_USER; 
INSERT INTO "PUBLIC"."TB_USER" VALUES
(1, 'rodrigo@gmail.com', 'Rodrigo', '$2a$10$RxTylgGR/e.3ernvqJxPTOCbRROqLOQB8vpG/y.6mFwX2ylIMt.8G'),
(2, 'larissa@gmail.com', 'Larissa', '$2a$10$RxTylgGR/e.3ernvqJxPTOCbRROqLOQB8vpG/y.6mFwX2ylIMt.8G'),
(3, 'junin@gmail.com', 'Junin', '$2a$10$RxTylgGR/e.3ernvqJxPTOCbRROqLOQB8vpG/y.6mFwX2ylIMt.8G'),
(4, 'joao@gmail.com', U&'Jo\00e3o', '$2a$10$RxTylgGR/e.3ernvqJxPTOCbRROqLOQB8vpG/y.6mFwX2ylIMt.8G'),
(5, 'ana@gmail.com', 'Ana', '$2a$10$RxTylgGR/e.3ernvqJxPTOCbRROqLOQB8vpG/y.6mFwX2ylIMt.8G');       
ALTER TABLE "PUBLIC"."TB_USER" ADD CONSTRAINT "PUBLIC"."UK_IG0BBYSXR6NNPXO4QN2BTDCC8" UNIQUE("NICKNAME");     
ALTER TABLE "PUBLIC"."TB_USER" ADD CONSTRAINT "PUBLIC"."UK_4VIH17MUBE9J7CQYJLFBCRK4M" UNIQUE("EMAIL");        
ALTER TABLE "PUBLIC"."TB_MATCH" ADD CONSTRAINT "PUBLIC"."FK8I4WA361BQXLVXDRVJN556U1C" FOREIGN KEY("HOME_TEAM_ID") REFERENCES "PUBLIC"."TB_TEAM"("ID") NOCHECK;
ALTER TABLE "PUBLIC"."TB_TEAM" ADD CONSTRAINT "PUBLIC"."FKNSMBE1XBWKK4NKCUFBAQEMTM" FOREIGN KEY("SWEEPSTAKE_ID") REFERENCES "PUBLIC"."TB_SWEEPSTAKE"("ID") NOCHECK;           
ALTER TABLE "PUBLIC"."TB_EXTERNAL_BET" ADD CONSTRAINT "PUBLIC"."FKHQXUHQA2LK5EV9Y6NEVK4F4P7" FOREIGN KEY("SWEEPSTAKE_ID") REFERENCES "PUBLIC"."TB_SWEEPSTAKE"("ID") NOCHECK;  
ALTER TABLE "PUBLIC"."TB_BET" ADD CONSTRAINT "PUBLIC"."FKDSLGBU024WY7198W9RR6OU4VH" FOREIGN KEY("USER_ID") REFERENCES "PUBLIC"."TB_USER"("ID") NOCHECK;       
ALTER TABLE "PUBLIC"."TB_EXTERNAL_BET" ADD CONSTRAINT "PUBLIC"."FKQKK9W2J5A2RT8JLEOQYQNRFN" FOREIGN KEY("USER_ID") REFERENCES "PUBLIC"."TB_USER"("ID") NOCHECK;               
ALTER TABLE "PUBLIC"."TB_BET" ADD CONSTRAINT "PUBLIC"."FKQLXB48MEOPFE3LQRB6Q89K3QL" FOREIGN KEY("SWEEPSTAKE_ID") REFERENCES "PUBLIC"."TB_SWEEPSTAKE"("ID") NOCHECK;           
ALTER TABLE "PUBLIC"."TB_PARTICIPANT" ADD CONSTRAINT "PUBLIC"."FK88HYNGQBFTW93BE04FG1UWL49" FOREIGN KEY("USER_ID") REFERENCES "PUBLIC"."TB_USER"("ID") NOCHECK;               
ALTER TABLE "PUBLIC"."TB_BET" ADD CONSTRAINT "PUBLIC"."FKJ2YGQOOTDF2BD5FJ6TMDS22ST" FOREIGN KEY("MATCH_ID") REFERENCES "PUBLIC"."TB_MATCH"("ID") NOCHECK;     
ALTER TABLE "PUBLIC"."TB_REQUEST" ADD CONSTRAINT "PUBLIC"."FK2DUTR78FOPHWDQ5WL67KATU1T" FOREIGN KEY("SWEEPSTAKE_ID") REFERENCES "PUBLIC"."TB_SWEEPSTAKE"("ID") NOCHECK;       
ALTER TABLE "PUBLIC"."TB_MATCH" ADD CONSTRAINT "PUBLIC"."FKHB04VPA021Y13WIOTGUX10YM4" FOREIGN KEY("SWEEPSTAKE_ID") REFERENCES "PUBLIC"."TB_SWEEPSTAKE"("ID") NOCHECK;         
ALTER TABLE "PUBLIC"."TB_MATCH" ADD CONSTRAINT "PUBLIC"."FK8AMNX6IOJUX7PV8RNFA5SC8PO" FOREIGN KEY("AWAY_TEAM_ID") REFERENCES "PUBLIC"."TB_TEAM"("ID") NOCHECK;
ALTER TABLE "PUBLIC"."TB_MATCH" ADD CONSTRAINT "PUBLIC"."FKNLOB6EEPN3AJ14B523IGGX23S" FOREIGN KEY("RULE_ID") REFERENCES "PUBLIC"."TB_RULE"("ID") NOCHECK;     
ALTER TABLE "PUBLIC"."TB_SWEEPSTAKE" ADD CONSTRAINT "PUBLIC"."FKDRFJ8CRFJDWU775KE1RA94MPD" FOREIGN KEY("LEAGUE_ID") REFERENCES "PUBLIC"."TB_LEAGUE"("ID") NOCHECK;            
ALTER TABLE "PUBLIC"."TB_PARTICIPANT" ADD CONSTRAINT "PUBLIC"."FKA5UYN8QYY22DUBRGVEKP46BYQ" FOREIGN KEY("SWEEPSTAKE_ID") REFERENCES "PUBLIC"."TB_SWEEPSTAKE"("ID") NOCHECK;   
ALTER TABLE "PUBLIC"."TB_REQUEST" ADD CONSTRAINT "PUBLIC"."FK8L5E93CDWML801JDK6CFO99VC" FOREIGN KEY("USER_ID") REFERENCES "PUBLIC"."TB_USER"("ID") NOCHECK;   
ALTER TABLE "PUBLIC"."TB_RULE" ADD CONSTRAINT "PUBLIC"."FK40UE64X0WDEHVRILVLM57PN2P" FOREIGN KEY("SWEEPSTAKE_ID") REFERENCES "PUBLIC"."TB_SWEEPSTAKE"("ID") NOCHECK;          
