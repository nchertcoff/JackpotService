package com.sportygroup.jackpot.properties;

import java.math.BigDecimal;

public class JackpotProperties {
    private String type;
    private BigDecimal fixedPercentage;
    private BigDecimal maxPercentage;
    private BigDecimal minPercentage;
    private BigDecimal maxPool;
    private BigDecimal initialAmount;

    // getters y setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getFixedPercentage() {
        return fixedPercentage;
    }

    public void setFixedPercentage(BigDecimal fixedPercentage) {
        this.fixedPercentage = fixedPercentage;
    }

    public BigDecimal getMaxPercentage() {
        return maxPercentage;
    }

    public void setMaxPercentage(BigDecimal maxPercentage) {
        this.maxPercentage = maxPercentage;
    }

    public BigDecimal getMinPercentage() {
        return minPercentage;
    }

    public void setMinPercentage(BigDecimal minPercentage) {
        this.minPercentage = minPercentage;
    }

    public BigDecimal getMaxPool() {
        return maxPool;
    }

    public void setMaxPool(BigDecimal maxPool) {
        this.maxPool = maxPool;
    }

    public BigDecimal getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(BigDecimal initialAmount) {
        this.initialAmount = initialAmount;
    }
}
