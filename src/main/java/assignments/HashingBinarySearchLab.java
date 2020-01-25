package assignments;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class HashingBinarySearchLab {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Enter file name: ");
        String fileName = scanner.nextLine().trim();
        System.out.println(fileName);
        try {
            BufferedReader inFile = new BufferedReader(new FileReader(fileName));
            String inputLine = inFile.readLine();
            String[] words = inputLine.toLowerCase().split("[^a-z]+");
        } catch (Exception e) {
            System.out.println("Error occurred reading file " + fileName);
        }
        System.out.println("Total number of words in the file is :");
    }
}


class WordCount {
    private String word;
    private int count;

    public WordCount(String word, int count) {
        this.word = word;
        this.count = count;
    }

    public String getWord() {
        return word;
    }

    public int getCount() {
        return count;
    }

    public String toString() {
        return "word = " + this.getWord() + ", count = " + this.getCount();
    }

    public void increaseCount() {
        ++this.count;
    }
}

class WordTable {

    //=====================================================
    // Class Node: An ordinary linked-list node
    //=====================================================
    private class Node {
        public WordCount item;
        public WordTable.Node next;

        public Node(WordCount newItem, WordTable.Node newNext) {
            item = newItem;
            next = newNext;
        } // end Node constructor

    } // end class Node
    //=====================================================

    private static final int A = 13; // For the hash function

    private WordTable.Node[] hashArray; // The array of linked lists.
    int numberItems; // A position in the hash array

    /******************************************************************
     *
     * TableWithSC constructor
     *
     * Assumption: tableSize is a prime number
     *
     *****************************************************************/
    public WordTable(int tableSize) {
        hashArray = new WordTable.Node[tableSize];
        for (int i = 0; i < hashArray.length; i++) {
            hashArray[i] = null;
        } // end for
        numberItems = 0;
    } // end TableWithSC constructor

    /******************************************************************
     *
     * hash
     *
     * Hashes key and returns the resulting array index.
     *
     * The hash function uses the polynomial hash code
     * implemented using Horner's method.
     *
     *****************************************************************/
    private int hash(String key) {
        int hashIndex = 0;

        for (int i = 0; i < key.length(); i++) {
            hashIndex = (hashIndex * A) % hashArray.length + (int) key.charAt(i);
            hashIndex = hashIndex % hashArray.length;
        } // end for

        return hashIndex;
    } // end hash

    /******************************************************************
     *
     * insert
     *
     * Inserts key into the table (except if key is already in the table,
     * in which case, this method prints an error message).
     *
     * Also, this method increments numberItems if the key is inserted.
     *
     *****************************************************************/
    public void insert(String key) {

        //========= Replace this comment with your code ==========

        // Get the location in hash table first
        int hashIndex = hash(key);
        // Iterate through particular linked list to ensure key doesn't exist
        for (WordTable.Node x = hashArray[hashIndex]; x != null; x = x.next) {
            if (key.equals(x.item.getWord())) {
                // Print error message if key exists
                System.out.println("***ERROR: Attempting to insert "
                        + " a duplicate of \"" + key
                        + "\" into a TableWithLP");
                return;
            }
        }
        // Add the key if doesn't exists
        hashArray[hashIndex] = new WordTable.Node(new WordCount(key, 1), hashArray[hashIndex]);
        numberItems++;

    } // end insert

    /******************************************************************
     *
     * search
     *
     * Searches the table for key, and returns true if key is found,
     * false if it isn't.
     *
     *****************************************************************/
    public WordCount search(String key) {

        //========= Replace this comment with your code ==========
        int hashIndex = hash(key);
        for (WordTable.Node x = hashArray[hashIndex]; x != null; x = x.next) {
            if (key.equals(x.item.getWord())) {
                return x.item;
            }
        }
        return null;
    } // end search


      /******************************************************************
     *
     * toString
     *
     * For debugging purposes only.
     * This method returns a String giving information about the
     * non-empty entries of the array.
     *
     *****************************************************************/
    public String toString() {
        String output = "The non-empty array entries:";
        int hashIndex;
        WordTable.Node curr;

        for (int i = 0; i < hashArray.length; i++) {
            if (hashArray[i] != null) {
                hashIndex = hash(hashArray[i].item.getWord());
                output += "\n   Keys at index = " + i + ": ";
                curr = hashArray[i];
                while (curr != null) {
                    output += "\n      " + curr.item;
                    curr = curr.next;
                } // end while
            } // end if
        } // end for

        return output;
    } // end toString

}


class BST {
    private class BSTNode {
        private WordCount wordCount;
        private BSTNode left, right;

        public BSTNode(WordCount wordCount) {
            this.wordCount = wordCount;
        }
    }
}