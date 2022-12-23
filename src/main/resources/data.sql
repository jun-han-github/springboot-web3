INSERT INTO wallet (id, address, balance) VALUES  (1, '0x123456', 50000);
INSERT INTO token (id, balance, symbol, wallet_id) VALUES (1, 50000, 'USDT', 1), (2, 10, 'ETH', 1);
INSERT INTO transaction (id, address, symbol, type, amount, price, status, timestamp)
    VALUES (1, '0x123456', 'ETHUSDT', 'Buy', 10, 1222.51, 'Success', '2022-12-23 00:00:01');