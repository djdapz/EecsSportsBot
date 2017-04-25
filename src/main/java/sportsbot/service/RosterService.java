package sportsbot.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sportsbot.model.Roster;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by devondapuzzo on 4/24/17.
 */

@Service
public class RosterService {

    private Roster roster;

    private SportsApiService requester = new SportsApiService();

    public RosterService(){
        ArrayList<LinkedHashMap> rawRosters = requester.getRostersFromAPI();
        roster = new Roster();
        roster.addNewPlayers(rawRosters);
    }


    public Roster getRoster() {
        if(roster != null){
            return roster;
        }else{
            return null;
        }
    }
}
