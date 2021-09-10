package com.apih5.mybatis.pojo;

import java.math.BigDecimal;

public class ZjLzehWeather {

    //气温
    private BigDecimal airTemperature;
    //湿度
    private BigDecimal humidity;
    //风向
    private String windDirection;
    //pm2.5
    private BigDecimal pm25;

    public BigDecimal getAirTemperature() {
        return airTemperature;
    }

    public void setAirTemperature(BigDecimal airTemperature) {
        this.airTemperature = airTemperature;
    }

    public BigDecimal getHumidity() {
        return humidity;
    }

    public void setHumidity(BigDecimal humidity) {
        this.humidity = humidity;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public BigDecimal getPm25() {
        return pm25;
    }

    public void setPm25(BigDecimal pm25) {
        this.pm25 = pm25;
    }
}
