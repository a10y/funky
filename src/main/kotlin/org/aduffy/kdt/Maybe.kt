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

sealed class Maybe<T> : Extract<T> {
    class Some<T>(val value: T) : Maybe<T>() {
        override fun unwrap(): T = value
        override fun or(other: Maybe<T>): Maybe<T> = this
        override fun equals(other: Any?): Boolean = other is Some<*> && other.value == value
        override fun hashCode(): Int = value?.hashCode() ?: 0
    }
    class None<T>: Maybe<T>() {
        override fun unwrap(): T = throw IllegalStateException("Cannot extract value from None type")
        override fun or(other: Maybe<T>): Maybe<T> = other
        override fun equals(other: Any?): Boolean = other is None<*>
        override fun hashCode(): Int = 0
    }

    abstract fun or(other: Maybe<T>): Maybe<T>

    /**
     * Extractor for Maybe types
     */
    operator fun component1(): T = when(this) {
        is None -> throw IllegalStateException("Cannot desugar None to a value")
        is Some -> this.value
    }
}

/**
 * Creates a org.aduffy.kdt.Maybe instance for the given value.
 */
fun <T> some(value: T): Maybe<T> = Maybe.Some(value)

/**
 * Creates a org.aduffy.kdt.Maybe instance that holds nothing.
 */
fun <T> none(): Maybe<T> = Maybe.None()

/**
 * Converts a nullable type into a org.aduffy.kdt.Maybe, mapping null -> None and non-null values to Some(value).
 */
fun <T> fromNullable(value: T?): Maybe<T> = if (value === null) none() else some(value)

/**
 * Finds the org.aduffy.kdt.first non-None entry, or None if they are all org.aduffy.kdt.none.
 */
fun <T> first(vararg maybes: Maybe<T>): Maybe<T> {
    val result = maybes.find {
        when(it) {
            is Maybe.Some -> true
            else -> false
        }
    }
    return result ?: none()
}
