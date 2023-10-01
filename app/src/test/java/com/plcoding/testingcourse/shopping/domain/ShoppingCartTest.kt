package com.plcoding.testingcourse.shopping.domain

import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.corresponds
import assertk.assertions.doesNotCorrespond
import assertk.assertions.hasMessage
import assertk.assertions.isInstanceOf
import assertk.assertions.prop
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource


internal class ShoppingCartTest {

    private lateinit var shoppingCart: ShoppingCart

    @BeforeEach
    fun setup() {
        //create an instance of ShoppingCart
        shoppingCart = ShoppingCart()
    }

    @AfterEach
    fun tearDown() {

    }

    @Test
    fun `add single product with quantity -1 and throws exception only`() {
        val product1 = Product(-1, "product1", 11.0)

        //assertFailure shoppingCard.addProduct() throws exception only and doesn't specify what type of exception
        assertFailure {
            shoppingCart.addProduct(product1, -1)
        }
    }

    @Test
    fun `add single product with quantity -1 and throws exception with matching message`() {
        val product1 = Product(-1, "product1", 11.0)

        //assertFailure shoppingCard.addProduct() throws IllegalArgumentException with message "Quantity can't be negative"
        assertFailure {
            shoppingCart.addProduct(product1, -1)
        }.isInstanceOf(IllegalArgumentException::class)
            .prop(Exception::message).isEqualTo("Quantity can't be negative")
    }

    @Test fun `add single product with valid quantity`() {

        //Given
        val product1 = Product(1, "product1", 11.0)
        shoppingCart.addProduct(product1, 1)

        //When (action/ execution)
        val totalSum = shoppingCart.getTotalCost()

        //Assertion (then)
        assertThat(totalSum).isEqualTo(11.0)
    }

    @Test
    fun `add multiple product with correct total price sum`() {
        //given (setup)
        val product1 = Product(1, "product1", 11.0)
        val product2 = Product(2, "product2", 12.0)
        val product3 = Product(3, "product3", 13.0)
        shoppingCart.addProduct(product1, 3)
        shoppingCart.addProduct(product2, 4)
        shoppingCart.addProduct(product3, 5)

        //when (execution/ action)
        val totalCost = shoppingCart.getTotalCost()

        //then (assertion)
        assertThat(totalCost).isEqualTo((11.0 * 3) + (12.0 * 4) + (13.0 * 5))
    }

    /*repeated test are to make sure flaky test passes all the time
    places to run these flaky test are like coroutines and flows collected sometimes don't give correct results*/
    @RepeatedTest(10)
    fun `add two products and make sure total price sum is correct for all 10 times`() {
        //given (setup)
        val product1 = Product(1, "product1", 11.0)
        val product2 = Product(2, "product2", 12.0)
        shoppingCart.addProduct(product1, 3)
        shoppingCart.addProduct(product2, 4)

        //when (execution/ action)
        val totalCost = shoppingCart.getTotalCost()

        //then (assertion)
        assertThat(totalCost).isEqualTo((11.0 * 3) + (12.0 * 4))
    }


    //parameterized tests can also be repeated but with extra parameter(s) to test different scenarios
    @ParameterizedTest
    @ValueSource(
        ints = [1, 2, 3, 4, 5]
    )
    fun `run parameterized test with different quantity passed into function`(quantity: Int) {
        //Given
        val product = Product(1, "toy", 10.0)

        //When
        shoppingCart.addProduct(product, quantity)

        //then (assertion)
        assertThat(shoppingCart.getTotalCost()).isEqualTo(quantity * 10.0)

    }

    //parameterized tests can also be repeated but with extra parameter(s) to test different scenarios
    @ParameterizedTest
    @ValueSource(
        doubles = [1.0, 2.0, 3.0, 4.0, 5.0]
    )
    fun `run parameterized test with different price passed into function`(priceChange: Double) {
        //Given
        val product = Product(1, "toy", priceChange)

        //When
        shoppingCart.addProduct(product, 3)

        //then (assertion)
        assertThat(shoppingCart.getTotalCost()).isEqualTo(3 * priceChange)
    }

    //parameterized tests can also be repeated but with extra parameter(s) to test different scenarios
    @ParameterizedTest
    @CsvSource(
        "1,10.0",
        "2,20.0",
        "3,30.0",
        "4,40.0",
        "0,0.0"
    )
    //take note that in this CsvSource, the first parameter is the quantity in Int and the second parameter is the expected price sum in Double
    //therefore, the function parameter names must match the parameter names in the CsvSource with Int followed by Double
    fun `run parameterized test with different price passed into function1`(quantity: Int,
                                                                            expectedPriceSum: Double) {
        //Given
        val product = Product(1, "toy", 10.0)

        //When
        shoppingCart.addProduct(product, quantity)

        //then (assertion)
        assertThat(shoppingCart.getTotalCost()).isEqualTo(expectedPriceSum)
    }

    //don't need to write code to test private functions directly but write code that test public functions that call private functions
    @Test
    fun `test private function of is valid product returns invalid for non existent product`() {
        val product = Product(6, "cheese", 10.0)

        shoppingCart.addProduct(product, 2)

        assertThat(shoppingCart.getTotalCost()).isEqualTo(0.0)
    }




}