package com.sky.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.result.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController("userShopController")
@RequestMapping("/user/shop")
@Slf4j
@Api(tags = "店铺相关接口")
public class ShopController {

    final public static String KEY = "SHOP_STATUS";

    @Autowired
    private RedisTemplate redisTemplate;

    
    @GetMapping("/status")
    @ApiOperation(value = "获取店铺状态")
    public Result<Integer> getStatus() {
        Integer status = (Integer)redisTemplate.opsForValue().get(KEY);
        log.info("获取店铺状态：{}", status==1?"营业中":"打样中");

        return Result.success(status);
    }
    

}
