-- populate DB with predifined stocks

INSERT INTO stocks (id, fixedDividend , lastDividend, operationalPrice, originalPrice, stockSymbol, type)
VALUES (1, null, 0, 0, 100, 'TEA', 0);

INSERT INTO stocks (id, fixedDividend , lastDividend, operationalPrice, originalPrice, stockSymbol, type)
VALUES (2, null, 8 , 0, 100, 'POP', 0);

INSERT INTO stocks (id, fixedDividend , lastDividend, operationalPrice, originalPrice, stockSymbol, type)
VALUES (3, null, 23, 0, 60, 'ALE', 0);

INSERT INTO stocks (id, fixedDividend , lastDividend, operationalPrice, originalPrice, stockSymbol, type)
VALUES (4, 2, 8, 0, 100, 'GIN', 1);

INSERT INTO stocks (id, fixedDividend , lastDividend, operationalPrice, originalPrice, stockSymbol, type)
VALUES (5, null, 13, 0, 250, 'JOE', 0);

INSERT INTO stocks (id, fixedDividend , lastDividend, operationalPrice, originalPrice, stockSymbol, type)
VALUES (6, null, null, null, null, 'BROKEN_STOCK', null);