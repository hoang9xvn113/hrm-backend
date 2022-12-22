package com.datn.hrm.common.validator;

import com.datn.hrm.common.utils.DefaultValue;

import java.util.Objects;

public class ValidatorUtils {

    public static boolean isNull(String var) {

        return Objects.isNull(var) || var.equals(DefaultValue.EMPTY);
    }

    public static boolean isNotNull(String var) {

        return !isNull(var);
    }

    public static boolean isNull(Long var) {

        return Objects.isNull(var) || var.equals(DefaultValue.LONG);
    }

    public static boolean isNotNull(Long var) {

        return !isNull(var);
    }

    public static boolean isNull(Integer var) {

        return Objects.isNull(var) || var.equals(DefaultValue.INTEGER);
    }

    public static boolean isNotNull(Integer var) {

        return !isNull(var);
    }

    public static boolean isNull(Float var) {

        return Objects.isNull(var) || var.equals(DefaultValue.FLOAT);
    }

    public static boolean isNotNull(Float var) {

        return !isNull(var);
    }

    public static boolean isNull(Double var) {

        return Objects.isNull(var) || var.equals(DefaultValue.DOUBLE);
    }

    public static boolean isNotNull(Double var) {

        return !isNull(var);
    }

    public static boolean isNull(Boolean var) {

        return Objects.isNull(var) || var;
    }

    public static boolean isNotNull(Boolean var) {

        return !isNull(var);
    }

    public static boolean isNull(Object[] array) {

        return Objects.isNull(array) || array.length == 0;
    }

    public static boolean isNotNull(Object[] array) {

        return !isNull(array);
    }

    public static boolean isNull(Object array) {

        return Objects.isNull(array);
    }

    public static boolean isNotNull(Object array) {

        return !isNull(array);
    }
}
