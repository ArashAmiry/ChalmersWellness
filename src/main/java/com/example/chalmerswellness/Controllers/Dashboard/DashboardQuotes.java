package com.example.chalmerswellness.Controllers.Dashboard;

public class DashboardQuotes {
    private String motivationalQuote;
    private String sportsAndCompetitionQuote;

    private static DashboardQuotes instance = new DashboardQuotes();

    private DashboardQuotes() {
        QuotesAPIConnector apiConnector = new QuotesAPIConnector();
        this.motivationalQuote = apiConnector.getRandomQuoteAsStringFromAPI("motivational");
        this.sportsAndCompetitionQuote = apiConnector.getRandomQuoteAsStringFromAPI("sports&competition");
    }

    synchronized public static DashboardQuotes getInstance() {
        if (instance == null) {
            instance = new DashboardQuotes();
        }
        return instance;
    }

    public String getMotivationalQuote() {
        return motivationalQuote;
    }

    public String getSportsAndCompetitionQuote() {
        return sportsAndCompetitionQuote;
    }
}
