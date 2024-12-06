package com.sky.controller.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@RestController
@RequestMapping("user/shoppingCart")
@Api(tags = "C端购物车相关接口")
public class ShoppingCartController {

    @Autowired
    ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     * @param entity
     * @return
     */
    // TODO 缺少减去一个商品
    @ApiOperation("添加购物车")
    @PostMapping("/add")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("{}添加至购物车", shoppingCartDTO);

        shoppingCartService.addShoppingCart(shoppingCartDTO);
        
        return Result.success();
    }


    @ApiOperation("查看购物车")
    @GetMapping("list")
    public Result<List<ShoppingCart>> show() {
        log.info("查看购物车");

        List<ShoppingCart> list = shoppingCartService.showShoppingCart();

        return Result.success(list);
    }


    @ApiOperation("清空购物车！")
    @DeleteMapping("clean")
    public Result clean() {
        log.info("清空购物车！");

        shoppingCartService.cleanShoppingCart();

        return Result.success();
    }
    
    
}
