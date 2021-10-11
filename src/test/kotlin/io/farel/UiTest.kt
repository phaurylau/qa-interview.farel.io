package io.farel

import com.codeborne.selenide.Condition.*
import com.codeborne.selenide.Selenide
import io.farel.pages.factorialCalcPage
import io.farel.script.invoke
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class UiTest {

    @Test
    @DisplayName("test positive calculate factorial")
    fun test01() {
        val number = 6

        "Open factorial page" {
            factorialCalcPage {
                open()

                "Check UI elements" {
                    calcName.shouldHave(exactText("The greatest factorial calculator!"))
                    numberInput.shouldHave(attribute("placeholder", "Enter an integer"))
                    termsLink.shouldHave(text("Privacy")) //it's a bug - link text should be equal Terms and Conditions
                    privacyLink.shouldHave(text("Terms and Conditions")) //it's a bug - link text should be equal Privacy
                    servicesLink.shouldHave(text("Qxf2 Services"))
                    assertEquals(
                        "Factoriall",
                        Selenide.title(),
                        "Incorrect page title"
                    ) // bug - correct name Factorial
                }
            }
        }
        "Calculate factorial for number" {
            factorialCalcPage {
                numberInput.value = number.toString()
                calculateButton.click()
            }
        }
        "Check correct result input" {
            factorialCalcPage {
                resultLabel.shouldHave(text("The factorial of $number is: " + factorial(number)))
            }
        }
    }

    @DisplayName("test various input value")
    @ParameterizedTest
    @CsvSource(
        value = [
            "3 4, Please enter an integer",
            "170, The factorial of 170 is: 7.257415615307999e+306",
            "171, The factorial of 171 is: Infinity"
        ]
    )
    fun test02(value: String, message: String) {
        factorialCalcPage {
            open()
            calculateFactorialFor(value)
            resultLabel.shouldHave(text(message))
        }
    }
}