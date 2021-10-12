package io.farel.interview.script

operator fun <T : Any, R> T.invoke(script: T.() -> R): Invoke<T, R> =
    Invoke(this, script).invoke()

class Invoke<T : Any, R>(
    private val target: T,
    private val script: T.() -> R
) {

    var output: R? = null
        private set

    operator fun invoke(): Invoke<T, R> {
        output = script.invoke(target)
        return this
    }
}