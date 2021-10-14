package io.farel.interview.config

import org.aeonbits.owner.Config
import org.aeonbits.owner.Config.*
import org.aeonbits.owner.ConfigFactory

val cfg: Env = ConfigFactory.create(Env::class.java)

@LoadPolicy(LoadType.MERGE)
@Sources("system:properties", "classpath:env.properties")
interface Env : Config {

    @Key("url")
    fun url(): String

    @Key("basic.auth.login")
    fun login(): String

    @Key("basic.auth.pass")
    fun pass(): String

    @Key("headless")
    fun headless(): Boolean
}