package io.farel.interview.api.requests

import io.farel.interview.api.Request

var getFactorial = fun(number: Int, httpStatusCode: Int) = "/factorial".let {
    Request(
        desc = "receive factorial for number",
        build = { setBody("number=$number") },
        send = { post(it) },
        expectedStatus = httpStatusCode
    )
}