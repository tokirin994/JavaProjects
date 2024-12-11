package com.sky.service;

import java.time.LocalDate;

import javax.servlet.http.HttpServletResponse;

import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

public interface ReportService {

    /**
     * 统计营业额
     * @param begin
     * @param end
     * @return
     */
    TurnoverReportVO getTurnOverStatistics(LocalDate begin, LocalDate end);


    /**
     * 用户统计
     * @param begin
     * @param end
     * @return
     */
    UserReportVO getUserStatistics(LocalDate begin, LocalDate end);


    /**
     * 订单统计
     * @param begin
     * @param end
     * @return
     */
    OrderReportVO getOrderStatistics(LocalDate begin, LocalDate end);


    /**
     * 销量排名top10
     * @param begin
     * @param end
     * @return
     */
    SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end);


    /**
     * 导出运营数据报表
     * @param response
     *//**
     * 导出运营数据报表
     * @param response
     */
    void exportBusinessData(HttpServletResponse response);

}
