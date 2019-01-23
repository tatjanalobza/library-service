package com.example.libraryservice.util;

@FunctionalInterface
public interface Supplier<T> {

    T get() throws Exception;

}
