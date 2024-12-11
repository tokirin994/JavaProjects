package com.sky.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.result.Result;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;

@Api(tags = "数据统计相关接口")
@Slf4j
@RestController
@RequestMapping("/admin/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @ApiOperation("营业额统计")
    @GetMapping("/turnoverStatistics")
    public Result<TurnoverReportVO> turnoverStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("营业额统计，从{}至{}",begin, end);

        TurnoverReportVO turnoverReportVO = reportService.getTurnOverStatistics(begin, end);

        return Result.success(turnoverReportVO);
    }

}
