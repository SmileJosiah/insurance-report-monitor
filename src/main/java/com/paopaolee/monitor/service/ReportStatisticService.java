package com.paopaolee.monitor.service;

import com.paopaolee.monitor.bean.ReportDataStatistic;

/**
 * @author paopaolee
 */
public interface ReportStatisticService {
    /**
     * 获取个险日报数据
     * @return PremiumReportStatistic
     */
    ReportDataStatistic getGexianStatistic();

    /**
     * 获取团意险日报数据
     * @return PremiumReportStatistic
     */
    ReportDataStatistic getTuanyixianStatistic();

    /**
     * 获取保运通日报数据
     * @return PremiumReportStatistic
     */
    ReportDataStatistic getBaoyuntongStatistic();
}
