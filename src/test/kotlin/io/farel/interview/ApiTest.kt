package io.farel.interview

import io.farel.factorial
import io.farel.interview.api.ApiClient
import io.farel.interview.api.requests.getFactorial
import io.farel.interview.config.cfg
import io.farel.interview.script.invoke
import io.qameta.allure.Feature
import io.restassured.authentication.BasicAuthScheme
import io.restassured.response.Response
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

const val STATUS_CODE_OK = 200
const val STATUS_CODE_INTERNAL_SERVER_ERROR = 500

@Feature("api tests")
@DisplayName("api tests suite")
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
    @ParameterizedTest(name = "call factorial endpoint with number {0}")
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
    @ParameterizedTest(name = "call factorial endpoint with number {0}")
    @ValueSource(ints = [-10, 979])
    fun test02(number: Int) {
        "Send POST request" {
            apiClient.send(getFactorial(number, STATUS_CODE_INTERNAL_SERVER_ERROR))
        }
    }
}