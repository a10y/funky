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

package org.funky

/**
 * Wrapped defines a type which can be forcibly unwrapped to yield a value.
 * A good example of this is wrapper types for results, ex. Either or Maybe,
 * which can either contain a value or an error indicator. This method can
 * throw at runtime if unwrapping fails.
 */
interface Wrapped<out T> {
    fun unwrap(): T
}

/**
 * Functor is a group of elements of type T that can be mapped over.
 */
interface Functor<out T> {
    fun <S> flatMap(f: (T) -> S): Functor<S>
}

/**
 * Monoids are types that correspond to a set which contain a zero value,
 * as well as being closed over the addition operation.
 */
interface Monoid<T> {
    val zero: T // zero value for this monoid
    fun append(other: T): T // combiner for this monoid
}
