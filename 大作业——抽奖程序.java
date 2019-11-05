import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

class LuckyDraw extends JFrame{
    private static boolean enableDraw = false;
    private JFrame frame;
    private JLabel tipText;
    private JLabel luckyName;
    private JButton loadNamelist;
    private JButton startBtn;
    private JButton pauseBtn;
    private JButton restartBtn;
    private JLabel rstText;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    Timer timer;
    public LuckyDraw() {
        buildUI();
        timer = new Timer();
        eventHandler();
    }


    void buildUI() {
        frame = new JFrame("抽奖小助手");
        tipText = new JLabel("准备开始吧！");
        luckyName = new JLabel("幸运儿会是谁呢？");
        loadNamelist = new JButton("读取名单");
        startBtn = new JButton("开始");
        pauseBtn = new JButton("停");
        restartBtn = new JButton("重置");
        rstText = new JLabel("结果：");
        panel1 = new JPanel();
        panel2= new JPanel();
        panel3 = new JPanel();
        panel4 = new JPanel();

        luckyName.setFont(new Font("方正舒体",Font.PLAIN,36));
        tipText.setFont(new Font("微软雅黑",Font.BOLD,20));
        rstText.setFont(new Font("微软雅黑",Font.PLAIN,26));

        frame.setLayout(new GridLayout(4,1));
        panel1.add(tipText);
        panel2.add(luckyName);
        panel3.add(rstText);
        panel4.add(startBtn);
        panel4.add(pauseBtn);
        panel4.add(restartBtn);
        panel4.add(loadNamelist);

        frame.add(panel1);
        frame.add(panel2);
        frame.add(panel3);
        frame.add(panel4);
        frame.setSize(500,400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void eventHandler(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (enableDraw) {
                    int num = (int) (1 + Math.random() * 101);
                    luckyName.setText(String.valueOf(num));
                }
            }
        },0, 100);

        pauseBtn.setEnabled(false);

        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!enableDraw){
                    enableDraw = true;
                    tipText.setText("开始！");
                    startBtn.setText("取消");
                    pauseBtn.setEnabled(true);
                }
                else {
                    enableDraw = false;
                    luckyName.setText("幸运儿会是谁呢？");
                    tipText.setText("准备开始吧！");
                    startBtn.setText("开始");
                    pauseBtn.setEnabled(false);
                }
            }
        });

        pauseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableDraw = false;
                rstText.setText(rstText.getText()+' '+luckyName.getText());
                tipText.setText("请看结果！");
                startBtn.setText("开始");
                pauseBtn.setEnabled(false);
            }
        });

        restartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableDraw = false;
                rstText.setText("结果：");
                luckyName.setText("幸运儿会是谁呢？");
                tipText.setText("准备开始吧！");
                startBtn.setText("开始");
            }
        });
    }

    public static void main(String[] args) {
        new LuckyDraw();
    }
}
