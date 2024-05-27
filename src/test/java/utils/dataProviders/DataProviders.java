package utils.dataProviders;

import org.testng.annotations.DataProvider;

public class DataProviders {

    @DataProvider(name = "multipleUsersProvider")
    public static Object[][] multipleUsersProvider() {
        return new Object[][] {
                { 1 },
                { 25 },
                { 100 },
                { 1000 }
        };
    }

    @DataProvider(name = "differentNationalitiesProvider")
    public static Object[][] differentNationalitiesProvider() {
        return new Object[][] {
                // nationality, number of users
                { "US", 5 },
                { "FI", 3 },
                { "AU", 1 },
                { "CA", 10 },
        };
    }
}
