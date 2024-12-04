package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;

public interface UserSercive {

    /**
     * 使用用户的许可码登录微信服务
     * @param userLoginDTO
     * @return
     */
    User wxLoginWithCode(UserLoginDTO userLoginDTO);



}
