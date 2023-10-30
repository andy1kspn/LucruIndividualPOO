ALTER TABLE utilizatori ADD COLUMN balance DECIMAL(10, 2);
DROP TABLE balanta;
INSERT INTO `utilizatori` (`id`, `nume`, `nr_card`, `pin`, `balance`) VALUES ('1', 'demo', '0000000000000000', '0000', '1000.00');
INSERT INTO `mesaje` (`id`, `id_utilizator`, `mesaj`) VALUES ('1', '1', 'Mesaj Demo!');
INSERT INTO `tranzactii` (`id`, `id_utilizator`, `data`, `tip`, `suma`) VALUES ('1', '1', '2023-10-30', 'deposit', '1000.00');
