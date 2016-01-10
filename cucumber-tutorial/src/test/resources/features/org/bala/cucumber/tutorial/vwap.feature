Feature: VWAP Calculation
	Scenario: Check average price calculated
		Given the list of Trades
		| productId | size  | price | timestamp  |
		| product01 |   0.0 |   0.0 | 1452451095 |
		| product01 |  11.0 | 100.5 | 1452452096 |
		| product01 | 200.0 | 100.1 | 1452453097 |
		| product01 |  31.0 | 100.4 | 1452454095 |
		| product01 |  41.0 | 100.3 | 1452455095 |
		| product01 | 100.0 | 100.2 | 1452456095 |
		When volume weighted average price is calculated
		Then it should be 100.183289