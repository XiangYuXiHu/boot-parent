package com.smile.source.code.anno;

import java.lang.annotation.*;

/**
 * @author 12780
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.FIELD})
public @interface Mapper {
}
