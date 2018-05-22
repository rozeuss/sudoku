import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Klasa AnswerKeys - odpowiada za obiekt wprowadzania poprawnych warto�ci
 */

public class AnswerKeys extends JPanel {

    /* Deklaracja prywatnych zmiennych 
     *
	 */
    private static final long serialVersionUID = 1L;
    private JButton one; //przycisk 1
    private JButton two;//przycisk 2
    private JButton three;//przycisk 3
    private JButton four;//przycisk 4
    private JButton five;//przycisk 5
    private JButton six;//przycisk 6
    private JButton seven;//przycisk 7
    private JButton eight;//przycisk 8
    private JButton nine;//przycisk 9
    private UI board; //plansza
    private JPanel cell; //wybrana komorka
    private JButton answer; //wybrana odpowiedz


    /**
     * Konstruktor klasy AnswerKeys
     *
     * @param board  plansza sudoku
     * @param cell   wybrana komorka z planszy sudoku
     * @param answer wybrana odpowiedz
     */
    public AnswerKeys(UI board, JPanel cell, JButton answer) {
        this.board = board;
        this.cell = cell;
        this.answer = answer;
        init();

    }

    /**
     * Inicjalizacja ka�dego przycisku dziewi�cioelementowej klawiatury
     */
    private void init() {

        one = new JButton();
        two = new JButton();
        three = new JButton();
        four = new JButton();
        five = new JButton();
        six = new JButton();
        seven = new JButton();
        eight = new JButton();
        nine = new JButton();
        
        /* Ustawienie layotu */
        setLayout(new GridLayout(3, 3));

        /* Przycisk 1, ustawienie tekstu, marginesu
         *  oraz ActionListenera(wywolanie funkcji ustawiajacej odpowiedz)
         * 	analogicznie w pozostalych */
        one.setText("1");
        one.setMargin(new Insets(1, 1, 1, 1));
        one.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                board.setAnswer("1", cell, answer);
            }
        });
        /* Dodanie przycisku do jpanelu */
        add(one);

        /* Przycisk 2  */
        two.setText("2");
        two.setMargin(new Insets(1, 1, 1, 1));
        two.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                board.setAnswer("2", cell, answer);
            }
        });
        add(two);

        /* Przycisk 3  */
        three.setText("3");
        three.setMargin(new Insets(1, 1, 1, 1));
        three.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                board.setAnswer("3", cell, answer);
            }
        });
        add(three);

        /* Przycisk 4  */
        four.setText("4");
        four.setMargin(new Insets(1, 1, 1, 1));
        four.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                board.setAnswer("4", cell, answer);
            }
        });
        add(four);

        /* Przycisk 5  */
        five.setText("5");
        five.setMargin(new Insets(1, 1, 1, 1));
        five.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                board.setAnswer("5", cell, answer);
            }
        });
        add(five);

        /* Przycisk 6  */
        six.setText("6");
        six.setMargin(new Insets(1, 1, 1, 1));
        six.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                board.setAnswer("6", cell, answer);
            }
        });
        add(six);

        /* Przycisk 7  */
        seven.setText("7");
        seven.setMargin(new Insets(1, 1, 1, 1));
        seven.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                board.setAnswer("7", cell, answer);
            }
        });
        add(seven);

        /* Przycisk 8  */
        eight.setText("8");
        eight.setMargin(new Insets(1, 1, 1, 1));
        eight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                board.setAnswer("8", cell, answer);
            }
        });
        add(eight);

        /* Przycisk 9  */
        nine.setText("9");
        nine.setMargin(new Insets(1, 1, 1, 1));
        nine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                board.setAnswer("9", cell, answer);
            }
        });
        add(nine);
    }


}
