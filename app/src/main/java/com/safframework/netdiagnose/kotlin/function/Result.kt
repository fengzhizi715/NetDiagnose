package com.safframework.netdiagnose.kotlin.function

/**
 *
 * @FileName:
 *          com.safframework.netdiagnose.kotlin.function.Result
 * @author: Tony Shen
 * @date: 2020-01-21 15:33
 * @version: V1.0 <描述当前版本功能>
 */
sealed class Result<out T, out E>

data class Success<out T>(val value: T) : Result<T, Nothing>()
data class Failure<out E>(val reason: E) : Result<Nothing, E>()

inline fun <T> resultFrom(block: () -> T): Result<T, Exception> =
    try {
        Success(block())
    }
    catch (x: Exception) {
        Failure(x)
    }

inline fun <T, Tʹ, E> Result<T, E>.map(f: (T) -> Tʹ): Result<Tʹ, E> = flatMap { value -> Success(f(value)) }

inline fun <T, Tʹ, E> Result<T, E>.flatMap(f: (T) -> Result<Tʹ, E>): Result<Tʹ, E> =
    when (this) {
        is Success<T> -> f(value)
        is Failure<E> -> this
    }

fun <T> Result<T, T>.get() = when (this) {
    is Success<T> -> value
    is Failure<T> -> reason
}

