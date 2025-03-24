package com.fenixenforge.FenixenCore.Handlers.Yaml;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME) public @interface YData {
    String name();

    String folder() default "";
}