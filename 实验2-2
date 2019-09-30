import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Test1 {
    public static void main(String[] args) {
       JFrame frame = new JFrame("Menu");
       JMenuBar menuBar = new JMenuBar();
       frame.setJMenuBar(menuBar);
       JMenu m1 = new JMenu("File");
       JMenu m2 = new JMenu("Format");
       JMenu m3 = new JMenu("Help");
       menuBar.add(m1);
       menuBar.add(m2);
       menuBar.add(m3);
       JMenuItem menuItem1 = new JMenuItem("中文");
       JMenu menuItem2 = new JMenu("进制");
       menuItem2.add(new JMenuItem("二进制"));
       menuItem2.add(new JMenuItem("八进制"));
       menuItem2.add(new JMenuItem("十进制"));
       m2.add(menuItem1);
       m2.add(menuItem2);
       frame.pack();
       frame.setVisible(true);
       frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}

