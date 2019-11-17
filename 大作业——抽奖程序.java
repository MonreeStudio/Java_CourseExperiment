import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.Timer;

class LuckyDraw extends JFrame{
    private static boolean enableDraw = false;
    private JFrame frame;
    private JLabel tipText;
    private JLabel luckyName;
    private JLabel countName;
    private JLabel toggleState;
    private JButton loadNamelist;
    private JButton startBtn;
    private JButton pauseBtn;
    private JButton restartBtn;
    private JLabel rstText;
    private JToggleButton repeatToggle;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;
    private List<String> list;
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
        countName = new JLabel("总人数：");
        loadNamelist = new JButton("读取名单");
        startBtn = new JButton("开始");
        pauseBtn = new JButton("停");
        restartBtn = new JButton("重置");
        rstText = new JLabel("结果：");
        repeatToggle = new JToggleButton("结果可重复",true);
        toggleState = new JLabel("是");
        panel1 = new JPanel();
        panel2= new JPanel();
        panel3 = new JPanel();
        panel4 = new JPanel();
        panel5 = new JPanel();

        luckyName.setFont(new Font("方正舒体",Font.PLAIN,36));
        tipText.setFont(new Font("微软雅黑",Font.BOLD,20));
        rstText.setFont(new Font("微软雅黑",Font.PLAIN,26));

        frame.setLayout(new GridLayout(5,1));
        panel1.add(tipText);
        panel2.add(luckyName);
        panel2.add(countName);
        panel3.add(rstText);
        panel4.add(startBtn);
        panel4.add(pauseBtn);
        panel4.add(restartBtn);
        panel4.add(loadNamelist);
        panel5.add(repeatToggle);
        panel5.add(toggleState);
        frame.add(panel1);
        frame.add(panel2);
        frame.add(panel3);
        frame.add(panel4);
        frame.add(panel5);
        frame.setSize(500,400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void eventHandler(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (enableDraw) {
                    long count;
                    Random r=new Random();
                    count = list.size();
                    int num = r.nextInt((int) count);
                    luckyName.setText(String.valueOf(list.get(num)));
                }
            }
        },0, 100);

        pauseBtn.setEnabled(false);

        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!enableDraw){
                    if (list==null)
                        JOptionPane.showMessageDialog(null, "未检测到名单！请先读取名单。");
                    else {
                        repeatToggle.setEnabled(false);
                        enableDraw = true;
                        tipText.setText("开始！");
                        startBtn.setText("取消");
                        pauseBtn.setEnabled(true);
                    }
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

        loadNamelist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pathName = "D:\\Bill的文件\\Java\\软件171名单.txt";
                list = new ArrayList<String>();
                try (FileReader reader = new FileReader(pathName))
                {

                    BufferedReader br = new  BufferedReader(reader);
                    String s = br.readLine();
                    while(s!=null){
                        list.add(s);
                        s=br.readLine();
                    }
                    br.close();
                    countName.setText("总人数："+list.size());
                    JOptionPane.showMessageDialog(null, "名单列表读取成功！请继续操作。");
                }
                catch (IOException e1){
                    e1.printStackTrace();
                }
            }
        });

        pauseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableDraw = false;
                rstText.setText(rstText.getText()+' '+luckyName.getText());
                if (list.size()>0) {
                    tipText.setText("请看结果！");
                    if (!repeatToggle.isSelected())
                        list.remove(luckyName.getText());
                }
                else {
                    tipText.setText("抽完了");
                }
                countName.setText("总人数："+String.valueOf(list.size()));
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
                list.clear();
                countName.setText("总人数："+String.valueOf(list.size()));
                repeatToggle.setEnabled(true);
            }
        });

        repeatToggle.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JToggleButton repeatToggle = (JToggleButton) e.getSource();
                if (repeatToggle.isSelected()){
                    toggleState.setText("是");
                }
                else {
                    toggleState.setText("否");
                }
            }
        });
    }

    public static void main(String[] args) {
        new LuckyDraw();
    }
}
