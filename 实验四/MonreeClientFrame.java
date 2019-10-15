import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MonreeClientFrame extends JFrame{
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    final int WIDTH = 700;
    final int HEIGHT = 700;
    JButton sendButton = new JButton("发送");
    JButton clearButton = new JButton("清屏");
    JButton exitButton = new JButton("退出");
    JLabel lblReceiver = new JLabel("谁来接收：");
    JTextArea sayJta = new JTextArea();
    JTextArea chatJta = new JTextArea();
    String[] colTitles = {" ","IP","端口"};   //当前在线列表的列标题
    String[][] rowData = null;      //当前在线列表的数据
    JTable onlineJtb = new JTable
            (
                    new DefaultTableModel(rowData,colTitles){
                        //表格不可编辑，只可显示
                        public boolean isCellEditable(int row, int column){
                            return false;
                        }
                    }
            );
    JScrollPane chatJsp = new JScrollPane(chatJta);     //创建聊天消息框的滚动窗
    JScrollPane onlineJsp = new JScrollPane(onlineJtb);     //创建当前在线列表的滚动窗

    //设置默认窗口属性，连接窗口组件
    public MonreeClientFrame(){
        setTitle("欢迎使用软件171赖夏昕聊天室应用");
        setSize(WIDTH, HEIGHT);
        setResizable(false);    //不可缩放
        setLayout(null);    //不使用默认布局，布局自定义
        sendButton.setBounds(20,600,100,60);
        clearButton.setBounds(140,600,100,60);
        exitButton.setBounds(260,600,100,60);
        lblReceiver.setBounds(20,420,300,30);

        sendButton.setFont(new Font("微软雅黑",Font.BOLD,18));
        clearButton.setFont(new Font("微软雅黑",Font.BOLD,18));
        exitButton.setFont(new Font("微软雅黑",Font.BOLD,18));

        this.add(sendButton);
        this.add(clearButton);
        this.add(exitButton);
        this.add(lblReceiver);

        sayJta.setBounds(20,460,360,120);
        sayJta.setFont(new Font("微软雅黑",Font.BOLD,16));
        this.add(sayJta);
        chatJta.setLineWrap(true);      //自动换行
        chatJta.setEditable(false);     //聊天框只读
        chatJta.setFont(new Font("微软雅黑",Font.BOLD,16));

        chatJsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        chatJsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        chatJsp.setBounds(20,20,360,400);
        this.add(chatJsp);

        onlineJsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        onlineJsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        onlineJsp.setBounds(420,20,250,400);
        this.add(onlineJsp);

        //添加发送按钮的响应事件
        sendButton.addActionListener
                (
                        new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                chatJta.setCaretPosition(chatJta.getDocument().getLength());
                                try{
                                    //有收信人才发送
                                    if (MonreeClient.uidReceiver.toString().equals("") == false){
                                        //在聊天窗打印发送动作信息
                                        chatJta.append(sdf.format(new Date()) + "\n发往" + MonreeClient.uidReceiver.toString() + ":\n");
                                        //显示发送信息
                                        chatJta.append(sayJta.getText() + "\n\n");
                                        //向服务器发送聊天信息
                                        OutputStream out = MonreeClient.clientSocket.getOutputStream();
                                        out.write(("Chat/" + MonreeClient.uidReceiver.toString() + "/" + sayJta.getText()).getBytes());
                                    }
                                }
                                catch (Exception e){}
                                finally {
                                    sayJta.setText("");
                                }
                            }
                        }
                );
        //添加清屏按钮的响应事件
        clearButton.addActionListener
                (
                        new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                //聊天框清屏
                                chatJta.setText("");
                            }
                        }
                );
        //添加退出按钮的响应事件
        exitButton.addActionListener
                (
                        new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                try {
                                    //向服务器发送退出信息
                                    OutputStream out = MonreeClient.clientSocket.getOutputStream();
                                    out.write("Exit/".getBytes());
                                    //退出
                                    System.exit(0);
                                }
                                catch (Exception e){}
                            }
                        }
                );
        //添加在线列表项被鼠标选中的响应事件
        onlineJtb.addMouseListener
                (
                        new MouseListener() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                //取得在线列表的数据模型
                                DefaultTableModel tbm = (DefaultTableModel) onlineJtb.getModel();
                                //提取鼠标选中的行作为消息目标，最少一个人，最多全体在线者接收消息
                                int[] selectedIndex = onlineJtb.getSelectedRows();
                                //将所有消息目标的uid拼接成一个字符串，以逗号分隔
                                MonreeClient.uidReceiver = new StringBuilder("");
                                for (int i = 0; i < selectedIndex.length; i++){
                                    MonreeClient.uidReceiver.append((String) tbm.getValueAt(selectedIndex[i],1));
                                    MonreeClient.uidReceiver.append(":");
                                    MonreeClient.uidReceiver.append((String) tbm.getValueAt(selectedIndex[i],2));
                                    if (i != selectedIndex.length - 1)
                                        MonreeClient.uidReceiver.append(",");
                                }
                                lblReceiver.setText("发给：" + MonreeClient.uidReceiver.toString());
                            }

                            @Override
                            public void mousePressed(MouseEvent e) {

                            }

                            @Override
                            public void mouseReleased(MouseEvent e) {

                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {

                            }

                            @Override
                            public void mouseExited(MouseEvent e) {

                            }
                        }
                );
    }
}
