package sportsbot.model;

import java.util.HashMap;

/**
 * Created by devondapuzzo on 4/19/17.
 */
public class Team {

    private int ID;
    private String City;
    private String Name;
    private String Abbreviation;
    private HashMap<String, Player> players;

    public Team() {
        players = new HashMap<String, Player>();
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getAbbreviation() {
        return Abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        Abbreviation = abbreviation;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public HashMap getPlayers(){
        return players;
    }

    public void addPlayer(Player player){
        String key = player.getLastName()+", "+player.getFirstName();
        if(!players.containsKey(key)){
            players.put(key, player);
        };
    }
}
