package com.sky.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserSercive;
import com.sky.utils.HttpClientUtil;





@Service
public class UserServiceImpl implements UserSercive{

    private static final String URL_WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties weChatProperties;

    @Autowired
    private UserMapper userMapper;

    /**
     * 使用用户的许可码登录微信服务
     * @param userLoginDTO
     * @return
     */
    @Override
    public User wxLoginWithCode(UserLoginDTO userLoginDTO) {
        
        String openId = getOpenId(userLoginDTO.getCode());

        if (openId == null) {
           throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        // 查询添加信息
        User user = userMapper.getByOpenId(openId);

        if (user == null) {
            user = User.builder()
                        .openid(openId)
                        .createTime(LocalDateTime.now())
                        .build();

            userMapper.insert(user);
        }
        
        return user;
        
    }

    private String getOpenId (String code) {
        // 请求参数
        Map<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");

        // 登录
        String doGet = HttpClientUtil.doGet(URL_WX_LOGIN, map);

        // 解析返回信息
        JSONObject jsonObject = JSON.parseObject(doGet);

        String openId = jsonObject.getString("openid");

        return openId;

    }


}
