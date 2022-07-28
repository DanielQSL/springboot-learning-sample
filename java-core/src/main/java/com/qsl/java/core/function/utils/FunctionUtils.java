package com.qsl.java.core.function.utils;

import com.qsl.java.core.function.base.SupplierThrow;
import com.qsl.java.core.function.base.SupplierVoidThrow;
import lombok.extern.slf4j.Slf4j;

/**
 * @author DanielQSL
 */
@Slf4j
public class FunctionUtils {

    private FunctionUtils() {
    }

    public static <T> T execute(String type, String name, SupplierThrow<T> supplier) throws Exception {
        T t;

        try {
            t = supplier.get();
        } catch (Exception ex) {
            throw ex;
        }
        return t;
    }

    public static <T> T executeNoThrow(String type, String name, SupplierThrow<T> supplier) {
        T t = null;

        try {
            t = supplier.get();

        } catch (Exception ex) {
            System.out.println("此处捕获做处理");
        }
        return t;
    }

    public static void execute(String type, String name, SupplierVoidThrow supplier) throws Exception {
        try {
            supplier.get();
        } catch (Exception ex) {
            throw ex;
        }
    }

}

