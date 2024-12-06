package com.sky.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;





@RestController
@RequestMapping("admin/dish")
@Slf4j
@Api(tags = "菜品相关接口")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("新增菜品")
    @PostMapping
    public Result<Object> postMethodName(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品:{}", dishDTO);

        dishService.saveWithFlavor(dishDTO);

        // 清理缓存
        String key = "dish_" + dishDTO.getCategoryId();
        cleanCache(key);
        
        return Result.success();
    }

    @ApiOperation(value = "菜品分页查询")
    @GetMapping("/page")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页查询:{}", dishPageQueryDTO);

        PageResult dishes = dishService.page(dishPageQueryDTO);

        return Result.success(dishes);
    }


    /**
     * 菜品批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("菜品批量删除")
    public Result delete(@RequestParam List<Long> ids) {
        log.info("菜品批量删除：{}", ids);
        dishService.deleteBatch(ids);

        // 这里是为简化查找每个dish的分类，所以全部删除
        // 将所有的菜品缓存数据清理掉，所有以dish_开头的key
        cleanCache("dish_*");

        return Result.success();
    }


    @ApiOperation(value = "根据id查询菜品")
    @GetMapping("/{id}")
    public Result<DishVO> getById(@PathVariable Long id) {
        log.info("根据id查询菜品，id {}", id);

        DishVO dishVO = dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }
    

    @ApiOperation(value = "修改菜品")
    @PutMapping
    public Result<Object> update(@RequestBody DishDTO dishDTO) {
        log.info("修改菜品：{}", dishDTO);

        // 这里是为简化查找每个dish的分类，所以全部删除
        // 将所有的菜品缓存数据清理掉，所有以dish_开头的key
        cleanCache("dish_*");

        dishService.updateWithFlavor(dishDTO);
        return Result.success();
    }


    @PostMapping("status/{status}")
    public Result<Object> enableOrDisableDishById(@PathVariable Integer status, Long id) {
        log.info("{}菜品{}", status==0?"禁用":"启用",  id);

        dishService.enableOrDisableDishById(status,id);

        //将所有的菜品缓存数据清理掉，所有以dish_开头的key
        cleanCache("dish_*");

        return Result.success();
    }


    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<Dish>> list(Long categoryId) {
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }
    


    private void cleanCache(String pattern) {
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);

    }
    
}
