package sportsbot.model;

import sportsbot.enums.Sport;
import sportsbot.enums.TemporalContext;

/**
 * Created by devondapuzzo on 4/19/17.
 */
public class QuestionContext {

    private String question;
    private String response;
    private TemporalContext temporalContext;
    private Sport sport;
    private Game game;
    private Player player;
    private Team team;
    private QuestionContext previousQuestion;
    private boolean error =false;
    private String errorMessage;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public TemporalContext getTemporalContext() {
        return temporalContext;
    }

    public void setTemporalContext(TemporalContext temporalContext) {
        this.temporalContext = temporalContext;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public QuestionContext getPreviousQuestion() {
        return previousQuestion;
    }

    public void setPreviousQuestion(QuestionContext previousQuestion) {
        this.previousQuestion = previousQuestion;
    }
}
