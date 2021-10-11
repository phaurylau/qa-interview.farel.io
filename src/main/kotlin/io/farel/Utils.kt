package io.farel

fun factorial(num: Int): String {
    var result = 1
    for (i in 2..num) result *= i
    return result.toString()
}