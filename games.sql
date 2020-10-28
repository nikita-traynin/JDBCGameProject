CREATE SCHEMA games;
USE games;

CREATE TABLE Player(
	id BIGINT NOT NULL AUTO_INCREMENT,
    firstName VARCHAR(100) NOT NULL,
    lastName VARCHAR(100) NOT NULL,
    join_date DATE NOT NULL,
    email VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE Game (
	id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(200) NOT NULL,
    releaseDate DATE NOT NULL,
    version VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE GamesOwned (
	id BIGINT NOT NULL AUTO_INCREMENT,
    playerID BIGINT NOT NULL,
    gameID BIGINT NOT NULL,
    purchaseDate DATE NOT NULL,
    purchasePrice FLOAT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (playerID) REFERENCES Player(id),
    FOREIGN KEY (gameID) REFERENCES Game(id)
);

CREATE TABLE GamesPlayed (
	id BIGINT NOT NULL AUTO_INCREMENT,
    playerID BIGINT NOT NULL,
    gameID BIGINT NOT NULL,
    timeFinished DATE NOT NULL,
    score INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (playerID) REFERENCES Player(id),
    FOREIGN KEY (gameID) REFERENCES Game(id)
);

CREATE TABLE CreditCard (
	id BIGINT NOT NULL AUTO_INCREMENT,
    playerID BIGINT NOT NULL,
    ccName VARCHAR(100) NOT NULL,
    ccNumber VARCHAR(100) NOT NULL,
    securityCode INT NOT NULL,
    expDate VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (playerID) REFERENCES Player(id)
);
