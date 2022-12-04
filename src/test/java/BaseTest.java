import entities.query;
import helpers.Helpers;
import org.awaitility.Awaitility;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.Matchers.is;

public class BaseTest {
    public String token;
    public String meEmail;
    public query.QueryLunch.Result.launch responseLaunch;
    public List<query.QueryLaunchesPageSize.Result.launches.launches_launches> responseLaunches;
    public query.BookTrips.Result.bookTrips bookingInfo;


    @BeforeClass
    public static void startingServerAndChecking() {
        step("Checking server is on", () -> {
            Awaitility.given().ignoreException(AssertionError.class)
                    .await().pollInterval(1, TimeUnit.SECONDS).atMost(10, TimeUnit.SECONDS)
                    .until(Helpers::checkServer,is(true)); });
    }

    @AfterClass
    public static void checkingServerIsDown() {
        step("Turning off server", () -> {
            try {
                Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
            } catch (Exception e) {
                e.printStackTrace();}
        });
        step("Checking server is off", () -> {
            Awaitility.given().ignoreException(AssertionError.class)
                    .await().pollInterval(1, TimeUnit.SECONDS).atMost(10, TimeUnit.SECONDS)
                    .until(Helpers::checkServer, is(false));
        });}
}
