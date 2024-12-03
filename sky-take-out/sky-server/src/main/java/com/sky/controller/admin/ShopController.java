package com.sky.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.result.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Slf4j
@Api(tags = "店铺相关接口")
public class ShopController {

    final public static String KEY = "SHOP_STATUS"; 

    @Autowired
    RedisTemplate redisTemplate;

    @PutMapping("/{status}")
    @ApiOperation(value = "设置店铺状态")
    public Result<Object> setStatus(@PathVariable Integer status) {
        log.info("设置店铺状态为：{}", status==1?"营业中":"打样中");
        redisTemplate.opsForValue().set(KEY, status);
    
        return Result.success();
    }

    @GetMapping("/status")
    @ApiOperation(value = "获取店铺状态")
    public Result<Integer> getStatus() {
        Integer status = (Integer)redisTemplate.opsForValue().get(KEY);
        log.info("获取店铺状态：{}", status==1?"营业中":"打样中");

        return Result.success(status);
    }
    

}
