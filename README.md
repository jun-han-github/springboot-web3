# Springboot-web3

### 1. Every 10 seconds, interval scheduler will retrieve the pricing from the source and store the best bid and ask price of ETHUSDT and BTCUSDT into the database.

### 2. User can buy or sell the supported crypto currency trading pairs, ETHUSDT and BTCUSDT.

<strong>POST /api/v1/transactions/buy </strong> <br>
example JsonNode Request:
<code>
{
    "wallet_id": 1,
    "symbol": "ETHUSDT",
    "base": "ETH",
    "quote": "USDT",
    "amount": 1.1
}
</code>

<strong>POST /api/v1/transactions/sell </strong> <br>
example JsonNode Request:
<code>
{
    "wallet_id": 1,
    "symbol": "BTCUSDT",
    "base": "BTC",
    "quote": "USDT",
    "amount": 2
}
</code>

### 3. Users will trade based on the latest best aggregated price.

### 4. Users can retrieve their crypto currencies wallet balance.

<strong> GET /api/v1/wallet/{wallet_id} </strong>
example JsonNode Response:
<code>
{
    "id": 1,
    "address": "0x123456",
    "balance": 50000.00
}
</code>

### 5. Users can retrieve their trading history.

<strong> GET /api/v1/transactions/{wallet_address} </strong> <br>
example JsonNode Response:
<code>
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
</code>
