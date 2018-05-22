import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

/**
 * Klasa UI przedstawiaj�ca User Interface gry Sudoku
 */
public class UI extends JFrame {

    /* Deklaracja prywantych zmiennych */
    private static final long serialVersionUID = 1L;
    private JPanel window; //glowne okno
    private JPanel board; //plansza sudoku
    private JPanel basis; //podstawa planszy
    private JMenuBar menuBar; //pasek menu
    private JPanel actionPanel; //panel z przyciskami akcji
    private JButton newGame; //przycisk nowej gry
    private JButton reset; //przycisk resetowania gry
    private JButton submit; //przycisk konczenia gry
    private JLabel timer; //etykieta stopera
    private JMenu level; //element menu poziom
    private JMenuItem easy; //pozycja �atwy w menu
    private JMenuItem medium; //pozycja �redni w menu
    private JMenuItem hard; //pozycja trudny w menu
    private JPopupMenu.Separator separator1; //separator menu
    private JPopupMenu.Separator separator2;
    private JLabel messageLabel; //komunikat koncowy


    private JButton[][] buttons; //przyciski planszy
    private ActionListener[][] actionListeners; //actionListenery planszy
    private JPanel[][] cells; //komorki planszy
    private Sudoku sudoku; //obiekt sudoku
    private GameLevel gameLevel; //poziom gry
    private Stoper stoper; //obiekt stoper

    /**
     * Konstruktor klasy UI
     */
    public UI() {
        sudoku = new Sudoku(); //utworzenie nowej planszy sudoku
        stoper = new Stoper(); //uruchomienie stopera
        init(); //inicjalizacja elementow UI
        gameLevel = GameLevel.MEDIUM; //ustawienie poziomu gry
        int[][] sud = sudoku.newSudoku(gameLevel);
        createSudoku(sud); //utworzenie graficznej planszy sudoku
        startTimer(); //wlaczenie stopera
    }


    /**
     * Inicjalizacja element�w GUI
     */
    private void init() {

        window = new JPanel();
        actionPanel = new JPanel();
        newGame = new JButton();
        reset = new JButton();
        timer = new JLabel();
        submit = new JButton();
        basis = new JPanel();
        board = new JPanel();
        menuBar = new JMenuBar();
        level = new JMenu();
        easy = new JMenuItem();
        medium = new JMenuItem();
        hard = new JMenuItem();
        separator1 = new JPopupMenu.Separator();
        separator2 = new JPopupMenu.Separator();


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Sudoku");
        setMinimumSize(new Dimension(800, 800)); //minimalny rozmiar
        setLocationRelativeTo(null); //wysrodkowanie okienka
        add(window); //dodanie glownego okna
        
        /* Przycisk nowej gry - nadanie tekstu i ActionListenera*/
        newGame.setText("Nowa gra");
        newGame.setFocusable(false);
        newGame.addActionListener(new ActionListener() {
            /** Wykonanie akcji dla JButton o nazwie newGame 
             * @param evt action event */
            public void actionPerformed(ActionEvent evt) {
                int[][] sud = sudoku.newSudoku(gameLevel);//wygenerowanie nowego sudoku
                createSudoku(sud);//utworzenie nowej planszy
            }
        });


        /* Przycisk resetowania gry - nadanie tekstu i ActionListenera*/
        reset.setText("Reset");
        reset.setFocusable(false);
        reset.addActionListener(new ActionListener() {
            /** Wykonanie akcji dla JButton o nazwie reset
             * @param evt action event  */
            public void actionPerformed(ActionEvent evt) {
                int[][] sud = sudoku.resetSudoku(); //zresetowanie sudoku
                createSudoku(sud);//utworzenie nowej planszy
            }
        });
        
        /* Przycisk zatwierdzenia odpowiedzi - nadanie tekstu i ActionListenera*/
        submit.setText("Zatwierd�");
        submit.setFocusable(false);
        submit.addActionListener(new ActionListener() {
            /** Wykonanie akcji dla JButton o nazwie submit, dop�ki nie jest 
             * wypelniona cala plansza to wyswietl stosowny komunikat
             * @param evt action event
             *  */
            public void actionPerformed(ActionEvent evt) {
                /* Sprawdzenie czy wszystkie pola s� wype�nione*/
                boolean isComplete = true; //wszystkie pola wypelnione
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        try {
                            Integer.parseInt(buttons[i][j].getText()); //przetworzenie lancucha znakow na liczbe calkowita
                        } catch (NumberFormatException e) { //w przypadku wyjatku ( brak wartosci )
                            isComplete = false;
                            break;
                        }
                    }
                }
                if (isComplete) {    //jesli wypelnione   
                    stoper.stop(); //zatrzymanie stopera
                    String msg = "";
                    int answer[][] = new int[9][9]; //nowa tablica przechowujaca wypelnione sudoku
                        /* Pobranie odpowiedzi wprowadzonych przez uzytkownika */
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            answer[i][j] = Integer.parseInt(buttons[i][j].getText()); //przetworzenie �ancucha znak�w na liczb� ca�kowit�
                        }
                    }
                    boolean isAnsCorrect = sudoku.isCorrect(answer); //sprawdzenie poprawnosci wypelnienia
                    if (isAnsCorrect) { //poprawna odpowiedz
                        msg = "Zwyci�stwo! \n Tw�j czas: " + timer.getText();
                    } else { //niepoprawna odpowiedz
                        msg = "Przegrana.";
                    }
	                /* Wyswietlenie komunikatu koncowego*/
                    messageLabel.setText(msg); //ustawienie tresci
                    basis.removeAll();
                    basis.add(messageLabel); //dodanie etykiety z wiadomoscia
                    basis.repaint();
                    setVisible(true);
                } else //w przeciwnym wypadku
                {
                    JOptionPane.showMessageDialog(submit, "Prosz� wype�ni� pola.");
                }
            }
        });

        /* Etykieta stopera - nadanie tekstu i wysrodkowanie */
        timer.setFont(new Font("Verdana", 0, 20));
        timer.setText("00:00");
        timer.setHorizontalAlignment(SwingConstants.CENTER);
  
        /* Panel opcji - dodanie powyzszych elementow */
        actionPanel.setLayout(new GridLayout());
        actionPanel.setBackground(new Color(200, 220, 255));
        actionPanel.add(newGame);
        actionPanel.add(reset);
        actionPanel.add(submit);
        actionPanel.add(timer);

		/* Podstawa planszy */
        basis.setBackground(new Color(255, 255, 255));
        basis.setLayout(new GridLayout(1, 1));

        
        /* Plansza sudoku - dodanie jej do podstawy */
        board.setBackground(new Color(0, 0, 0));
        board.setLayout(new GridLayout(9, 9));
        basis.add(board);

        
        /* Dodanie do g�ownego okna podstawy i panelu akcji */
        window.setLayout(new BorderLayout());
        window.add(basis, BorderLayout.CENTER);
        window.add(actionPanel, BorderLayout.PAGE_START);

  
        /* Pasek menu*/
        level.setText("Poziom");
        
        /* Poziom �atwy - nadanie nazwy i ActionListener */
        easy.setText("�atwy");
        easy.addActionListener(new ActionListener() {
            /** Wykonanie akcji dla JMenuItem o nazwie easy
             * @param evt action event*/
            public void actionPerformed(ActionEvent evt) {
                gameLevel = GameLevel.EASY; //zmiana poziomu na �atwy
                int[][] sud = sudoku.newSudoku(gameLevel); //wygenerowanie nowego sudoku
                createSudoku(sud);//utworzenie nowej planszy
            }
        });
        level.add(easy);//dodanie do menu poziomu


        level.add(separator1);
        
        /* Poziom �redni - nadanie nazwy i ActionListener */
        medium.setText("�redni");
        medium.addActionListener(new ActionListener() {
            /** Wykonanie akcji dla JMenuItem o nazwie medium
             * @param evt action event */
            public void actionPerformed(ActionEvent evt) {
                gameLevel = GameLevel.MEDIUM;//zmiana poziomu na �redni
                int[][] sud = sudoku.newSudoku(gameLevel); //wygenerowanie nowego sudoku
                createSudoku(sud); //utworzenie nowej planszy
            }
        });
        level.add(medium);//dodanie do menu poziomu


        level.add(separator2);
        
        /* Poziom trudny - nadanie nazwy i ActionListener */
        hard.setText("Trudny");
        hard.addActionListener(new ActionListener() {
            /** Wykonanie akcji dla JMenuItem o nazwie hard
             * @param evt action event */
            public void actionPerformed(ActionEvent evt) {
                gameLevel = GameLevel.HARD;//zmiana poziomu na trudny
                int[][] sud = sudoku.newSudoku(gameLevel);//wygenerowanie nowego sudoku
                createSudoku(sud);//utworzenie nowej planszy
            }
        });
        level.add(hard);//dodanie do menu poziomu

        menuBar.add(level); //dodanie do paska menu elementu level
        /*Ustawienie paska menu dla okna*/
        setJMenuBar(menuBar);
        /*ekran koncowy*/
        messageLabel = new JLabel();
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER); //format tekstu
        messageLabel.setFont(new Font("Verdana", 1, 40));
    }

    /**
     * Wyswietlenie nowej graficznej planszy
     *
     * @param sud przekazana tablica
     */
    private void createSudoku(int[][] sud) {
        board.removeAll(); //usuniecie elementow z planszy
        cells = new JPanel[9][9];
        buttons = new JButton[9][9];
        actionListeners = new ActionListener[9][9];
        board.setLayout(new GridLayout(9, 9, 1, 1)); //ustawienie layotu planszy

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j] = new JPanel(); //dla kazdego pola po JPanel i JButon
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Verdana", 0, 21)); //ustalenie czcionki 

                //ustalenie koloru kom�rek
                if ((((0 <= i && i < 3) || (6 <= i && i < 9)) && (3 <= j && j < 6)) ||
                        ((3 <= i && i < 6) && ((0 <= j && j < 3) || (6 <= j && j < 9)))) {
                    buttons[i][j].setBackground(new Color(200, 220, 255)); // dla blok�w 2,4,6, 8
                } else {
                    buttons[i][j].setBackground(new Color(255, 255, 255)); // dla pozosta�ych
                }

                String value = "";
                if (0 < sud[i][j] && sud[i][j] <= 9) { //jezeli pole ma wartosc
                    value += sud[i][j]; //zapamietanie wartosci
                } else { //w przeciwnym wypadku mozliwosc wprowadzenia wartosci
                    JButton tempbutton = buttons[i][j];
                    JPanel tempBlock = cells[i][j];

                    actionListeners[i][j] = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            showAnswerKeys(tempBlock, tempbutton); //nacisniecie na pole umozliwia wprowadzenie odpowiedzi
                        }
                    };
                    buttons[i][j].addActionListener(actionListeners[i][j]);//nadanie ActionListenera
                    buttons[i][j].setBackground(new Color(255, 204, 204)); //kolor dla pustej kom�rki
                }

                buttons[i][j].setText(value); //ustawienie wartosci komorki
                buttons[i][j].setFocusable(false);
                cells[i][j].setLayout(new GridLayout(1, 1));
                cells[i][j].add(buttons[i][j]);
                board.add(cells[i][j]);
            }
        }

        basis.removeAll(); //usuniecie elementow z podstawy
        basis.add(board); //dodanie nowej planszy
        board.repaint();
        basis.repaint();
        this.setVisible(true);
        stoper.start(); //start stopera
    }


    /**
     * Funkcja wyswietlajaca blok wprowadzania danych
     *
     * @param cell   wybrana komorka
     * @param answer wybrany obiekt JButton skojarzony z komorka
     */
    private void showAnswerKeys(JPanel cell, JButton answer) {
        JPanel answerKey = new AnswerKeys(this, cell, answer);
        cell.remove(answer); //usuwa JButton answer
        cell.add(answerKey); //zastepuje obiektem klasy AnswerKeys
        this.setVisible(true);
    }

    /**
     * Funkcja wypelniajaca blok odpowiedzi� podan� przez u�ytkownika
     *
     * @param ans    wybrana odpowiedz
     * @param answer wybrany blok odpowiedzi
     * @param cell   wybrana komorka planszy
     */
    public void setAnswer(String ans, JPanel cell, JButton answer) {
        cell.removeAll(); //usuwa wczesniejsza zawartosc kom�rki
        answer.setFont(new Font("Verdana", 1, 24)); //wytluszczony i powiekszony tekst
        answer.setText(ans); //ustawia odpowiedz
        cell.add(answer); //dodanie JButtona z ActionListenerem
        this.repaint();
    }

    /**
     * Rozpoczecie dzialania stopera
     */
    private void startTimer() {
        SimpleDateFormat dt = new SimpleDateFormat("mm:ss"); //ustawienie formatu stopera
        Thread t = new Thread(new Runnable() { //utworzenie nowego watku i nowego obiektu Runnable
            public void run() {
                stoper.start(); //rozpoczecie dzialania stopera
                while (true) {
                    String timeString = dt.format(stoper.elapsed()); //przetworzenie czasu na String
                    timer.setText(timeString); //ustawienie tekstu etykiety timer
                }
            }
        }
        );
        t.start(); //rozpoczecie dzialania watku
    }

}
