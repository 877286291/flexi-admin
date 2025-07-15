package top.houyuji.common.query.annotation;

public @interface LikeRight {

    /**
     * 注解的实体字段属性名称/字段column名称，默认为空或空字符串时将使用属性名称.
     *
     * @return 值
     */
    String value() default "";

    /**
     * 是否使用驼峰命名，默认为 {@code true}.
     *
     * @return 值
     */
    boolean underCamel() default true;
}
