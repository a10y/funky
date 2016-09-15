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
import kotlin.test.assertTrue

class TestEither {
    @Test
    fun testUnwrap() {
        assertFailsWith<RuntimeException>("Cannot extract from Left") {
            leftOf(10).unwrap()
        }
        assertEquals(10, rightOf(10).unwrap())
    }

    @Test
    fun testEquality() {
        assertEquals(rightOf(10), rightOf(10))
        assertEquals(leftOf("Left"), leftOf("Left"))
    }

    @Test
    fun testFilterLeft() {
        val list = listOf(leftOf(1), leftOf(2), rightOf(3))
        val filtered = listOf(leftOf(1), leftOf(2))
        assertEquals(filtered, list.filterLeft())
    }

    @Test
    fun testFilterRight() {
        val list = listOf(leftOf(1), leftOf(2), rightOf(3))
        val filtered = listOf(rightOf(3))
        assertEquals(filtered, list.filterRight())
    }

    @Test
    fun testIsFuncs() {
        assertTrue {
            isLeft(leftOf(10))
        }

        assertTrue {
            isRight(rightOf(10))
        }
    }
}
