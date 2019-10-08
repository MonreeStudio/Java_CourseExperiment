import javax.swing.JTextField;

public class WordThread extends Thread {
    char word;
    int startPosition = 19968;
    int endPosition = 32320;
    JTextField showWord;
    int sleepLength = 6000;
    public void SetJTextField(JTextField t){
        showWord = t;
        showWord.setEditable(false);
    }
    public void setSleepLength(int n){
        sleepLength = n;
    }
    public void run(){
        int k = startPosition;
        while (true){
            word=(char)k;
            showWord.setText(""+word);
            try {
                sleep(sleepLength);
            }
            catch (InterruptedException e){}
            k++;
            if (k>=endPosition)
                k = startPosition;
        }
    }
}
