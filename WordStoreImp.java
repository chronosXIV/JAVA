/** - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 * Author: Elif Sebnem Cudi
 * Last Updated: 10/12/2017
 *
 * This class implements the WordStore interface and
 * acts as a bridge between the test code and the hash table.
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
package adsoofmini; // REMOVE

class WordStoreImp implements WordStore {

    HashTable h = new HashTable();  // Creates a hash table specific to the WordStoreImp object

    /**
     * Passes the string to be stored to the hash table.
     */
    public void add(String word) {
        h.put(word,1);
    }

    /**
     * Passes the string to be counted to the hash table
     * and returns the amount of occurrences found.
     */
    public int count(String word) {
        return h.countOccurences(word);
    }

    /**
     * Passes the string to be removed to the hash table.
     */
    public void remove(String word) {
        h.remove(word);
    }

    /**
     * Method placed by the author to be used during testing.
     * Prints out information about the hash table.
     * For this class to correctly implement it's interface,
     * this method has also been created in WordStore.
     */
    public void print() {
        h.print();
    }

}