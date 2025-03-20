package com.fenixenforge.FenixenCore.Handlers.Yaml;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME) public @interface YamlData {
    String name();

    String folder() default "";
}