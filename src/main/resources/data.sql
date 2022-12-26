INSERT INTO wallet (id, address, balance) VALUES  (1, '0x123456', 50000);
INSERT INTO token (id, balance, symbol, wallet_id) VALUES (1, 50000, 'USDT', 1);
DROP SEQUENCE token_sequence;
CREATE SEQUENCE token_sequence START WITH 2;
INSERT INTO transaction (id, address, symbol, type, amount, price, source, timestamp)
    VALUES (1, '0x123456', 'ETHUSDT', 'Buy', 10, 1222.51, 'Binance', '2022-12-23 00:00:01');
DROP SEQUENCE transaction_sequence;
CREATE SEQUENCE transaction_sequence START WITH 2;

