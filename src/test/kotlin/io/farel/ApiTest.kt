package io.farel

import io.farel.api.ApiClient
import io.farel.api.requests.getFactorial
import io.farel.script.cfg
import io.farel.script.invoke
import io.restassured.authentication.BasicAuthScheme
import io.restassured.response.Response
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

const val STATUS_CODE_OK = 200
const val STATUS_CODE_INTERNAL_SERVER_ERROR = 500

class ApiTest {

    private val basicAuthScheme = BasicAuthScheme()
    private lateinit var apiClient: ApiClient
    private lateinit var response: Response

    @BeforeEach
    fun setUp() {
        basicAuthScheme.userName = cfg.login()
        basicAuthScheme.password = cfg.pass()

        "Create service api client" {
            apiClient = ApiClient(cfg.url(), basicAuthScheme)
        }
    }

    @DisplayName("test positive [POST] FACTORIAL endpoint")
    @ParameterizedTest
    @ValueSource(ints = [0, 1, 12])
    fun test01(number: Int) {
        "Send POST request" {
            response = apiClient.send(getFactorial(number, STATUS_CODE_OK))
        }
        "Check result value" {
            assertEquals(
                factorial(number), response.jsonPath().getString("answer"),
                "The factorial of $number was calculated incorrectly"
            )
        }
    }

    @DisplayName("test negative [POST] FACTORIAL endpoint")
    @ParameterizedTest
    @ValueSource(ints = [-10, 979])
    fun test02(number: Int) {
        "Send POST request" {
            apiClient.send(getFactorial(number, STATUS_CODE_INTERNAL_SERVER_ERROR))
        }
    }
}