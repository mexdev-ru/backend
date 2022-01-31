package ru.mexdev.application.util;

public interface LinkUtils<T> {

    T addOperationWithLink(T clazz);

    T getOperationWithLink(T clazz);
}
