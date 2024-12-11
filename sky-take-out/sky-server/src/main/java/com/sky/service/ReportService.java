package com.sky.service;

import java.time.LocalDate;

import com.sky.vo.TurnoverReportVO;

public interface ReportService {

    /**
     * 统计营业额
     * @param begin
     * @param end
     * @return
     */
    TurnoverReportVO getTurnOverStatistics(LocalDate begin, LocalDate end);

}
