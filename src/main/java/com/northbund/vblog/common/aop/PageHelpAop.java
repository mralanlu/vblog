package com.northbund.vblog.common.aop;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.northbund.vblog.common.PageBean;
import com.northbund.vblog.common.annotation.PagingQuery;
import com.northbund.vblog.pojo.param.PageParam;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: lk
 **/
@Aspect
@Component
public class PageHelpAop {
    @Around("@annotation(pagingQuery)")
    public Object pagingQuery(ProceedingJoinPoint joinPoint, PagingQuery pagingQuery) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Class<?> returnType = signature.getMethod().getReturnType();
        if (returnType == PageBean.class) {
            //获取类名
            Map<String,Object> nameAndArgs = getFieldsName(joinPoint);
            String pageNum=null;
            String pageSize=null;
            String orderBy=null;
             for (Map.Entry<String, Object> m : nameAndArgs.entrySet()){
                 String key=m.getKey();
                 if(key.contains("aram")){
                     Object value=m.getValue();
                     PageParam pageParam=  JSON.parseObject(JSON.toJSONString(value),PageParam.class);
                     pageNum=pageParam.getPageNum().toString();
                     pageSize=pageParam.getPageSize().toString();
                     orderBy=pageParam.getOrderBy();
                 }
            }
            if (StringUtils.isNotBlank(pageNum) && StringUtils.isNotBlank(pageSize)) {
                    PageHelper.startPage(Integer.valueOf(pageNum), Integer.valueOf(pageSize));
                    if(StringUtils.isNotBlank(orderBy)){
                        PageHelper.orderBy(orderBy);
                    }
            }
        }
        return joinPoint.proceed();
    }

    /**
     * 获取参数列表
     *
     * @param joinPoint
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    private static Map<String, Object> getFieldsName(ProceedingJoinPoint joinPoint) {
        // 参数值
        Object[] args = joinPoint.getArgs();
        ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String[] parameterNames = pnd.getParameterNames(method);
        Map<String, Object> paramMap = new HashMap<>(32);
        for (int i = 0; i < parameterNames.length; i++) {
            paramMap.put(parameterNames[i], args[i]);
        }
        return paramMap;
    }

}
