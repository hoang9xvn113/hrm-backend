package com.datn.hrm.common.utils;

import com.datn.hrm.common.validator.ValidatorUtils;

public class GetterUtils {

    public static final String getString(String value) {

        return ValidatorUtils.isNull(value) ? "" : null;
    }

    public static final long getLong(Long value) {

        return ValidatorUtils.isNull(value) ? DefaultValue.LONG : value;
    }

    public static final int getInteger(Integer value) {

        return ValidatorUtils.isNull(value) ? DefaultValue.INTEGER : value;
    }

    public static final float getFloat(Float value) {

        return ValidatorUtils.isNull(value) ? DefaultValue.FLOAT : value;
    }

    public static final double getDouble(Double value) {

        return ValidatorUtils.isNull(value) ? DefaultValue.DOUBLE : value;
    }

    public static final boolean getBoolean(Boolean value) {

        return ValidatorUtils.isNull(value) ? DefaultValue.FALSE : value;
    }
}
