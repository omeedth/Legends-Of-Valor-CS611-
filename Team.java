/**
 * @author: Zhuyun Chen
 * date: 11/08/20
 * Class represent a Team of characters in general
 * Superclass of Player team
 **/

import java.util.*;

public abstract class Team {
    
    protected ArrayList<Character> members;
    
   /* Default constructor */
    Team(){
        this.members = new ArrayList<Character>();
    }
    
    /* Display all members in team */
    public abstract void display();
    
    /** Add a new member to team */
    public void addMember(Character newChar){
        this.members.add(newChar);
    }
    
    /** Get member from the team by index */
    public Character getMember(int index){ return this.members.get(index);}
    public ArrayList<Character> getMembers(){ return this.members;}
    
    /** Remove member from the team by member */
    public void removeMember(Character member){
        this.members.remove(member);
    }
    /** Remove member from the team by index */
    public void removeMember(int index){
        this.members.remove(index);
    }
    
    /** return size of the team */
    public int size(){
        return this.members.size();
    }
    
    /** Spawn each team member on the nexus*/
    public void spawn(ArrayList<? extends NexusTile> nexus){
        if(nexus.size() != this.members.size()){
            System.out.println("No enough space to spawn!");
        }else{
            for(int i=0;i<nexus.size();i++){
                BattleCharacter c = (BattleCharacter)this.members.get(i);
                c.spawn(nexus.get(i).getCoords());
            }
        }
    }
    
    /** Extend the team with another team */
    public void joinTeam(Team aTeam){
        this.members.addAll(aTeam.getMembers());
    }
}
