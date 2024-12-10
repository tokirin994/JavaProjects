package com.sky.controller.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController("userOrderController")
@RequestMapping("/user/order")
@Slf4j
@Api(tags = "C端用户订单接口")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/submit")
    public Result<OrderSubmitVO> postMethodName(@RequestBody OrdersSubmitDTO ordersSubmitDTO) {
        log.info("用户下单", ordersSubmitDTO);

        OrderSubmitVO orderSubmitVO = orderService.submitOrder(ordersSubmitDTO);
        
        return Result.success(orderSubmitVO);
    }
    

}
