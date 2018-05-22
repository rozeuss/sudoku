/**
 * Klasa Stoper - imituje dzialanie stopera
 */

public class Stoper {

    private boolean isRunning = false; //czy stoper pracuje
    private long start = 0; //czas startu
    private long stop = 0; //czas stopu
    private long elapsed = 0; //czas uplyniety


    /**
     * start() - rozpoczecie odliczania czasu
     */
    public void start() {
        this.start = System.currentTimeMillis(); //zwraca czas w milisekundach
        this.isRunning = true;
    }


    /**
     * stop() - zakoczenie odliczania czasu
     */
    public void stop() {
        this.stop = System.currentTimeMillis();
        this.isRunning = false;
    }


    /**
     * elapsed() - obliczenie uplynietego czasu
     *
     * @return elapsed - zmierzona wartosc czasu
     */
    public long elapsed() {
        if (isRunning) {
            elapsed = (System.currentTimeMillis() - start);
        } else {
            elapsed = (stop - start);
        }
        return elapsed;
    }
}
