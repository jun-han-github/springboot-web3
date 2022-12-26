# Springboot-web3

### 1. Every 10 seconds, interval scheduler will retrieve the pricing from the source and store the best bid and ask price of ETHUSDT and BTCUSDT into the database.

### 2. User can buy or sell the supported crypto currency trading pairs, ETHUSDT and BTCUSDT.

POST /api/v1/transactions/buy 
example JsonNode:
{
    "wallet_id": 1,
    "symbol": "ETHUSDT",
    "base": "ETH",
    "quote": "USDT",
    "amount": 1.1
}

POST /api/v1/transactions/sell
example JsonNode:
{
    "wallet_id": 1,
    "symbol": "BTCUSDT",
    "base": "BTC",
    "quote": "USDT",
    "amount": 2
}

### 3. Users will trade based on the latest best aggregated price.

### 4. Users can retrieve their crypto currencies wallet balance.

GET /api/v1/wallet/{wallet_id}

### 5. Users can retrieve their trading history.

GET /api/v1/transactions/{wallet_address}
example JsonNode Response:
{
    "id": 1,
    "address": "0x123456",
    "symbol": "ETHUSDT",
    "type": "Buy",
    "amount": 1.00,
    "price": 1219.42,
    "source": "Houbi",
    "timestamp": "2022-12-26T20:54:04.044327"
}
