package com.sky.aspect;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut(){};

    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("开始公共字段填充...");

        // 反射，获取修饰符的值
        MethodSignature methodSignature= (MethodSignature)joinPoint.getSignature();
        AutoFill autoFill = methodSignature.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType = autoFill.value();

        // 这里做一个约定俗成，默认第一个为输入的参数
        Object[] args = joinPoint.getArgs();
        
        if (args == null || args.length ==0) {
            return;
        }
        
        Object entiy = args[0];

        LocalDateTime now = LocalDateTime.now();
        Long id = BaseContext.getCurrentId();

        if (operationType == OperationType.INSERT) {
            
            try {
                Method setCreateTime = entiy.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setCreateUser = entiy.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateTime = entiy.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entiy.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                setCreateTime.invoke(entiy, now);
                setCreateUser.invoke(entiy, id);
                setUpdateTime.invoke(entiy, now);
                setUpdateUser.invoke(entiy, id);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }else if ((operationType == OperationType.UPDATE)) {
            
            try {
                Method setUpdateTime = entiy.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entiy.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                setUpdateTime.invoke(entiy, now);
                setUpdateUser.invoke(entiy, id);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
        

}
