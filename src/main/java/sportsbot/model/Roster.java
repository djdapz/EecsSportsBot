package sportsbot.model;

import java.util.*;

/**
 * Created by devondapuzzo on 4/19/17.
 */
public class Roster {
    private HashMap<String, Player> players;
    private HashMap<String, Team> teams;

    private boolean isInitialized = false;

    public Roster(){
        this.players = new HashMap<String, Player>();
        this.teams = new HashMap<String, Team>();
    }

    public void addNewPlayers(ArrayList<LinkedHashMap> rosterResponse){
        for(int i = 0; i < rosterResponse.size(); i ++){
            LinkedHashMap<String, LinkedHashMap> playerTeamObject = (LinkedHashMap<String, LinkedHashMap>) rosterResponse.get(i);
            LinkedHashMap<String, String> playerResult = (LinkedHashMap<String, String>) playerTeamObject.get("player");
            LinkedHashMap<String, String> teamResult = (LinkedHashMap<String, String>) playerTeamObject.get("team");

            Player player = new Player();
            player.setID(Integer.parseInt(playerResult.get("ID")));
            player.setFirstName(playerResult.get("FirstName"));
            player.setLastName(playerResult.get("LastName"));
            player.setPosition(playerResult.get("Position"));
            player.setRookie(Boolean.getBoolean(playerResult.get("Rookie")));
            if(playerResult.get("JerseyNumber")!= null){
                player.setJerseyNumber(Integer.parseInt(playerResult.get("JerseyNumber")));
            }
            players.put(player.getLastName()+", "+player.getFirstName(), player);

            if(teams.containsKey(teamResult.get("Abbreviation"))){
                player.setTeam(teams.get(teamResult.get("Abbreviation")));
            }else{
                Team team = new Team();
                team.setName(teamResult.get("Name"));
                team.setAbbreviation(teamResult.get("Abbreviation"));
                team.setCity(teamResult.get("City"));
                team.setID(Integer.parseInt(teamResult.get("ID")));

                //right now just the abbreaviation is the key, may be a better way to do this, such as ID or combo of city /name
                teams.put(team.getAbbreviation(), team);
                player.setTeam(team);
            }

            teams.get(teamResult.get("Abbreviation")).addPlayer(player);
            players.put(player.getLastName()+", "+player.getFirstName(), player);
        }

        this.isInitialized = true;
    }


    public boolean isInitialized() {
        return isInitialized;
    }

    public HashMap<String, Team> getTeams() {
        return teams;
    }


}
