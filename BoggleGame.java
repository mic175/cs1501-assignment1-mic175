import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class BoggleGame implements BoggleGameInterface {

    private static final int[] rowOffsets = { -1, -1, -1, 0, 0, 1, 1, 1 };
    private static final int[] colOffsets = { -1, 0, 1, -1, 1, -1, 0, 1 };

    @Override
    public char[][] generateBoggleBoard(int size) {
        if (size <= 0) {
            return null;
        }
        int stringLength = size * size;
        if (stringLength <= 0) {
            return null;
        }
        String s = generateRandomString(stringLength);
        char[][] board = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = s.charAt(i * size + j);
            }
        }
        return board;
    }

    @Override
    public int countWords(char[][] boggleBoard, DictInterface dictionary) {
        HashSet<String> foundWords = new HashSet<>();
        boolean[][] visited = new boolean[boggleBoard.length][boggleBoard[0].length];
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < boggleBoard.length; i++) {
            for (int j = 0; j < boggleBoard[0].length; j++) {

                searchWords(i, j, boggleBoard, visited, dictionary, word, foundWords);
            }
        }
        return foundWords.size();
    }

    @Override
    public int countWordsOfCertainLength(char[][] boggleBoard, DictInterface dictionary, int wordLength) {
        HashSet<String> foundWords = new HashSet<>();
        boolean[][] visited = new boolean[boggleBoard.length][boggleBoard[0].length];
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < boggleBoard.length; i++) {
            for (int j = 0; j < boggleBoard[i].length; j++) {

                searchWordsOfCertainLength(i, j, boggleBoard, visited, dictionary, word, foundWords, wordLength);
            }
        }
        return foundWords.size();
    }

    private void searchWords(int row, int col, char[][] boggleBoard, boolean[][] visit, DictInterface dictionary,
            StringBuilder currentSolution, HashSet<String> foundWords) {

        visit[row][col] = true; // mark the letter as used
        currentSolution.append(Character.toLowerCase(boggleBoard[row][col]));
        // boggleBoard[row][col] = Character.toUpperCase(boggleBoard[row][col]);
        int len = currentSolution.length();
        int res = dictionary.searchPrefix(currentSolution); // 0 none, 1 prefix, 2 word, 3 both
        if (res == 0) { // no prefix or word
            currentSolution.deleteCharAt(len - 1);
            visit[row][col] = false;
            return; // backtrack if no prefix or word
        }

        if (len >= 3 && (res == 2 || res == 3)) { // word
            foundWords.add(currentSolution.toString());
        }

        if (res == 1 || res == 3) { // prefix / prefix and word
            for (int dir = 0; dir < 8; dir++) {
                int newRow = row + rowOffsets[dir];
                int newCol = col + colOffsets[dir];
                if (newRow >= 0 && newRow < boggleBoard.length && newCol >= 0 && newCol < boggleBoard[0].length
                        && !visit[newRow][newCol]) {
                    searchWords(newRow, newCol, boggleBoard, visit, dictionary, currentSolution, foundWords);
                }
            }
        }

        currentSolution.deleteCharAt(len - 1);
        // boggleBoard[row][col] = Character.toLowerCase(boggleBoard[row][col]);
        visit[row][col] = false;

    }

    private void searchWordsOfCertainLength(int row, int col, char[][] boggleBoard, boolean[][] visit,
            DictInterface dictionary, StringBuilder currentSolution, HashSet<String> foundWords, int wordLength) {

        visit[row][col] = true; // mark the letter as used
        currentSolution.append(Character.toLowerCase(boggleBoard[row][col]));
        // boggleBoard[row][col] = Character.toUpperCase(boggleBoard[row][col]); //
        // meaningless to change case here

        int len = currentSolution.length();
        if (len > wordLength) {
            currentSolution.deleteCharAt(currentSolution.length() - 1);
            visit[row][col] = false;
            return; // backtrack if current solution exceeds desired length
        }

        int res = dictionary.searchPrefix(currentSolution); // 0 none, 1 prefix, 2 word, 3 both

        if (len == wordLength) {
            if (res == 2 || res == 3) { // word / prefix and word
                foundWords.add(currentSolution.toString());
            }
            // backtrack and stop: exactly target length; no need to explore neighbors
            currentSolution.deleteCharAt(currentSolution.length() - 1);
            visit[row][col] = false;
            return;
        }

        if (res == 1 || res == 3) { // prefix / prefix and word
            for (int dir = 0; dir < 8; dir++) {
                int newRow = row + rowOffsets[dir];
                int newCol = col + colOffsets[dir];
                if (newRow >= 0 && newRow < boggleBoard.length && newCol >= 0 && newCol < boggleBoard[0].length
                        && !visit[newRow][newCol]) {
                    searchWordsOfCertainLength(newRow, newCol, boggleBoard, visit, dictionary, currentSolution,
                            foundWords, wordLength);
                }
            }
        }

        currentSolution.deleteCharAt(len - 1);
        // boggleBoard[row][col] = Character.toLowerCase(boggleBoard[row][col]); //
        // meaningless to change case here
        visit[row][col] = false;

    }

    @Override
    public boolean isWordInDictionary(DictInterface dictionary, String word) {
        // TODO Implement this method

        if (dictionary == null || word == null)
            return false;

        String w = word.trim().toLowerCase();
        if (w.isEmpty())
            return false;

        int res = dictionary.searchPrefix(new StringBuilder(w));
        return res == 2 || res == 3;
    }

    @Override
    public boolean isWordInBoard(char[][] boggleBoard, String word) {
        if (boggleBoard == null || boggleBoard.length == 0 || boggleBoard[0].length == 0 || word == null)
            return false;
        String w = word.trim();
        if (w.isEmpty())
            return false;

        int rows = boggleBoard.length, cols = boggleBoard[0].length;
        if (w.length() > rows * cols)
            return false; // impossible: not enough cells

        // Normalize once to avoid repeated toUpperCase calls
        char[] target = w.toUpperCase().toCharArray();

        boolean[][] visited = new boolean[rows][cols];
        char first = target[0];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (Character.toUpperCase(boggleBoard[i][j]) == first && dfs(boggleBoard, i, j, target, 0, visited)) {
                    return true; // // only start on matching first char
                }

            }
        }
        return false; // Word not found
    }

    private boolean dfs(char[][] board, int row, int col, char[] target, int index, boolean[][] visited) {
        if (index == target.length - 1) {
            return true; // Entire word found
        }

        int rows = board.length, cols = board[0].length;
        if (row < 0 || row >= rows || col < 0 || col >= cols || visited[row][col]) {
            return false; // Out of bounds or character mismatch
        }

        char current = Character.toUpperCase(board[row][col]);
        char wordIndex = target[index];
        if (current != wordIndex) {
            return false;
        }

        visited[row][col] = true;
        // Explore all adjacent directions
        for (int dir = 0; dir < 8; dir++) {
            int newRow = row + rowOffsets[dir];
            int newCol = col + colOffsets[dir];
            int newIndex = index + 1;
            if (dfs(board, newRow, newCol, target, newIndex, visited)) {
                visited[row][col] = false;
                return true; // Continue search in the direction
            }
        }

        visited[row][col] = false;
        return false;
    }

    @Override
    public String anyWord(char[][] boggleBoard, DictInterface dictionary) {

        for (int i = 0; i < boggleBoard.length; i++) {
            for (int j = 0; j < boggleBoard[0].length; j++) {
                HashSet<String> foundWords = new HashSet<>();
                boolean[][] visited = new boolean[boggleBoard.length][boggleBoard[0].length];
                StringBuilder word = new StringBuilder();
                searchWords(i, j, boggleBoard, visited, dictionary, word, foundWords);
                if (!foundWords.isEmpty())
                    return foundWords.iterator().next();
            }

        }

        return null;
    }

    @Override
    public ArrayList<Tile> markWordInBoard(char[][] boggleBoard, String word) {
        boolean[][] visited = new boolean[boggleBoard.length][boggleBoard[0].length];
        for (int i = 0; i < boggleBoard.length; i++) {
            for (int j = 0; j < boggleBoard[i].length; j++) {
                if (boggleBoard[i][j] == word.charAt(0)) { // Start from the first letter of the word
                    ArrayList<Tile> path = new ArrayList<>();

                    if (dfsMark(boggleBoard, i, j, word, 0, visited, path)) {
                        return path; // Word found, return path
                    }
                }
            }
        }
        return null; // Word not found
    }

    private boolean dfsMark(char[][] board, int row, int col, String word, int index, boolean[][] visited,
            ArrayList<Tile> path) {
        if (index == word.length()) {
            return true; // Entire word found
        }

        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length || visited[row][col]
                || board[row][col] != word.charAt(index)) {
            return false; // Out of bounds or character mismatch
        }

        visited[row][col] = true; // Mark as visited
        path.add(new Tile(row, col)); // Add current tile to path

        // Explore all adjacent directions
        for (int dir = 0; dir < 8; dir++) {
            int newRow = row + rowOffsets[dir];
            int newCol = col + colOffsets[dir];
            if (dfsMark(board, newRow, newCol, word, index + 1, visited, path)) {
                return true; // Continue search in the direction
            }
        }

        visited[row][col] = false; // Backtrack
        path.remove(path.size() - 1); // Remove the last tile from the path
        return false;
    }

    @Override
    public boolean checkTiles(char[][] boggleBoard, ArrayList<Tile> tiles, String word) {
        if (tiles == null || boggleBoard == null || word == null || tiles.isEmpty() || word.isEmpty()) {
            return false;
        }

        if (tiles.size() != word.length()) {
            return false; // The number of tiles must match the word's length
        }

        for (int i = 0; i < tiles.size(); i++) {
            Tile currentTile = tiles.get(i);
            // Check if tile matches the corresponding letter in the word
            if (boggleBoard[currentTile.row][currentTile.col] != word.charAt(i)) {
                return false;
            }
            // Check adjacency for tiles except for the first one
            if (i > 0) {
                Tile previousTile = tiles.get(i - 1);
                if (!areAdjacent(previousTile, currentTile)) {
                    return false;
                }
            }
        }
        // All tiles match their letters and are adjacent
        return true;
    }

    private boolean areAdjacent(Tile tile1, Tile tile2) {
        int rowDiff = Math.abs(tile1.row - tile2.row);
        int colDiff = Math.abs(tile1.col - tile2.col);
        // Tiles are adjacent if both row and column differences are less than or equal
        // to 1, but not both 0
        return rowDiff <= 1 && colDiff <= 1 && !(rowDiff == 0 && colDiff == 0);
    }

    @Override
    public String anyWord(char[][] boggleBoard, DictInterface dictionary, int length) {
        for (int i = 0; i < boggleBoard.length; i++) {
            for (int j = 0; j < boggleBoard[0].length; j++) {
                HashSet<String> foundWords = new HashSet<>();
                boolean[][] visited = new boolean[boggleBoard.length][boggleBoard[0].length];
                StringBuilder word = new StringBuilder();
                searchWordsOfCertainLength(i, j, boggleBoard, visited, dictionary, word, foundWords, length);
                if (!foundWords.isEmpty())
                    return foundWords.iterator().next();
            }

        }

        return null;
    }

    private String generateRandomString(int length) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = length;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString().toUpperCase();

        // System.out.println(generatedString);
        return generatedString;
    }

}
