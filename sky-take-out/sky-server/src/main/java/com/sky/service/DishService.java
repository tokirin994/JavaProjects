package com.sky.service;

import java.util.List;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;



public interface DishService {

    /**
     * 新增菜品（带有口味）
     * @param dishDTO
     */
    public void saveWithFlavor(DishDTO dishDTO);

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    public PageResult page(DishPageQueryDTO dishPageQueryDTO);

    /*
     * 修改菜品
     */
    public void updateWithFlavor(DishDTO dishDTO);

    /**
     * 菜品批量删除
     * @param ids
     */
    public void deleteBatch(List<Long> ids);

    /**
     * 根据id查询菜品
     * @param id
     * @return
     */
    public DishVO getByIdWithFlavor(Long id);

    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    List<DishVO> listWithFlavor(Dish dish);

    /**
     * 启用禁用菜品
     * @param status
     * @param id
     */
    public void enableOrDisableDishById(Integer status, Long id);

    /**
     * 根据分类ID 查询菜品
     * @param categoryId
     * @return
     */
    public List<Dish> list(Long categoryId);
}
