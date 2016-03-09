package com.jeff.game.pathfinder.util.option

/**
 * My bad attempt at copying scala option type.
 */
abstract class Option<T>() {

    protected var value: T? = null

    /**
    * Secondary constructor that takes a value.
     */
    internal constructor(value: T) : this() {
        this.value = value
    }

    abstract fun defined(): Boolean
    abstract fun get(): T

}

/**
 * Specialization of an Option with a value
 */
final class Some<T>(value: T) : Option<T>(value) {
    override fun defined(): Boolean = true
    override fun get(): T = value!!
}

/**
 * Specialization of an Option without a value.
 * @throws NullPointerException if get() is called.
 */
final class None<T>() : Option<T>() {
    override fun defined(): Boolean = false
    override fun get(): T = throw NullPointerException()
}

