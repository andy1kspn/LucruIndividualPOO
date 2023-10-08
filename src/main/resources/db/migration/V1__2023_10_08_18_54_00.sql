
CREATE TABLE utilizatori (
id INT PRIMARY KEY AUTO_INCREMENT,
nume VARCHAR(255),
nr_card VARCHAR(16),
pin VARCHAR(4)
);

CREATE TABLE balanta (
id INT PRIMARY KEY AUTO_INCREMENT,
id_utilizator INT,
balanta DECIMAL(10, 2),
FOREIGN KEY (id_utilizator) REFERENCES utilizatori(id)
);

CREATE TABLE tranzactii (
id INT PRIMARY KEY AUTO_INCREMENT,
id_utilizator INT,
data DATE,
tip VARCHAR(10), -- 'deposit' sau 'withdraw'
suma DECIMAL(10, 2),
FOREIGN KEY (id_utilizator) REFERENCES utilizatori(id)
);

CREATE TABLE mesaje (
id INT PRIMARY KEY AUTO_INCREMENT,
id_utilizator INT,
mesaj TEXT,
FOREIGN KEY (id_utilizator) REFERENCES utilizatori(id)
);