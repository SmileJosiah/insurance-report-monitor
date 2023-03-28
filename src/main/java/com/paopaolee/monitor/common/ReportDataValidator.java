package com.paopaolee.monitor.common;

/**
 * @author paopaolee
 */
public interface ReportDataValidator<T> {
    /**
     * 数据校验器
     * @param realData
     * @param reviewDate
     * @return boolean
     */
    boolean validate(T realData, T reviewDate);
}
