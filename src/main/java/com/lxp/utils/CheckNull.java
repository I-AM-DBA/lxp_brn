package com.lxp.utils;

import java.lang.reflect.Field;
import java.util.NoSuchElementException;

public class CheckNull {
    public static void checkExists(Object object, String msg) {
        if (object == null) {
            throw new NoSuchElementException(msg);
        }
    }

    public static void checkArgument(Object object, String msg) {
        if (object == null) {
            throw new IllegalArgumentException(msg);
        }

        if (isSimpleType(object)) {
            return;
        }

        // Reflection으로 객체 내부 필드 순회
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true); // private 필드 접근 허용
            try {
                if (field.get(object) == null) {
                    throw new IllegalArgumentException(field.getName() + " is null");
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // 단일 데이터 Null 체크
    private static boolean isSimpleType(Object object) {
        return object instanceof Number || object instanceof String || object instanceof Boolean;
    }
}
