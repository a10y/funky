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

sealed class Either<out L, out R> : Extract<R> {
    class Left<L>(val value: L) : Either<L, Nothing>() {
        override fun unwrap(): Nothing = throw IllegalStateException("Cannot extract from Left")
        override fun equals(other: Any?): Boolean = other is Left<*> && value == other.value
        override fun hashCode(): Int = value?.hashCode() ?: 0
    }

    class Right<R>(val value: R): Either<Nothing, R>() {
        override fun unwrap(): R = value
        override fun equals(other: Any?): Boolean = other is Right<*> && value == other.value
        override fun hashCode(): Int = value?.hashCode() ?: 0
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
fun Collection<Either<*, *>>.filterLeft(): List<Either<*, *>> = this.filter { it is Either.Left }
fun Collection<Either<*, *>>.filterRight(): List<Either<*, *>> = this.filter { it is Either.Right }
