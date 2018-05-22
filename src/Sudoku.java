import java.util.Random;

/**
 * Klasa Sudoku - zawiera wszystkie matematyczne funkcje potrzebne do wygenerowania sudoku
 */
public class Sudoku {

    public static final int[][] example_board = {
            {6, 3, 9, 5, 7, 4, 1, 8, 2},
            {5, 4, 1, 8, 2, 9, 3, 7, 6},
            {7, 8, 2, 6, 1, 3, 9, 5, 4},
            {1, 9, 8, 4, 6, 7, 5, 2, 3},
            {3, 6, 5, 9, 8, 2, 4, 1, 7},
            {4, 2, 7, 1, 3, 5, 8, 6, 9},
            {9, 5, 6, 7, 4, 8, 2, 3, 1},
            {8, 1, 3, 2, 9, 6, 7, 4, 5},
            {2, 7, 4, 3, 5, 1, 6, 9, 8}};
    /* poprawne wartosci */
    final String properValues = "123456789";
    /* gotowe sudoku */
    private int[][] sudoku;
    /* obiekt do operacji losowych */
    private Random random = new Random();

    /**
     * swapRows() - zamiana miejscami dw�ch wierszy tablicy
     *
     * @param board przekazana tablica
     * @param row1  wiersz pierwszy
     * @param row2  wiersz drugi
     * @return board tablica po zamienionych wierszach
     */
    private int[][] swapRows(int[][] board, int row1, int row2) {
        for (int j = 0; j < 9; j++) {
            int tmp = board[row1][j]; //zapamietujemy wartosc
            board[row1][j] = board[row2][j]; //podmieniamy
            board[row2][j] = tmp;
        }
        return board;
    }

    /**
     * swapColumns() - zamiana miejscami dw�ch kolumn tablicy
     *
     * @param board przekazana tablica
     * @param col1  kolumna pierwsza
     * @param col2  kolumna druga
     * @return board tablica po zamienionych kolumnach
     */
    private int[][] swapColumns(int[][] board, int column1, int column2) {
        for (int i = 0; i < 9; i++) {
            int tmp = board[i][column1]; //zapamietujemy wartosc
            board[i][column1] = board[i][column2]; //podmieniamy
            board[i][column2] = tmp;
        }
        return board;
    }

    /**
     * swapping - zamienia wiersze i kolumny planszy
     *
     * @param board przekazana tablica
     * @return board - tablica po zamianie wierszy i kolumn
     */
    private int[][] swapping(int[][] board) {
        int row[] = new int[2];
        int column[] = new int[2];

        for (int i = 0; i < 7; i += 3) { //zamiana wierszy mo�e odbywa� si� tylko w 3 grupach
            //0 <= i < 3     lub         3 <= i < 6     lub       6 <= i < 9
            row[0] = i + random.nextInt(3);
            row[1] = i + random.nextInt(3);
            swapRows(board, row[0], row[1]);
        }

        for (int j = 0; j < 7; j += 3) {//zamiana kolumn mo�e odbywa� si� tylko w 3 grupach
            //0 <= j < 3     lub         3 <= j < 6     lub       6 <= j < 9
            column[0] = j + random.nextInt(3);
            column[1] = j + random.nextInt(3);
            swapColumns(board, column[0], column[1]);

        }
        return board;
    }


    /**
     * swapNumbRows() - losuje dwie liczby z przedzia�u 1-9 a nast�pnie zamienia odpowiednie
     * miejscami w wierszach
     *
     * @param board przekazana tablica
     * @return board - tablica z zamienionymi liczbami
     */
    private int[][] swapNumbRows(int[][] board) {
        int number[] = new int[2];
        number[0] = 1 + random.nextInt(9); //losuje 1 liczbe
        number[1] = 1 + random.nextInt(9); //losuje 2 liczbe
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == number[0]) { //jezeli rowne 1 liczbie
                    board[i][j] = number[1]; //podmien wartosc
                } else if (board[i][j] == number[1]) { //jezeli rowne 2 liczbie
                    board[i][j] = number[0]; //podmien wartosc
                }
            }
        }
        return board;
    }

    /**
     * swapNumbColumns() - losuje dwie liczby z przedzia�u 1-9 a nast�pnie zamienia odpowiednie
     * miejscami w kolumnach
     *
     * @param board przekazana tablica
     * @return board - tablica z zamienionymi liczbami
     */
    private int[][] swapNumbColumns(int[][] board) {
        int number[] = new int[2];
        number[0] = 1 + random.nextInt(9); //losuje 1 liczbe
        number[1] = 1 + random.nextInt(9); //losuje 2 liczbe
        for (int j = 0; j < 9; j++) {
            for (int i = 0; i < 9; i++) {
                if (board[i][j] == number[0]) { //jezeli rowne 1 liczbie
                    board[i][j] = number[1]; //podmien wartosc
                } else if (board[i][j] == number[1]) { //jezeli rowne 2 liczbie
                    board[i][j] = number[0]; //podmien wartosc
                }
            }
        }
        return board;
    }

    /**
     * newSudoku() - tworzy nowe sudoku wykonujac operacje zamienia miejsc na przykladowym sudoku,
     * nastepnie okresla liczbe pustych blokow
     *
     * @param gamelevel poziom gry
     * @return zwraca wywolanie funckji makeSudoku z nowa plansza
     */
    public int[][] newSudoku(GameLevel gamelevel) {
        int[][] board = example_board;
        int[][] copy = new int[9][];
        int emptyBlocks = 0;
        for (int i = 0; i < 10; i++) { //wywolanie funkcji mieszajacych
            swapping(board);
            swapNumbRows(board);
            swapNumbColumns(board);
        }
        for (int i = 0; i < 9; i++) copy[i] = board[i].clone(); //stworzenie kopi wypelnionego sudoku
        this.sudoku = copy; //zapisanie nowego sudoku
        //okreslenie liczby pustych blokow w zaleznosci od wybranego poziomu gry 
        if (gamelevel == GameLevel.TEST) {
            emptyBlocks = 0;
        } //test poziom
        if (gamelevel == GameLevel.EASY) {
            emptyBlocks = 20;
        } //dla �atwego
        if (gamelevel == GameLevel.MEDIUM) {
            emptyBlocks = 40;
        } //�redniego
        if (gamelevel == GameLevel.HARD) {
            emptyBlocks = 60;
        } //trudnego
        emptyBlocks += random.nextInt(3);
        for (int i = 0; i < emptyBlocks; i++) {
            int tempty[] = new int[2];
            tempty[0] = random.nextInt(9); //wygnerowanie wspolrzednych pustej komorki
            tempty[1] = random.nextInt(9);
            this.sudoku[tempty[0]][tempty[1]] = 0; //nadanie warto�ci kom�rki
        }
        return this.sudoku;

    }

    /**
     * resetSudoku() - resetuje plansze sudoku
     *
     * @return zwraca niewype�nion� plansz� sudoku
     */
    public int[][] resetSudoku() {
        return sudoku;
    }

    /**
     * isCorrect() - sprawdza czy podane sudoku jest poprawnie wypelnione
     *
     * @param board - sudoku wypelnione przez uzytkownika
     * @return isCorrect - zwraca true or false
     */
    public boolean isCorrect(int[][] board) {
        boolean isCorrect = true; //poprawne rozwiazanie

        // sprawdzenie wierszy
        for (int i = 0; i < 9; i++) {
            String answerValues = properValues;
            for (int j = 0; j < 9; j++) {
                answerValues = answerValues.replace("" + board[i][j], ""); //usuwanie podanego podstringa ze stringa
            }
            if (answerValues.length() > 0) { //jezeli nie zostaly usuniete wszystkie substringi
                isCorrect = false; //bledne rozwiazanie
                return isCorrect;
            }
        }
        // sprawdzenie kolumn
        for (int j = 0; j < 9; j++) {
            String answerValues = properValues;
            for (int i = 0; i < 9; i++) {
                answerValues = answerValues.replace("" + board[i][j], "");//usuwanie podanego podstringa ze stringa
            }
            if (answerValues.length() > 0) {//jezeli nie zostaly usuniete wszystkie substringi
                isCorrect = false; //bledne rozwiazanie
                return isCorrect;
            }
        }
        // sprawdzenie blok�w 3x3
        for (int Hblock = 0; Hblock < 9; Hblock += 3) {
            for (int V_block = 0; V_block < 9; V_block += 3) {
                String answerValues = properValues;
                for (int i = Hblock; i < Hblock + 3; i++) {
                    for (int j = V_block; j < V_block + 3; j++) {
                        answerValues = answerValues.replace("" + board[i][j], "");//usuwanie podanego podstringa ze stringa
                    }
                }
                if (answerValues.length() > 0) {// jezeli nie zostaly usuniete wszystkie substringi
                    isCorrect = false; // bledne rozwiazanie
                    return isCorrect;
                }
            }
        }

        return isCorrect;
    }

}
