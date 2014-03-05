package com.augmentum.masterchef.vo;

import java.util.Date;

public class WalletVo  {

    long cash;
    long coin;
    long userId;
    long timestamp;

    public WalletVo() {
        this.timestamp = new Date().getTime();
    }

    public WalletVo(long cash, long coin) {
        super();
        this.cash = cash;
        this.coin = coin;
        this.timestamp = new Date().getTime();
    }

    public long getCash() {
        return cash;
    }

    public void setCash(long cash) {
        this.cash = cash;
    }

    public long getCoin() {
        return coin;
    }

    public void setCoin(long coin) {
        this.coin = coin;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
