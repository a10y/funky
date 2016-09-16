/*
 * Copyright 2016 Andrew Duffy. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.aduffy.kdt

import java.util.*


sealed class Maybe<T> : Extract<T>, Functor<T> {
    class Some<T>(val value: T) : Maybe<T>()
    class None<T> : Maybe<T>()

    /**
     * Extractor for Maybe types
     */
    operator fun component1(): T = when(this) {
        is None -> throw IllegalStateException("Cannot desugar None to a value")
        is Some -> this.value
    }

    /**
     * Applies the given function to the contained value if Some, otherwise returns None
     */
    override fun <S> flatMap(f: (T) -> S): Maybe<S> = when(this) {
        is Maybe.Some -> some(f(this.value))
        else -> none()
    }

    override fun unwrap(): T = when(this) {
        is Some -> this.value
        is None -> throw IllegalStateException("Cannot extract value from None type")
    }

    override fun equals(other: Any?): Boolean = when(this) {
        is Some -> other is Some<*> && other.value == value
        is None -> true
    }

    override fun hashCode(): Int = when(this) {
        is Some -> Objects.hash(value)
        is None -> 0
    }

    override fun toString(): String = when(this) {
        is Some -> String.format("Some(%s)", value.toString())
        is None -> "None"
    }
}

/**
 * Creates a Maybe instance holding the given value.
 */
fun <T> some(value: T): Maybe<T> = Maybe.Some(value)

/**
 * Creates an empty Maybe instance.
 */
fun <T> none(): Maybe<T> = Maybe.None()

/**
 * Converts a nullable type into a Maybe, mapping null -> None and non-null values to Some(value).
 */
fun <T> fromNullable(value: T?): Maybe<T> = if (value === null) none() else some(value)

/**
 * Finds the first Some entry, or None if they are all None.
 */
fun <T> firstSome(maybes: Collection<Maybe<T>>): Maybe<T> = maybes.find {
    when(it) {
        is Maybe.Some -> true
        else -> false
    }
} ?: none()

fun <T> firstSome(vararg maybes: Maybe<T>): Maybe<T> = firstSome(maybes.asList())

fun Collection<Maybe<*>>.filterSome(): List<Maybe<*>> = filter { it is Maybe.Some }
fun Collection<Maybe<*>>.filterNone(): List<Maybe<*>> = filter { it is Maybe.None }