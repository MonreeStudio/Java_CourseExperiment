import java.awt.*;
import java.awt.event.*;
import java.security.PublicKey;
import javax.swing.*;

public class ThreadFrame extends JFrame implements ActionListener{
    JTextField showWord;
    JButton button;
    JTextField inputText,showScore;
    WordThread giveWord;
    int score = 0;
    ThreadFrame(){
        showWord = new JTextField(6);
        showWord.setFont(new Font("",Font.BOLD,72));
        showWord.setHorizontalAlignment(JTextField.CENTER);
        giveWord = new WordThread();
        giveWord.SetJTextField(showWord);
        giveWord.setSleepLength(5000);
        button = new JButton("开始");
        inputText = new JTextField(10);
        showScore = new JTextField(5);
        showScore.setEditable(false);
        button.addActionListener(this);
        inputText.addActionListener(this);
        add(button,BorderLayout.NORTH);
        add(showWord,BorderLayout.CENTER);
        JPanel southP = new JPanel();
        southP.add(new JLabel("输入汉字（回车）："));
        southP.add(inputText);
        southP.add(showScore);
        add(southP,BorderLayout.SOUTH);
        setBounds(100,100,350,180);
        setVisible(true);
        validate();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == button){
            if (!(giveWord.isAlive())){
                giveWord = new WordThread();
                giveWord.SetJTextField(showWord);
                giveWord.setSleepLength(5000);
            }
            try{
                giveWord.start();
            }
            catch (Exception exe){}
        }
        else if (e.getSource() == inputText){
            if (inputText.getText().equals(showWord.getText()))
                score++;
            showScore.setText("得分："+score);
            inputText.setText(null);
        }
    }
}
