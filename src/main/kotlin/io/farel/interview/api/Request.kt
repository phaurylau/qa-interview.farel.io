package io.farel.interview.api

import io.restassured.builder.RequestSpecBuilder
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification

class Request<out R : Response>(
    var desc: String,
    val build: RequestSpecBuilder.() -> Unit = {},
    val send: RequestSpecification.() -> R,
    val expectedStatus: Int
)