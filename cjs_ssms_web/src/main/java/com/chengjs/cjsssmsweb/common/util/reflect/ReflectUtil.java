package com.chengjs.cjsssmsweb.common.util.reflect;


import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ReflectUtil: 反射工具类
 *
 * @author: <a href="mailto:chengjs@servyou.com.cn">chengjs</a>
 * @version: 1.0.0, 2017-09-08
 *
 * ALL RIGHTS RESERVED,COPYRIGHT(C) FCH LIMITED Shanghai Servyou Ltd 2017
 **/
public class ReflectUtil {
    private static Logger log = Logger.getLogger(ReflectUtil.class);
    private final static String[] PROTOTYPE_CLASS_NAMES = new String[] { "java.lang.String",
        "java.lang.Character","java.lang.Integer",
        "java.lang.Object", "java.lang.Short", 
        "java.lang.Boolean", "java.lang.Byte", 
        "java.lang.Long","java.lang.Double", 
        "java.lang.Float", "int", "short", "char",
        "boolean", "byte", "long", "double", "float" };
    /**
     * 
    * @Title: loadClass 
    * @Description: 加载类
    * @param className 
    * @return Class  
    * @throws
     */
    @SuppressWarnings("unchecked")
    public static Class loadClass(String className) {
        Class clazz = null;
        try {
            clazz = Class.forName(className);
            clazz.newInstance();
        } catch (ClassNotFoundException e) {
            log.error(e);
        } catch (InstantiationException e) {
            log.error(e);
        } catch (IllegalAccessException e) {
            log.error(e);
        }
        return clazz;
    }
    /**
     * 
    * @Title: newInstance 
    * @Description: 创建类实例
    * @param clazz Class
    * @return Object  
    * @throws
     */
    @SuppressWarnings("unchecked")
    public static Object newInstance(Class clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            log.error(e);
        } catch (IllegalAccessException e) {
            log.error(e);
        }
        return null;
    }
    /**
     * 
    * @Title: newInstance 
    * @Description: 根据ClassName创建类实例
    * @param className String 
    * @return Object  
    * @throws
     */
    @SuppressWarnings("unchecked")
    public static Object newInstance(String className) {
        Class clazz = loadClass(className);
        return newInstance(clazz);
    }
    
    /**
     * 
    * @Title: gainField 
    * @Description: 获取clazz对象的名为propertyName的字段
    * @param clazz Class
    * @param propertyName 属性名
    * @return Field
    * @throws
     */
    @SuppressWarnings("unchecked")
    public static Field gainField(Class clazz, String propertyName) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(propertyName);
        } catch (SecurityException e) {
            log.error(e);
        } catch (NoSuchFieldException e) {
            log.error(e);
        }
        return field;
    }
    /**
     * 
    * @Title: gainFieldWithParent 
    * @Description: 递归获取父类的名为propertyName字段
    * @param clazz 
    * @param propertyName 
    * @return Field  
    * @throws
     */
    @SuppressWarnings("unchecked")
    public static Field gainFieldWithParent(Class clazz, String propertyName) {
        Field field = null;
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(propertyName);
                if (field != null)
                    return field;
            } catch (Exception e) {
                // 这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                // 如果这里的异常打印或者往外抛，则就不会执行clazz =
                // clazz.getSuperclass(),最后就不会进入到父类中了
            }
        }
        return field;
    }
    /**
     * 
    * @Title: printObject 
    * @Description: 打印对象
    * @param entity void  
    * @throws
     */
    public static void printObject(Object entity) {
        if (null == entity)
            return;
    }
    
    /**
     * 根据属性名获取属性值
     * 
     * @param obj 
     * @param property 
     * @return Object
     */
    @SuppressWarnings("unchecked")
    public static Object getProperty(Object obj, String property) {
        Class clazz = obj.getClass();
        String methodName = "get" + property.substring(0, 1).toUpperCase() + property.substring(1);
        Method method = null;
        Object rstObj = null;
        try {
            method = clazz.getDeclaredMethod(methodName, new Class[]{});
        } catch (SecurityException e1) {
            log.error(e1);
        } catch (NoSuchMethodException e1) {
            log.error(e1);
        } 
        if (null == method)
            return null;
        try {
            rstObj = method.invoke(obj, new Object[]{});
        } catch (IllegalArgumentException e) {
            log.error(e);
        } catch (IllegalAccessException e) {
            log.error(e);
        } catch (InvocationTargetException e) {
            log.error(e);
        }
        return rstObj;
    }
    /**
     * 
    * @Title: gainPropertyMap 
    * @Description: 获取属性,值 Map(传入任意对象，得到其属性名，值Map)
    * @param object 
    * @return Map   
    * @throws
     */
    @SuppressWarnings("unchecked")
    public static Map gainPropertyMap(Object object) {
        Field[] fields = listField(object);
        Map propertyMap = new HashMap();
        for (int i = 0; null != fields && i < fields.length; i++) {
            Field field = fields[i];
            Object value = null;
            String propertyName = field.getName();
            try {
                value = getProperty(object, propertyName);
            } catch (IllegalArgumentException e) {
                log.error(e);
            }
            propertyMap.put(propertyName, value);
        }
        return propertyMap;
    }
    
   /**
    * 
    * @Title: listField 
    * @Description: 罗列出对象的所有字段
    * @param object object
    * @return Field[]  
    * @throws
     */
    @SuppressWarnings("unchecked")
    public static Field[] listField(Object object) {
        return listField(object.getClass());
    }
    
    /**
     * 
    * @Title: listField 
    * @Description: 罗列出对象的所有字段
    * @param clazz clazz 
    * @return Field[]  
    * @throws
    */
    @SuppressWarnings("unchecked")
    public static Field[] listField(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        List fieldList = new ArrayList();
        for(int i = 0 ; null != fields && fields.length > 0 && i < fields.length; i ++){
            if(!"serialVersionUID".equalsIgnoreCase(fields[i].getName())){
                fieldList.add(fields[i]);
            }
        }
        return (Field[]) fieldList.toArray(new Field[0]);
    }
    
    /**
     * 是否为ArrayList类型
     * 
     * @param obj Object
     * @return boolean 是ArrayList类型返回true,否则返回false
     */
    @SuppressWarnings("unchecked")
    public static boolean isArrayListType(Object obj) {
        Class clazz = obj.getClass();
        return "java.lang.ArrayList".equals(clazz.getName());
    }
    /**
     * 
    * @Title: isBusinessElementType 
    * @Description: isBusinessElementType
    * @param obj Object
    * @return boolean 是BusinessElement类型返回true,不是返回false  
    * @throws
     */
    @SuppressWarnings("unchecked")
    public static boolean isBusinessElementType(Object obj) {
        Class clazz = obj.getClass();
        return "cn.com.servyou.wlfp.base.xml.entity.BusinessElement".equals(clazz.getName());
    }
    
    /**
     * 
    * @Title: isPrototype 
    * @Description: 是否为基本类型
    * @param className
    * @return boolean  
    * @throws
     */
    private static boolean isPrototype(String className) {
        for (int i = 0; i < PROTOTYPE_CLASS_NAMES.length; i++) {
            if (PROTOTYPE_CLASS_NAMES[i].equals(className)) {
                return true;
            }
        }
        return false;
    }
    
    /** 
    * @Title: isListNotNull 
    * @Description: 列表是否为空
    * @param list
    * @return boolean  
    * @throws 
    */
    @SuppressWarnings({ "unchecked", "unused" })
    private static boolean isListNotNull(List list) {
        return null != list && !list.isEmpty();
    }
}
