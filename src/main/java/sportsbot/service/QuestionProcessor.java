package sportsbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sportsbot.enums.TemporalContext;
import sportsbot.model.Game;
import sportsbot.model.QuestionContext;
import sportsbot.model.Roster;
import sportsbot.model.Team;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by devondapuzzo on 4/24/17.
 */

@Service
public class QuestionProcessor {


    @Autowired
    private RosterService rosterService;

    private SportsApiService sportsApiRequester = new SportsApiService();

    private Roster roster;
    private HashMap<String, Team> teams;

    public QuestionContext answer(QuestionContext qr) {
        //update every time?
        roster = rosterService.getRoster();
        teams = roster.getTeams();

        String question = qr.getQuestion().toLowerCase();


        if(question.contains("today")){
            qr.setTemporalContext(TemporalContext.TODAY);
        }else if(question.contains("yesterday")){
            qr.setTemporalContext(TemporalContext.YESTERDAY);
        }else if(question.contains("tomorrow")){
            qr.setTemporalContext(TemporalContext.TOMORROW);
        }else{
            qr.setTemporalContext(TemporalContext.TODAY);
        }

        if(qr.getTemporalContext() != null){

            for(String teamName: teams.keySet()){
                Team team = teams.get(teamName);
                if(question.contains(team.getCity().toLowerCase()) || question.contains(team.getName().toLowerCase())){
                    qr.setTeam(team);
                    LinkedHashMap todaysGameMap = sportsApiRequester.getTodaysGame(qr);

                    if((Boolean) todaysGameMap.get("error")){
                        qr.setResponse((String) todaysGameMap.get("message"));
                        return qr;
                    }

                    Game thisGame = new Game();

                    thisGame.buildFromScoreboard(todaysGameMap, roster);
                    qr.setResponse(thisGame.generateGameStatusString(qr));

                    return qr;
                }
            }
            qr.setResponse("We couldn't find the team you were asking about");
        }else{
            qr.setResponse("Sorry, I'm not sure what you're asking.");
        }

        return qr;
    }

    public QuestionProcessor() {
    }
}
