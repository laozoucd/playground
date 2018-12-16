package com.stingerzou.common

sealed class BooleanExt<out T>

/*
 * Nothing has no instances. You can use Nothing to represent "a value that never exists": for example,
 * if a function has the return type of Nothing, it means that it never returns (always throws an exception).
 */
object Otherwise : BooleanExt<Nothing>()

class WithData<T>(val data: T) : BooleanExt<T>()

inline fun <T> Boolean.yes(block: () -> T) =
        when {
            this -> {
                WithData(block())
            }
            else -> {
                Otherwise
            }
        }

inline fun <T> Boolean.no(block: () -> T):BooleanExt<T> =
        when {
            this -> Otherwise
            else -> {
                WithData(block())
            }
        }

inline fun <T> BooleanExt<T>.otherwise(block: () -> T): T =
        when (this) {
            is Otherwise -> block()
            is WithData -> block()
        }