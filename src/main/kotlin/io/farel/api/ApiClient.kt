package io.farel.api

import io.farel.script.invoke
import io.restassured.RestAssured
import io.restassured.authentication.BasicAuthScheme
import io.restassured.builder.RequestSpecBuilder
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.response.Response

class ApiClient(
    private val url: String,
    private val basicAuthScheme: BasicAuthScheme,
    private val specBuilder: RequestSpecBuilder = RequestSpecBuilder()
) {

    private val spec: RequestSpecBuilder
        get() {
            return specBuilder
                .setBaseUri(url)
                .addFilter(RequestLoggingFilter())
                .addFilter(ResponseLoggingFilter())
                .setContentType("application/x-www-form-urlencoded; charset=utf-8")
                .setAuth(basicAuthScheme)
        }

    fun <R : Response> send(request: Request<R>): R = send(request, spec)

    private fun <R : Response> send(
        request: Request<R>,
        specification: RequestSpecBuilder
    ): R {
        return "Send ${request.desc.ifEmpty { "request" }}" {
            try {
                request.build(specification)
                val response = request.send(RestAssured.given(specification.build()))
                response.then().statusCode(request.expectedStatus)
                response
            } finally {
            }
        }.output!!
    }
}
