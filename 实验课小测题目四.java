import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Test1 {
    public static void main(String[] args) {
        JFrame f= new JFrame("Test");
        JButton b1 = new JButton("-");
        JButton b2 = new JButton("+");
        JLabel l = new JLabel("0",JLabel.CENTER);
        Monitor bh = new Monitor();
        f.setLayout(new GridLayout());
        bh.setJLabel(l);
        f.add(b1);
        f.add(l);
        f.add(b2);
        b1.setActionCommand("-");
        b2.setActionCommand("+");
        b1.addActionListener(bh);
        b2.addActionListener(bh);
        f.pack();
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
class Monitor implements ActionListener{
    int count = 0;
    JLabel l;
    public void setJLabel(JLabel l){
        this.l = l;
    }
    public void actionPerformed(ActionEvent e){
        String str = e.getActionCommand();
        if (str.equals("-")){
            if (count>0) count--;
        }
        else {
            count++;
        }
        l.setText(""+count);
    }
}
