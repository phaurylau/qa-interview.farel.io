package io.farel.pages

import com.codeborne.selenide.AuthenticationType
import com.codeborne.selenide.Selenide
import io.farel.script.cfg

val factorialCalcPage = FactorialCalcPage()

class FactorialCalcPage {

    val calcName = Selenide.element("h1.text-center")

    val numberInput = Selenide.element("input[id='number']")
    val calculateButton = Selenide.element("button[id='getFactorial']")
    val resultLabel = Selenide.element("p[id='resultDiv']")

    val privacyLink = Selenide.element("a[href='/privacy']")
    val termsLink = Selenide.element("a[href='/terms']")
    val servicesLink = Selenide.element("a[href *= 'qxf2']")

    fun open() {
        Selenide.open(cfg.url(), AuthenticationType.BASIC, cfg.login(), cfg.pass())
    }

    fun calculateFactorialFor(number: String) {
        numberInput.value = number
        calculateButton.click()
    }
}