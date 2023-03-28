package com.paopaolee.monitor.dao.ds1;

import com.paopaolee.monitor.bean.ReportDataStatistic;

/**
 * @author paopaolee
 */
public interface TuanyixianDao {
    /**
     * 获取团意险数据
     * @return PremiumReportStatistic
     */
    ReportDataStatistic getReportDataStatistic();
}
