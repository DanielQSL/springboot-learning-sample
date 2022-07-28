package com.qsl.java.core.function.base;

/**
 * @author DanielQSL
 */
@FunctionalInterface
public interface SupplierVoidThrow {

    void get() throws Exception;

}
