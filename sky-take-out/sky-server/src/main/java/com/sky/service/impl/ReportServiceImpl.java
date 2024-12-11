package com.sky.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 统计营业额
     * 
     * @param begin
     * @param end
     * @return
     */
    @Override
    public TurnoverReportVO getTurnOverStatistics(LocalDate begin, LocalDate end) {

        // 日期列表
        List<LocalDate> dateList = new ArrayList<>();
        while (!begin.isAfter(end)) {
            dateList.add(begin);
            begin = begin.plusDays(1);
        }

        // 遍历营业额
        List<Double> turnList = new ArrayList<>();
        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            Map<String, Object> map = new HashMap<>();
            map.put("begin", beginTime);
            map.put("end", endTime);
            map.put("status", Orders.COMPLETED);

            Double turnover = orderMapper.sumByMap(map);

            // 注意这里没有订单返回为空
            turnover = turnover == null ? 0 : turnover;
            turnList.add(turnover);
            
        }



        return TurnoverReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .turnoverList(StringUtils.join(turnList, ","))
                .build();
    }

}
