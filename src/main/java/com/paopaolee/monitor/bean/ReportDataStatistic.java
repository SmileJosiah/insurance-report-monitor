package com.paopaolee.monitor.bean;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author paopaolee
 */
public class ReportDataStatistic implements Serializable {
    private Integer realSettlementPrice;
    private Integer ecoPremium;

    public ReportDataStatistic(Integer realSettlementPrice, Integer ecoPremium) {
        this.realSettlementPrice = realSettlementPrice;
        this.ecoPremium = ecoPremium;
    }

    public Integer getRealSettlementPrice() {
        return realSettlementPrice;
    }

    public Integer getEcoPremium() {
        return ecoPremium;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReportDataStatistic that = (ReportDataStatistic) o;
        return Objects.equals(realSettlementPrice, that.realSettlementPrice) && Objects.equals(ecoPremium, that.ecoPremium);
    }

    @Override
    public int hashCode() {
        return Objects.hash(realSettlementPrice, ecoPremium);
    }

    @Override
    public String toString() {
        return "ReportDataStatistic{" +
                "realSettlementPrice=" + realSettlementPrice +
                ", ecoPremium=" + ecoPremium +
                '}';
    }
}
