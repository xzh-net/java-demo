package net.xzh.interceptor.encrpyt;

import java.lang.annotation.*;

/**
 * 
 * @author Administrator
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface SensitiveFormat {
	String value() default "";
}
