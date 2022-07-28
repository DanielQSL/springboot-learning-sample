package com.qsl.java.core.function.base;

/**
 * @author DanielQSL
 */
@FunctionalInterface
public interface SupplierThrow<T> {

    T get() throws Exception;

}
