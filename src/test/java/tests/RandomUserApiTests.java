package tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.dataProviders.DataProviders;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static utils.enums.HttpsStatusCodes.Code.*;

public class RandomUserApiTests extends TestBase {

    @Test
    public void testGetRandomUser() {
        Response response = given()
                .when().
                get("/");
        assertEquals(response.getStatusCode(), OK.getCode());
        Assert.assertFalse(response.jsonPath().getList("results").isEmpty());
    }

    @Test(dataProvider = "multipleUsersProvider", dataProviderClass = DataProviders.class)
    public void testGetMultipleUsers(int numbOfResults) {
        Response response = given()
                .queryParam("results", numbOfResults)
                .when()
                .get("/");
        assertEquals(response.getStatusCode(), OK.getCode());
        assertEquals(response.jsonPath().getList("results").size(), numbOfResults);
    }

    @Test
    public void testInvalidParam() {
        given()
                .queryParam("results", "invalid")
                .when()
                .get("/")
                .then()
                .statusCode(BAD_REQUEST.getCode());
    }

    @Test
    public void testInvalidEndpoint() {
        given()
                .when()
                .get("/invalid_endpoint")
                .then()
                .statusCode(NOT_FOUND.getCode());
    }

    @Test(dataProvider = "differentNationalitiesProvider", dataProviderClass = DataProviders.class)
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
