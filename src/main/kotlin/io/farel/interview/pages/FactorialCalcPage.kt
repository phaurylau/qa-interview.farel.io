package io.farel.interview.pages

import com.codeborne.selenide.AuthenticationType
import com.codeborne.selenide.Selenide.element
import com.codeborne.selenide.Selenide.open
import io.farel.interview.config.cfg

val factorialCalcPage = FactorialCalcPage()

class FactorialCalcPage {

    val calcNameLabel = element("h1.text-center")

    val numberInput = element("input[id='number']")
    val calculateButton = element("button[id='getFactorial']")
    val resultLabel = element("p[id='resultDiv']")

    val privacyLink = element("a[href='/privacy']")
    val termsLink = element("a[href='/terms']")
    val servicesLink = element("a[href *= 'qxf2']")

    fun open() {
        open(cfg.url(), AuthenticationType.BASIC, cfg.login(), cfg.pass())
    }

    fun calculateFactorialFor(number: String) {
        numberInput.value = number
        calculateButton.click()
    }
}