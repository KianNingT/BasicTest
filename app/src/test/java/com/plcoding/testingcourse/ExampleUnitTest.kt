package com.plcoding.testingcourse

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @BeforeEach
    fun setup() {
        /* setup specific resources before running test cases so that every test case start with a
        fresh state independent of other test cases*/
    }

    @AfterEach
    fun tearDown() {
        // free up specific resources after running test cases
    }

    @Test
    fun addition_isCorrect() {
        //assertEquals(4, 2 + 2)
        //use assert K library like below so it's more readable
        assertThat(2 + 2).isEqualTo(4)
    }
}