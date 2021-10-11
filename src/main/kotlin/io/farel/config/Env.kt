package io.farel.config

import org.aeonbits.owner.Config

@Config.Sources("classpath:env.properties")
interface Env : Config {

    @Config.Key("url")
    fun url(): String

    @Config.Key("basic.auth.login")
    fun login(): String

    @Config.Key("basic.auth.pass")
    fun pass(): String
}