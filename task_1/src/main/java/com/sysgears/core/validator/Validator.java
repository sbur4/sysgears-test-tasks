package com.sysgears.core.validator;

@FunctionalInterface
public interface Validator<T> {

    void isValid(T t);
}
