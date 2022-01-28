package dev.bituum.tinkoffmservice.enums;

public enum CandleInterval {
    CANDLE_INTERVAL_UNSPECIFIED("0"),
    SUBSCRIPTION_INTERVAL_ONE_MINUTE("1"),
    SUBSCRIPTION_INTERVAL_FIVE_MINUTES("5");

    private String interval;
    CandleInterval(String interval){
        this.interval = interval;
    }
}
