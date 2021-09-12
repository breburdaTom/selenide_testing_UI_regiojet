import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.*;

/*
Create a test, that will test basic functionality of RegioJet system.

        Search for a direct connection between Ostrava and Brno using https://shop.regiojet.sk/ domain,
        departing on Monday and get additional information such as:
        • departure time
        • arrival time
        • number of stops
        • the price of the journey (accept value price from)

        At the same time make sure that all the connections that have been found meet the input criteria
        (departure time, place of the departure, place of the arrival and whether it is a direct or indirect
        connection).

        Create 3 cases which will choose optimal connection, based on the following criteria:
        a) the fastest arrival time
        b) the shortest time spent with travelling
        c) the lowest price of the journey
*/

public class regioJetTest {

    @BeforeEach
    public void SetUp() {
        Configuration.browser = "chrome";
        Configuration.startMaximized = true;
        Configuration.holdBrowserOpen = true;

    }

    @Test
    public void getFastestArrivalTime() {
        // fastest arrival time doesn't make any sense - only earliest arrival time exist OR the fastest connection (with the shortest time)
        // testing the earliest arrival time doesn't make sense because regiojet.sk only allows to query connections by date and not a specific hour
        // hence the first connection within a day will always have the earliest arrival time
        // for testing the fastest connection; with the shortest time there is the second test case

        open("https://shop.regiojet.sk/");
        $(byId("route-from")).setValue("Ostrava").pressEnter();
        $(byId("route-to")).setValue("Brno").pressEnter();
        $(byId("route-there-input")).click();
        $(byClassName("react-datepicker__month")).find("[aria-label^='Choose Monday']").click();
        $(byId("search-button")).click();

        $$(byClassName("connection-detail")).filterBy(text("Prestup: 0"))
                .first().find((byId("price-yellow-desktop"))).click();

    }

    @AfterEach
    public void tearDown() {
        closeWebDriver();
    }

}
