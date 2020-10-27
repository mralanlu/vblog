package com.northbund.vblog.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BeanUtilExtend {

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static void copyProperties(Object source, Object target, String... properties) {
        copyProperties(source, target, true, properties);
    }

    /**
     * 使用source properties 覆盖 target 的 properties已有的值
     * 必须匹配 property name，并且 source property type 能assign 给 target property type
     * source properties 必须有 getters, target properties必须有 setters
     *
     * Also see {@link org.springframework.beans.BeanUtils#copyProperties(Object, Object, String...)}
     *
     * @param source
     * @param target
     * @param includeNull true 表示复制null source properties，false 表示不复制null source properties
     * @param properties 指定的需要复制的source properties, 缺省则复制所有的source properties
     */
    public static void copyProperties(Object source, Object target, boolean includeNull, String... properties) {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        List<String> propertyList = (properties == null ? null : Arrays.asList(properties));

        PropertyDescriptor[] sourcePds = BeanUtils.getPropertyDescriptors(source.getClass());
        for (PropertyDescriptor sourcePd : sourcePds) {
            if (propertyList == null || propertyList.contains(sourcePd.getName())) {
                Method readMethod = sourcePd.getReadMethod();
                PropertyDescriptor targetPd = null;
                try {
                    // target property name 和 source property name 必须相同
                    targetPd = BeanUtils.getPropertyDescriptor(target.getClass(), sourcePd.getName()); // return null if there is no such descriptor
                    if (targetPd != null) {
                        Method writeMethod = targetPd.getWriteMethod();
                        // source properties 必须有 getter method,
                        // target properties必须有 setter
                        // source property type 能assign 给 target property type
                        if (readMethod != null && writeMethod != null
                                && ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object property = readMethod.invoke(source);
                            if (!includeNull && property == null) {
                                break;
                            }
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            writeMethod.invoke(target, property);
                        }
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    break;
                }
            }
        }
    }
}