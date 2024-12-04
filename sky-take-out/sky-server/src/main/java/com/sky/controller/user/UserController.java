package com.sky.controller.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.properties.JwtProperties;
import com.sky.result.Result;
import com.sky.service.UserSercive;
import com.sky.utils.JwtUtil;
import com.sky.vo.UserLoginVO;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/user/user")
@Slf4j
@Api(tags = "C端-用户接口")
public class UserController {

    private static final String URL = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    UserSercive userSercive;

    @Autowired
    JwtProperties jwtProperties;

    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("微信登录,授权码：{}", userLoginDTO);

        // wx登录
        User user = userSercive.wxLoginWithCode(userLoginDTO);
        
        // 
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);

        
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(token)
                .build();

        return Result.success(userLoginVO);
    }
    
}
