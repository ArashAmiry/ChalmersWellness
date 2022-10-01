package com.example.chalmerswellness.Controllers.Dashboard;

public class DashboardQuotes {
    private static DashboardQuotes instance = null;
    private String motivationalQuote;
    private String sportsAndCompetitionQuote;

    private DashboardQuotes(String motivationalQuote, String sportsAndCompetitionQuote) {
        this.motivationalQuote = motivationalQuote;
        this.sportsAndCompetitionQuote = sportsAndCompetitionQuote;
    }

    public static DashboardQuotes getInstance(){
        if(instance == null){
            QuotesAPIConnector apiConnector = new QuotesAPIConnector();
            String motivationalQuote = apiConnector.getRandomQuoteAsStringFromAPI("motivational");
            String sportsAndCompetitionQuote = apiConnector.getRandomQuoteAsStringFromAPI("sports&competition");
            instance = new DashboardQuotes(motivationalQuote, sportsAndCompetitionQuote);
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
