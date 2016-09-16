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

import java.util.*

sealed class Either<out L, out R> : Extract<R>, Functor<R> {
    class Left<out L>(val value: L) : Either<L, Nothing>()

    class Right<out R>(val value: R): Either<Nothing, R>()

    override fun unwrap(): R = when(this) {
        is Left -> throw IllegalStateException("Cannot extract from Left")
        is Right -> value
    }

    override fun <S> flatMap(f: (R) -> S): Either<L, S> = when(this) {
        is Left -> leftOf(value)
        is Right -> rightOf(f(value))
    }

    operator fun component1(): R = when(this) {
        is Left -> throw IllegalStateException("Cannot desugar Left")
        is Right -> value
    }

    override fun equals(other: Any?): Boolean = when(this) {
        is Left -> other is Left<*> && value == other.value
        is Right -> other is Right<*> && value == other.value
    }

    override fun hashCode(): Int = when(this) {
        is Left -> Objects.hash(value)
        is Right -> Objects.hash(value)
    }

    override fun toString(): String = when(this) {
        is Left -> String.format("Left(%s)", value.toString())
        is Right -> String.format("Right(%s)", value.toString())
    }
}

/*
 * Constructors for Left and Right values
 */
fun <L> leftOf(left: L): Either<L, Nothing> = Either.Left(left)
fun <R> rightOf(right: R): Either<Nothing, R> = Either.Right(right)

/*
 * Checking methods for types
 */
fun isLeft(either: Either<*, *>): Boolean = either is Either.Left<*>
fun isRight(either: Either<*, *>): Boolean = either is Either.Right<*>

/*
 * Extension methods for Collections of Eithers.
 */
fun Collection<Either<*, *>>.filterLeft(): List<Either<*, *>> = filter { it is Either.Left }
fun Collection<Either<*, *>>.filterRight(): List<Either<*, *>> = filter { it is Either.Right }
