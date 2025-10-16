import java.util.ArrayList;

public interface BoggleGameInterface {

    /**
     * Randomly generate and return a Boggle board of a given dimension
     * @param size the dimension size of the board
     * @return a randomly generated Boggle board as a 2-d array of characters or 
     *         null if size <= 0 or size is too big
     */
    public char[][] generateBoggleBoard(int size);

    /**
     * Returns the total number of words of length three or more found in the 
     * board following the rules of the Boggle Game
     * @param boggleBoard the 2-d character array representing the Boggle board
     * @param dictionary the DictInterface dictionary
     * @return total number of words found in the board
     */
    public int countWords(char[][] boggleBoard, DictInterface dictionary);

    /**
     * Returns the total number of words of a given length found in the board
     * following the rules of the Boggle Game
     * @param boggleBoard the 2-d character array representing the Boggle board
     * @param dictionary the DictInterface dictionary
     * @param wordLength the word length
     * @return total number of words of length `wordLength` found in the board
     */
    public int countWordsOfCertainLength(char[][] boggleBoard, DictInterface dictionary, int wordLength);

    /**
     * Checks if a given word is in a given dictionary
     * @param dictionary the DictInterface dictionary
     * @param word the String word to check
     * @return true if word exists in dictionary and false otherwise
     */
    public boolean isWordInDictionary(DictInterface dictionary, String word);

    /**
     * Checks if a given word can be found in a given Boggle board following the
     * rules of the Boggle Game
     * @param boggleBoard the 2-d character array representing the Boggle board
     * @param word the String word to check
     * @return rue if word can be found in boggleBoard and false otherwise
     */
    public boolean isWordInBoard(char[][] boggleBoard, String word);

    /**
     * Finds a word of length three or more from a given dictionary in a given 
     * Boggle board
     * @param boggleBoard the 2-d character array representing the Boggle board
     * @param dictionary the DictInterface dictionary
     * @return a String word (from dictionary) of length three or more found in 
     * boggleBoard or null if no such word can be found
     */
    public String anyWord(char[][] boggleBoard, DictInterface dictionary);

     /**
     * Finds a word of a given length from a given dictionary in a given 
     * Boggle board
     * @param boggleBoard the 2-d character array representing the Boggle board
     * @param dictionary the DictInterface dictionary
     * @param length the int word length
     * @return a String word (from dictionary) of length characters found in 
     * boggleBoard or null if no such word can be found
     */
    public String anyWord(char[][] boggleBoard, DictInterface dictionary, int length);


    /**
     * Finds a given word in a given board and returns a list of board tiles 
     * where the word is found
     * @param boggleBoard the 2-d character array representing the Boggle board
     * @param word the String word to find
     * @return an ArrayList of board tiles where the word is found or null if the
     * word cannot be found in the board
     */
    public ArrayList<Tile> markWordInBoard(char[][] boggleBoard, String word);

    /**
     * Checks a list of board tiles to see if they are adjacent (according to
     * the rules of the Boggle Game) and that they have the letters of a given 
     * word in order
     * @param boggleBoard the 2-d character array representing the Boggle board
     * @param tiles an ArrayList of board tiles
     * @param word
     * @return true if tiles contains adjacent tiles that have the letters of 
     * word in order or false otherwise
     */
    public boolean checkTiles(char[][] boggleBoard, ArrayList<Tile> tiles, String word);

}
