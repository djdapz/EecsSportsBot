package sportsbot.service;

import java.util.*;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sportsbot.enums.TemporalContext;
import sportsbot.model.Game;
import sportsbot.model.QuestionContext;
import sportsbot.model.Team;
import sun.awt.image.ImageWatched;


/**
 * Created by devondapuzzo on 4/12/17.
 */

@Component
public class SportsApiService {


    private HttpHeaders createHeaders(final String username, final String password ){
        HttpHeaders headers =  new HttpHeaders(){
            {
                String auth = username + ":" + password;
                Base64.Encoder encoder = Base64.getEncoder();
                byte[] encodedAuth = encoder.encode(auth.getBytes());
                String authHeader = "Basic " + new String( encodedAuth );
                set( "Authorization", authHeader );
            }
        };
        headers.add("Content-Type", "application/xml");
        headers.add("Accept", "application/xml");

        return headers;
    }

    public ResponseEntity<Object> contactApi(String requestType, Integer offset){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = this.createHeaders("djdapz", "goCats");

        String dateString = this.getToday(offset);

        String urlString = "https://www.mysportsfeeds.com/api/feed/pull/mlb/current/"+requestType+".json?fordate="+dateString+"&force=true";
        return restTemplate.exchange(urlString, HttpMethod.GET, new HttpEntity<Object>(httpHeaders), Object.class);
    }

    public ResponseEntity<Object> contactApi(String requestType){
        return contactApi(requestType, 0);
    }


    public ArrayList<LinkedHashMap> getRostersFromAPI(){

        ResponseEntity<Object> response = this.contactApi("roster_players");

        LinkedHashMap<String, LinkedHashMap> roster = (LinkedHashMap<String, LinkedHashMap>) response.getBody();
        return (ArrayList<LinkedHashMap>) roster.get("rosterplayers").get("playerentry");
    }



    public LinkedHashMap getTodaysGame(QuestionContext questionContext){

        Team team = questionContext.getTeam();
        TemporalContext temporalContext = questionContext.getTemporalContext();
        ResponseEntity<Object> response;
        Integer offset = 0;
        LinkedHashMap<String, Object> errorHash = new LinkedHashMap<>();

        if(questionContext.getTemporalContext() == TemporalContext.YESTERDAY){
            offset = -1;
            response = this.contactApi("daily_game_schedule", offset);
        }else {
            if(questionContext.getTemporalContext() == TemporalContext.TOMORROW){
                offset = 1;
            }
            response = this.contactApi("scoreboard", offset);
        }

        if(response.getStatusCode().value() != 200){
            errorHash.put("error", true);
            errorHash.put("message", "ERROR: We were unable to get the information from our API. Please contact the administrators.");
            return errorHash;
        }

        LinkedHashMap<String, LinkedHashMap> responseResult = (LinkedHashMap<String, LinkedHashMap>) response.getBody();
        ArrayList<LinkedHashMap>  games = (ArrayList<LinkedHashMap>) responseResult.get("scoreboard").get("gameScore");

        for(int i = 0; i < games.size(); i++){
            LinkedHashMap todaysScore = games.get(i);
            LinkedHashMap todaysGame = (LinkedHashMap) todaysScore.get("game");
            LinkedHashMap homeTeamMap = (LinkedHashMap) todaysGame.get("awayTeam");
            LinkedHashMap awayTeamMap = (LinkedHashMap) todaysGame.get("homeTeam");

            String homeTeamAbbr = (String) homeTeamMap.get("Abbreviation");
            String awayTeamAbbr = (String) awayTeamMap.get("Abbreviation");

            if(team.getAbbreviation().equals(homeTeamAbbr) ||team.getAbbreviation().equals(awayTeamAbbr)){
                return todaysScore;
            }
        }

        errorHash.put("error", true);
        //TODO - adapt this message to the temporal context
        errorHash.put("message", "It looks like that team isn't playing today");
        return errorHash;
    }

    public String getToday(){
        return getToday(0);
    }

    public String getToday(Integer offset){
        Calendar now = Calendar.getInstance();

        Integer year = now.get(now.YEAR);
        Integer month = now.get(now.MONTH)+1;
        Integer day = now.get(now.DATE) + offset;

        if(now.get(now.HOUR_OF_DAY) < 3) {
            day--;
        }


        String yearS = Integer.toString(year);
        String monthS = Integer.toString(month);
        String dayS = Integer.toString(day);

        if(monthS.length() == 1){
            monthS = "0" + monthS;
        }

        if(dayS.length() == 1){
            dayS = "0" + dayS;
        }

        return yearS + monthS + dayS;

    }
}
