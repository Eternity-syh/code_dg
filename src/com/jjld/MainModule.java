package com.jjld;

import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Localization;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.filter.CrossOriginFilter;
import org.nutz.mvc.ioc.provider.ComboIocProvider;


@Localization(value="msg/", defaultLocalizationKey="zh-CN")
@IocBy(type=ComboIocProvider.class, args={"*js", "ioc/",
                    // 这个package下所有带@IocBean注解的类,都会登记上
                                        "*anno", "com.jjld",
                                        "*tx", // 事务拦截 aop
                                        "*async"}) // 异步执行aop
@Modules(scanPackage=true)
@Ok("json:full")
@Fail("jsp:jsp.500")
public class MainModule {

}
