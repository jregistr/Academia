package com.jeff.chaser.util.option

interface Option<T> {
    fun defined(): Boolean
    fun get(): T
}

/**
 * Specialization of an Option with a value
 */
final class Some<T>(private val value: T) : Option<T> {
    override fun defined(): Boolean = true
    override fun get(): T = value
}

/**
 * Specialization of an Option without a value.
 * @throws NullPointerException if get() is called.
 */
final class None<T>() : Option<T>{
    override fun defined(): Boolean = false
    override fun get(): T = throw NullPointerException()
}
