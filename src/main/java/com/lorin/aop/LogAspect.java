package com.lorin.aop;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lorin.entity.Log;
import com.lorin.service.LogService;
import com.lorin.utils.HttpContextUtil;
import com.lorin.utils.IpUtils;
import com.lorin.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * TODO
 *
 * @author lorin
 * @date 2021/12/17 8:43
 */


@Aspect
@Component
@Slf4j
public class LogAspect {
    @Autowired
    LogService logService;

    @Pointcut("@annotation(com.lorin.aop.LogAnnotation)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //保存日志
        recordLog(point, time, result);
        return result;
    }

    private void recordLog(ProceedingJoinPoint joinPoint, long time, Object result) throws JsonProcessingException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        log.info("=====================log start================================");
        String module = logAnnotation.module();
        log.info("module: {}", module);
        String operation = logAnnotation.operation();
        log.info("operation: {}", operation);
        ObjectMapper mapper = new ObjectMapper();
        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.info("request-class-method: {}", className + "." + methodName + "()");

        //请求的参数
        Object[] args = joinPoint.getArgs();
        String params = "";
        try {
            params = mapper.writeValueAsString(args[0]);
            log.info("params: {}", params);
        } catch (Exception e) {
            log.info("params: {}", "null");
        }
        log.info("user:{}", UserUtil.getLoginUser());
        //获取request 设置IP地址
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        log.info("requests-methods: {}", request.getMethod());
        String ipAddress = IpUtils.getIpAddress(request);
        log.info("ip: {}", ipAddress);
        String source = IpUtils.getIpSource(ipAddress);
        if (source == "") {
            source = "局域网地址";
        }
        log.info("location: {}", source);
        UserAgent userAgent = IpUtils.getUserAgent(request);
        String browser = userAgent.getBrowser().toString();
        String os = userAgent.getOs().toString();
        log.info("ua: {}", os + " | " + browser);
        log.info("return: {}", result);
        log.info("excute-time : {} ms", time);
        log.info("=====================log end================================");
        Log myLog = new Log();
        myLog.setModule(module);
        myLog.setOperation(operation);
        myLog.setClassMethod(className + "." + methodName + "()");
        myLog.setPrams(params);
        myLog.setUsername(UserUtil.getLoginUser());
        myLog.setRequestMethod(request.getMethod());
        myLog.setIp(ipAddress);
        myLog.setLocation(source);
        myLog.setUa(os + " | " + browser);
        myLog.setRes(JSONUtil.toJsonStr(result));
        myLog.setExcuteTime(String.valueOf(time));
        myLog.setCreateTime(LocalDateTime.now());
        logService.insertLog(myLog);
    }
}
