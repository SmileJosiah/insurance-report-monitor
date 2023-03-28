package com.paopaolee.monitor.dao.ds2;

import com.paopaolee.monitor.bean.ReportDataStatistic;

/**
 * @author paopaolee
 */
public interface BaoyuntongDao {
    /**
     * 获取保运通数据
     * @return PremiumReportStatistic
     */
    ReportDataStatistic getReportDataStatistic();
}
