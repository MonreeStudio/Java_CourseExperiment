import javax.swing.*;
import java.awt.*;

public class Test1 {
    public static void main(String[] args) {
        JFrame frame = new JFrame("登录");
        JTextField jTextField = new JTextField(16);
        JPasswordField jPasswordField = new JPasswordField(15);
        JLabel label1 = new JLabel("用户名：");
        JLabel label2 = new JLabel("密码：");
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JButton button = new JButton("确定");

        frame.setLayout(new GridLayout(3,1));
        panel1.add(label1);
        panel1.add(jTextField);
        panel2.add(label2);
        panel2.add(jPasswordField);
        panel3.add(button);
        frame.add(panel1);
        frame.add(panel2);
        frame.add(panel3);
        frame.setSize(300,200);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
