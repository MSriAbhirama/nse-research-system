CREATE TABLE market_quote (
    id BIGSERIAL PRIMARY KEY,

    symbol VARCHAR(50) NOT NULL,
    instrument_key VARCHAR(100) NOT NULL,

    last_price NUMERIC(20, 4) NOT NULL,
    previous_close NUMERIC(20, 4) NOT NULL,
    open_price NUMERIC(20, 4) NOT NULL,
    day_high NUMERIC(20, 4) NOT NULL,
    day_low NUMERIC(20, 4) NOT NULL,

    volume BIGINT NOT NULL,

    fifty_two_week_high NUMERIC(20, 4) NOT NULL,
    fifty_two_week_low NUMERIC(20, 4) NOT NULL,

    last_updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uk_market_quote_symbol UNIQUE (symbol)
);


CREATE TABLE market_candle (
    id BIGSERIAL PRIMARY KEY,

    symbol VARCHAR(50) NOT NULL,
    interval VARCHAR(20) NOT NULL,
    candle_timestamp TIMESTAMP WITH TIME ZONE NOT NULL,

    open_price NUMERIC(20, 4) NOT NULL,
    high_price NUMERIC(20, 4) NOT NULL,
    low_price NUMERIC(20, 4) NOT NULL,
    close_price NUMERIC(20, 4) NOT NULL,

    volume BIGINT NOT NULL,

    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uk_market_candle UNIQUE (symbol, interval, candle_timestamp)
);