package io.farel.interview

import com.codeborne.selenide.Condition.attribute
import com.codeborne.selenide.Condition.exactText
import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selenide
import io.farel.factorial
import io.farel.interview.config.cfg
import io.farel.interview.pages.factorialCalcPage
import io.farel.interview.script.invoke
import io.qameta.allure.Feature
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

@Feature("ui tests")
@DisplayName("ui tests suite")
class UiTest {

    @BeforeEach
    fun setUp() {
        Configuration.headless = cfg.headless()
    }

    @DisplayName("test positive calculate factorial")
    @ParameterizedTest
    @ValueSource(strings = ["chrome", "firefox"])
    fun test01(browserName: String) {
        Configuration.browser = browserName

        val number = 6
        "Open factorial page" {
            factorialCalcPage {
                open()

                "Check UI elements" {
                    calcNameLabel.shouldHave(exactText("The greatest factorial calculator!"))
                    numberInput.shouldHave(attribute("placeholder", "Enter an integer"))
                    termsLink.shouldHave(exactText("Privacy")) //it's a bug - link text should be equal Terms and Conditions
                    privacyLink.shouldHave(exactText("Terms and Conditions")) //it's a bug - link text should be equal Privacy
                    servicesLink.shouldHave(exactText("Qxf2 Services"))
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
                resultLabel.shouldHave(exactText("The factorial of $number is: " + factorial(number)))
            }
        }
    }

    @Disabled
    @DisplayName("test various input value")
    @ParameterizedTest(name = "factorial for number {0} is {1}")
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
            resultLabel.shouldHave(exactText(message))
        }
    }

    @AfterEach
    fun tearDown() {
        Selenide.closeWebDriver()
    }
}