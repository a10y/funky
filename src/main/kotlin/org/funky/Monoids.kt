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

fun Int.monoid(): Monoid<Int> {
    this.let {
        return object : Monoid<Int> {
            override val zero: Int = 0
            override fun append(other: Int): Int = it + other
        }
    }
}

fun Double.monoid(): Monoid<Double> {
    this.let {
        return object : Monoid<Double> {
            override val zero: Double = 0.0
            override fun append(other: Double): Double = it + other
        }
    }
}

fun String.monoid(): Monoid<String> {
    this.let {
        return object : Monoid<String> {
            override val zero: String = ""
            override fun append(other: String): String = it + other
        }
    }
}

fun <T> Collection<T>.monoid(): Monoid<Collection<T>> {
    this.let {
        return object : Monoid<Collection<T>> {
            override val zero: Collection<T> = listOf()
            override fun append(other: Collection<T>): Collection<T> {
                val copy = it.toMutableList()
                copy.addAll(other)
                return copy.toList()
            }
        }
    }
}
