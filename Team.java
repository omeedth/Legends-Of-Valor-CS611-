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
    public Character getMember(int index){
        return this.members.get(index);
    }
    
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
}
