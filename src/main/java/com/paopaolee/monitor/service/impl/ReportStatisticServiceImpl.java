package com.paopaolee.monitor.service.impl;

import com.paopaolee.monitor.bean.ReportDataStatistic;
import com.paopaolee.monitor.dao.ds2.BaoyuntongDao;
import com.paopaolee.monitor.dao.ds1.GexianDao;
import com.paopaolee.monitor.dao.ds1.TuanyixianDao;
import com.paopaolee.monitor.service.ReportStatisticService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author paopaolee
 */
@Service
public class ReportStatisticServiceImpl implements ReportStatisticService {
    @Resource
    private GexianDao gexianDao;

    @Resource
    private TuanyixianDao tuanyixianDao;

    @Resource
    private BaoyuntongDao baoyuntongDao;

    @Override
    public ReportDataStatistic getGexianStatistic() {
        return gexianDao.getReportDataStatistic();
    }

    @Override
    public ReportDataStatistic getTuanyixianStatistic() {
        return tuanyixianDao.getReportDataStatistic();
    }

    @Override
    public ReportDataStatistic getBaoyuntongStatistic() {
        return baoyuntongDao.getReportDataStatistic();
    }
}
