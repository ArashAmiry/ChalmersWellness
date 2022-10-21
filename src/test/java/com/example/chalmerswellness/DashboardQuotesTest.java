package com.example.chalmerswellness;

import com.example.chalmerswellness.Controllers.Dashboard.DashboardQuotes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DashboardQuotesTest {
    static DashboardQuotes dashboardQuotes;

    @BeforeAll
    static void setup() {
        dashboardQuotes = DashboardQuotes.getInstance();
    }

    @Test
    void getMotivationalQuoteMethodShouldReturnQuote() {
        Assertions.assertNotNull(dashboardQuotes.getMotivationalQuote());
    }

    @Test
    void getSportsAndCompetitionQuoteMethodShouldReturnQuote() {
        Assertions.assertNotNull(dashboardQuotes.getSportsAndCompetitionQuote());
    }

    @Test
    void getInstanceMethodShouldReturnTheSameInstance() {
        DashboardQuotes dashboardQuotes2 = DashboardQuotes.getInstance();
        Assertions.assertEquals(dashboardQuotes, dashboardQuotes2);
    }
}
