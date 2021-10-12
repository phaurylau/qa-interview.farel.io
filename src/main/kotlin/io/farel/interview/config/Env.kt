package io.farel.interview.config

import org.aeonbits.owner.Config
import org.aeonbits.owner.ConfigFactory

val cfg: Env = ConfigFactory.create(Env::class.java)

@Config.Sources("classpath:env.properties")
interface Env : Config {

    @Config.Key("url")
    fun url(): String

    @Config.Key("basic.auth.login")
    fun login(): String

    @Config.Key("basic.auth.pass")
    fun pass(): String
}