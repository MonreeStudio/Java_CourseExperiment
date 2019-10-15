import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.nio.charset.*;
import java.text.*;

public class MonreeClient{
    public static Socket clientSocket = null;   //建立客户端
    public static StringBuilder uidReceiver = null;
    public static void main(String[] args) throws Exception{
        MonreeClientFrame mcFrame = new MonreeClientFrame();    //创建客户端窗口对象
        //窗口关闭无效，必须通过退出键退出客户端以便善后
        mcFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //获取本机屏幕横向分辨率
        int w = Toolkit.getDefaultToolkit().getScreenSize().width;
        //获取本机屏幕纵向分辨率
        int h = Toolkit.getDefaultToolkit().getScreenSize().height;
        //将窗口之中
        mcFrame.setLocation((w - mcFrame.WIDTH)/2,(h - mcFrame.HEIGHT)/2);
        mcFrame.setVisible(true);
        try {
            //连接服务器
            clientSocket = new Socket(InetAddress.getLocalHost(),6666);
            //获取输入输出流
            InputStream in = clientSocket.getInputStream();
            OutputStream out = clientSocket.getOutputStream();
            //获取服务器欢迎信息
            byte[] bytes = new byte[1024];
            int len = in.read(bytes);
            //将服务端发来的连接成功信息打印在聊天消息框
            mcFrame.chatJta.append(new String(bytes,0,len));
            mcFrame.chatJta.append("\n");
            //持续等待服务器信息直至退出
            while (true){
                //读取服务器发来的信息
                in = clientSocket.getInputStream();
                len = in.read(bytes);
                //处理服务器传来的信息
                String msg = new String(bytes,0,len);
                //获取消息类型：更新在线名单或者聊天
                String type = msg.substring(0,msg.indexOf("/"));
                //消息本体：更新后的名单或者聊天内容
                String chat = msg.substring(msg.indexOf("/")+1);
                //根据消息类型分别处理
                //更新在线名单
                if (type.equals("OnlineListUpdate")){
                    //提取在线列表的数据
                    DefaultTableModel dtm = (DefaultTableModel) mcFrame.onlineJtb.getModel();
                    //清楚在线名单
                    dtm.setRowCount(0);
                    //更新在线列表
                    String[] onlineList = chat.split(",");
                    for (String member:onlineList){
                        //保存在线成员的IP、端口号
                        String[] tmp = new String[3];
                        //自己不能在列表里
                        String me = member.substring(member.indexOf("~")+1);
                        if (me.equals(InetAddress.getLocalHost().getHostAddress() + ":" + clientSocket.getLocalPort())){
                            continue;
                        }
                        //获取成员信息
                        tmp[0] = "";
                        tmp[1] = member.substring(0,member.indexOf(":"));
                        tmp[2] = member.substring(member.indexOf(":")+1);
                        //在在线列表中添加在线者信息
                        dtm.addRow(tmp);
                    }
                    //提取在线列表渲染模型
                    DefaultTableCellRenderer tbr = new DefaultTableCellRenderer();
                    //表格数据居中显示
                    tbr.setHorizontalAlignment(JLabel.CENTER);
                    //设置单元格的渲染器为复选框
                    mcFrame.onlineJtb.setDefaultRenderer(Object.class,tbr);
                }
                //聊天
                else if (type.equals("Chat")){
                    //获取发送信息者和信息内容
                    String sender = chat.substring(0,chat.indexOf("/"));
                    String word = chat.substring(chat.indexOf("/")+1);
                    //在聊天窗打印聊天信息
                    mcFrame.chatJta.append(mcFrame.sdf.format(new Date())+"\n来自" + sender + ":\n" + word + "\n\n");
                    //显示最新消息
                    //这个方法只是一个观察文本的字段的显示功能，参数指明观察的位置
                    //但注意一定不能超出文本的总长度，因为不可能在文本外观观察到文本的字段，所以会抛出异常
                    mcFrame.chatJta.setCaretPosition(mcFrame.chatJta.getDocument().getLength());
                }
            }
        }
        catch (Exception e)
        {
            mcFrame.chatJta.append("呃，服务器宕机了。\n");
            e.printStackTrace();
        }
    }
}
