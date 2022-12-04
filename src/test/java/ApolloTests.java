import entities.query.*;

import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

import static api.Constants.*;
import static helpers.Helpers.randomInt20;
import static io.qameta.allure.Allure.step;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ApolloTests extends BaseTest{
    //HERE YOU NEED TO START SERVER USING cd (here directory of apollo code)\final\\server && npm i && npm start
    //I couldn't handle to start server by itself before test =(

    @Test(description ="Verify login mutation works")
    public void loginMutationTokenCheck() {
        step("Sending email - getting token", () -> {
        token = Mutation.builder().withEmail(EMAIL).build().request(ENDPOINT)
                .post().getLogin().getToken();});
        step("Sending query me with token", () -> {
        meEmail = QueryMe.builder().build().request(ENDPOINT)
                .withHeader("Authorization",token).post().getMe().getEmail();});
        step("Checking email", () -> assertThat("Wrong email",EMAIL, is(meEmail)));
    }

    @Test(description ="Verify launch query works")
    public void verifyingLaunchQuery() {
        step("Sending launch query with id", () -> {
            responseLaunch = QueryLunch.builder("100").build().request(ENDPOINT).post().getLaunch();});
        step("Checking site", () -> assertThat("Wrong site",responseLaunch.getSite(), is(siteLaunchId100)));
        step("Checking mission", () -> assertThat("Wrong mission",responseLaunch.getMission().getName(), is(missionLaunchId100)));
        step("Checking rocket", () -> assertThat("Wrong rocket",responseLaunch.getRocket().getName(), is(rocketLaunchId100)));
    }

    @Test(description ="Verify pageSize input fields works")
    public void verifyingPageSizeFunction() {
        int number = randomInt20();
        step("Sending launch query with page size number", () -> {
            responseLaunches = QueryLaunchesPageSize.builder().withPageSize(number).build()
                .request(ENDPOINT).post().getLaunches().getLaunches();});
        step("Checking size number = list size", () -> assertThat("Wrong size",responseLaunches.size(), is(number)));
    }

    @Test(description ="Verify you can book trips for launches")
    public void verifyingBookTripsFunction() {
        List<String> launchId = new ArrayList<>();
        launchId.add("100");
        step("Sending email - getting token", () -> {
            token = Mutation.builder().withEmail(EMAIL).build().request(ENDPOINT)
                .post().getLogin().getToken();});
        step("Sending booking info Query with token and launchId", () -> {
            bookingInfo = BookTrips.builder(launchId).build().request(ENDPOINT)
               .withHeader("Authorization",token).post().getBookTrips();});
        step("Checking if booking success", () -> assertThat("Failure",bookingInfo.getSuccess(), is(true)));
    }
}


