package com.sky.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;





@RestController
@RequestMapping("admin/dish")
@Slf4j
@Api(tags = "菜品相关接口")
public class DishController {

    @Autowired
    DishService dishServiceImpl;

    @ApiOperation("新增菜品")
    @PostMapping
    public Result<Object> postMethodName(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品:{}", dishDTO);

        dishServiceImpl.saveWithFlavor(dishDTO);
        
        return Result.success();
    }

    @ApiOperation(value = "菜品分页查询")
    @GetMapping("/page")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页查询:{}", dishPageQueryDTO);

        PageResult dishes = dishServiceImpl.page(dishPageQueryDTO);

        return Result.success(dishes);
    }


    @ApiOperation(value = "修改菜品")
    @PutMapping
    public Result<Object> update(@RequestBody DishDTO dishDTO) {
        log.info("修改菜品：{}", dishDTO);

        dishServiceImpl.update(dishDTO);
        return Result.success();
    }
    
    
}
