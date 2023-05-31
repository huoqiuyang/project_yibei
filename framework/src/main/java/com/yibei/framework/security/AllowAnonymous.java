package com.yibei.framework.security;

import org.springframework.web.bind.annotation.Mapping;
import java.lang.annotation.*;

@Inherited
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface AllowAnonymous {

}
