package tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestType;
import utils.dataProviders.DataProviders;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static utils.enums.HttpsStatusCodes.Code.*;

public class RandomUserApiTests extends TestBase {

    @Test(groups = {
            TestType.SANITY,
            TestType.REGRESSION},
            description = "Verify that a valid request to the base endpoint returns status code 200")
    public void testGetRandomUser() {
        Response response = given()
                .when().
                get("/");
        assertEquals(response.getStatusCode(), OK.getCode());
        Assert.assertFalse(response.jsonPath().getList("results").isEmpty());
    }

    @Test(groups = {TestType.REGRESSION},
            dataProvider = "multipleUsersProvider", dataProviderClass = DataProviders.class,
            description = "Check that the API returns the expected number of results as per request")
    public void testGetMultipleUsers(int numbOfResults) {
        Response response = given()
                .queryParam("results", numbOfResults)
                .when()
                .get("/");
        assertEquals(response.getStatusCode(), OK.getCode());
        assertEquals(response.jsonPath().getList("results").size(), numbOfResults);
    }

    @Test(groups = {TestType.REGRESSION},
            description = "Request with an invalid query returns status code 400")
    public void testInvalidParam() {
        given()
                .queryParam("results", "invalid")
                .when()
                .get("/")
                .then()
                .statusCode(BAD_REQUEST.getCode());
    }

    @Test(groups = {
            TestType.SANITY,
            TestType.REGRESSION},
            description = "Request to an invalid endpoint returns status code 404")
    public void testInvalidEndpoint() {
        given()
                .when()
                .get("/invalid_endpoint")
                .then()
                .statusCode(NOT_FOUND.getCode());
    }

    @Test(groups = {TestType.REGRESSION},
            dataProvider = "differentNationalitiesProvider", dataProviderClass = DataProviders.class,
            description = "Verify that filtering results by nationality returns the correct number of results and" +
                    "all returned results match the specified nationality")
    public void testFiltering(String nationality, int numbOfUsers) {
        Response response = given()
                .queryParam("results", numbOfUsers)
                .queryParam("nat", nationality)
                .when()
                .get("/");
        assertEquals(response.getStatusCode(), OK.getCode());

        // Asserting the number of results with the specified nationality
        List<Map<String, Object>> results = response.jsonPath().getList("results");
        long count = results.stream().filter(r -> r.get("nat").equals(nationality)).count();
        assertEquals(count, numbOfUsers, "Expected " + numbOfUsers + " results with nationality " + nationality);
    }

}
