/** - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 * Author: Elif Sebnem Cudi
 * Last Updated: 10/12/2017
 *
 * Main class for the hash table.
 * It is a chaining hash table: the table is an array of singly-linked
 * lists containing values with the same hash. The information stored is 
 * a string, and the amount of times it was added to the hash table.
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
package adsoofmini; // REMOVE

public class HashTable {

    private final int INITIAL_TABLE_SIZE = 128;
    private float threshold = 0.75f;    // The load factor
    private int maxSize = 96;   // Initial maximum amount of entries before the table is set to expand
    private int size = 0;   // Count for the number of entries

    LinkedHashEntry[] table;    // Initialising the hash table

    /**
     * Class constructor for the initial hash table with the size 128.
     * Sets every bucket to null.
     */
    HashTable() {
        table = new LinkedHashEntry[INITIAL_TABLE_SIZE];
        for (int i = 0; i < INITIAL_TABLE_SIZE; i++) {
            table[i] = null;
        }
    }

    /**
     * This method passes a string into a hash function. The resulting hash is
     * used to determine the index in the hash table which the initial string
     * will be placed in.
     *
     * If the value inside the index is null, it will make the string the first
     * item of a linked list within the bucket. If there already exists a linked
     * list within the bucket, it will traverse the linked list. If the string 
     * is found to already exist within the linked list, it will increment 
     * it's count by 1, else, it will add the string to the end of the list with
     * count 1.
     * 
     * With every addition of a new word, size is also incremented. If
     * the size surpasses the threshold, a new hash table
     * is created twice the size of the previous one and the contents
     * of the previous hash table are copied over by the resize() method.
     */
    public void put(String value, int count) {

        int hash = hashCode(value);   // Retrieves the hash index 

        if (table[hash] == null) {
            table[hash] = new LinkedHashEntry(value, count);
            size++;
        } else {
            LinkedHashEntry entry = table[hash];
            while (entry.getNext() != null && !entry.getValue().equals(value)) {
                entry = entry.getNext();
            }
            if (entry.getValue().equals(value)) {
                entry.increaseCount();
            } else {
                entry.setNext(new LinkedHashEntry(value, count));
                size++;
            }
        }
        if (size >= maxSize) {
            resize();
        }
    }

    /**
     * This method expands the hash table by creating a new
     * hash table double its previous size, and copying the contents of the
     * table over to the new one. However, each word is placed at a new index
     * as a result of the hash function returning an integer dependent 
     * on the new table size.
     */
    private void resize() {

        size = 0;   // Variable set back to 0 to recount the amount of unique entries
        int tableSize = 2 * table.length;
        maxSize = (int) (tableSize * threshold);    // Increases the maximum size to fit the 70% threshold
        LinkedHashEntry[] oldTable = table;
        table = new LinkedHashEntry[tableSize];

        for (int i = 0; i < tableSize; i++) {
            table[i] = null;
        }
        for (int i = 0; i < oldTable.length; i++) {
            if (oldTable[i] != null) {
                LinkedHashEntry entry = oldTable[i];
                while (entry != null) {
                    put(entry.getValue(), entry.getCount());
                    entry = entry.getNext();
                }
            }
        }
    }

    /**
     * This method removes entries from the hash table.
     * 
     * Taking a single string as it's argument, the method finds the string's
     * hash index, and, if the index is not null in the first place, searches 
     * for the string within the linked list. It will remove the string 
     * by setting the entry to be the next entry.
     * 
     * It will also decrement the size variable to suit the amount of unique
     * entries within the hash table.
     */
    public void remove(String value) {

        int hash = hashCode(value);

        if (table[hash] != null) {
            LinkedHashEntry prevEntry = null;
            LinkedHashEntry entry = table[hash];
            while (entry.getNext() != null && !entry.getValue().equals(value)) {
                prevEntry = entry;
                entry = entry.getNext();
            }
            
            if (!entry.getValue().equals(value)) {
                    if (prevEntry == null) {
                        table[hash] = entry.getNext();
                    } else {
                        prevEntry.setNext(entry.getNext());
                    }
                    size--;
                }
            
        }
    }

    /**
     * This method is the hash function. It finds what position in the hash
     * table the method put() should put the string based on the number 
     * generated by the computation.
     *
     * In this case, the computation multiplies the initial prime number 7
     * with another prime 31, and adds the ASCII value of a character of the
     * string ???? how do i explain dis???
     * 
     * and divides it by the size of the table,
     * returning the remainder of the computation as the position to place the
     * string. This computation will always produce a number between 0 and the
     * table size.
     */
    public int hashCode(String value) {

        int hash = 7;
        
        for (int i = 0; i < value.length(); i++) {
            hash = hash * 31 + value.charAt(i);
        }
        return absoluteValue(hash % table.length);
    }

    /**
     * This method returns the absolute value of it's integer argument.
     */
    int absoluteValue(int n) {
        if (n < 0) {
            return n * (-1);
        } else {
            return n;
        }
    }

    /**
     * This method returns the number of occurrences of a particular 
     * string within the hash table.
     *
     * It will first generate the hash index of the valueToMatch string and 
     * try to find it within the linked list of that particular bucket. 
     * If the value is not found, it will return 0, if the value is found, 
     * it will return the information saved within the count variable on how 
     * many times the word was generated.
     */
    int countOccurences(String valueToMatch) {

        int hash = hashCode(valueToMatch);

        if (table[hash] == null) {
            return 0;
        } else {
            LinkedHashEntry entry = table[hash];

            while (entry != null) {
                if (entry.getValue().equals(valueToMatch)) {
                    return entry.getCount();
                }
                entry = entry.getNext();
            }

            return 0;
        }
    }

    /**
     * This method was written by the author to experiment how the hash table
     * stores values.
     *
     * It goes through every entry within every bucket and can print out: 
     * every value, every information on the amount of times the string was 
     * added, the hash index, the total strings stored including duplicates, 
     * and the table size at the point of method's execution.
     */
    void print() {
        int sumOfEntries = 0;
        for (int i = 0; i < table.length; i++) {
            LinkedHashEntry entry = table[i];
            //System.out.println("Hash index: " + i + ":");

            while (entry != null) {
                //System.out.println("- Value: " + entry.getValue() + " | Amount generated: " + entry.getCount());
                sumOfEntries = sumOfEntries + entry.getCount();
                entry = entry.getNext();
            }
            //System.out.println();
        }
        System.out.println("Total words saved: " + sumOfEntries);
        System.out.println("Table size: " + table.length);
    }
}
