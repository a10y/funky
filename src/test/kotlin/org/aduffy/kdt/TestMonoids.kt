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

class TestMonoids {

    fun <T> summer(ms: Collection<Monoid<T>>): T = ms.fold(ms.first().zero, { acc, v -> v.append(acc) })

    @Test
    fun testInt() {
        val things = summer(listOf(1.monoid(), 2.monoid(), 3.monoid()))
        assertEquals(6, things)
    }

}