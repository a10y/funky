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

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TestMaybe {
    @Test
    fun testDesugarSome() {
        val (num) = some(10)
        assertEquals(num, 10)
    }

    @Test
    fun testEquals() {
        // Two Nones of same type should be equal
        assert(none().equals(none()))
    }

    @Test
    fun testDesugarNone() {
        assertFailsWith(IllegalStateException::class, "Cannot desugar None to a value", {
            none().component1()
        })
    }

    @Test
    fun testFirstSome() {
        assertEquals(some(10), firstSome(none(), none(), some(10)))
    }

    @Test
    fun testFirstNone() {
        assertEquals(none(), firstSome(none(), none(), none()))
    }

    @Test
    fun testFromNullable() {
        assertEquals(some(10), fromNullable(10))
        assertEquals(none(), fromNullable(null))
    }

    @Test
    fun testUnwrap() {
        assertFailsWith<IllegalStateException>("Cannot extract value from None type") {
            none().unwrap()
        }
        assertEquals(10, some(10).unwrap())
    }

    @Test
    fun testFlatMap() {
        assertEquals(none(), none().flatMap { it })
        assertEquals(some(4), some(2).flatMap { it * 2 })
    }

    @Test
    fun testFilter() {
    }

    @Test
    fun testToString() {
        assertEquals("Some(4)", some(4).toString())
        assertEquals("None", none().toString())
    }
}
