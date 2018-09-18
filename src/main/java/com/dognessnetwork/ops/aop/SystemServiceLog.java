package com.dognessnetwork.ops.aop;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface SystemServiceLog {
    //定义成员
    String decription() default "" ;
    String actionType() default "" ;//操作的类型，1、添加 2、删除 3、修改 4、查询
    String tableName() default "";
}
