/**
 * @author: Zhuyun Chen date: 11/08/20 Class represent an Item in general
 **/

public abstract class Item{
    
    protected String name;
    
    Item(){
        this.name = "No name";
	}
    
    /**
     * User defined constructor
     */
    Item(String name){
        this.name = name;
    }
    
    /** Display the item info */
    public abstract void display();
    
    /** GET METHODS */
    public String getName(){ return this.name;}

}
