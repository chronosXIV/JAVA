/** - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 * Author: Elif Sebnem Cudi
 * Last Updated: 10/12/2017
 * 
 * This is the class for the linked lists that make up the hash table.
 * 
 * It stores a string, and information on how many times
 * the string was added to the hash table.
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

public class LinkedHashEntry {

    private String value;           // Variable for the word
    private int count;              // Variable for the amount added
    private LinkedHashEntry next;

    LinkedHashEntry(String value, int count) {
        this.value = value;
        this.count = count;
        this.next = null;
    }

    public String getValue() {
        return value;
    }
    
    public int getCount() {
        return count;
    }
        
    public void increaseCount() {
        count++;
    }
    
    public void decreaseCount(){
        count--;
    }
    
    public LinkedHashEntry getNext() {
        return next;
    }

    public void setNext(LinkedHashEntry next) {
        this.next = next;
    }
}
