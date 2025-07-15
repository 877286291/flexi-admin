package top.houyuji.common.base.aspect;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import top.houyuji.common.base.utils.JsonUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@Aspect
public class ApiLogAspect {
    @Pointcut("execution(* top.houyuji.*.controller..*.*(..))")
    public void controller() {
    }

    @Around("controller()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // Get HttpServletRequest from the join point
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String requestMethod = request.getMethod();
        String urlPath = request.getRequestURI();
        String args = getMethodArgs(point);
        log.info("Request Method: {} Request URL: {} Request Parameters: {}", requestMethod, urlPath, args);
        long startTime = System.currentTimeMillis();
        Object result = point.proceed();
        long executionTime = System.currentTimeMillis() - startTime;
        log.info("API Execution Time: {} ms", executionTime);
        return result;
    }

    private String getMethodArgs(JoinPoint point) {
        Object[] args = point.getArgs();
        if (args == null || args.length == 0) {
            return "";
        }
        try {
            Map<String, Object> params = new HashMap<>();
            String[] parameterNames = ((MethodSignature) point.getSignature()).getParameterNames();
            for (int i = 0; i < parameterNames.length; i++) {
                Object arg = args[i];
                // 过滤不能转换成JSON的参数
                if ((arg instanceof ServletRequest) || (arg instanceof ServletResponse)) {
                    continue;
                } else if ((arg instanceof MultipartFile)) {
                    arg = arg.toString();
                }
                params.put(parameterNames[i], arg);
            }
            return JsonUtil.DEFAULT.toJson(params);
        } catch (Exception e) {
            log.error("接口出入参日志打印切面处理请求参数异常", e);
        }
        return Arrays.toString(args);
    }
}
