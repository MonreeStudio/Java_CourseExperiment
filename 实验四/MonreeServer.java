import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

class ServerThread implements Runnable{
    Socket clientSocket = null;     //客户端Socket
    ServerSocket serverSocket = null;   //服务器Socket
    String IP = null;   //客户端IP
    int port = 0;   //客户端端口
    String uid = null;  //组合客户端IP和端口字符串得到uid字符串
    static  ArrayList<String> uid_arr = new ArrayList<String>();
    static  Map<String,ServerThread> cm = new ConcurrentHashMap<>();

    public ServerThread(Socket clientSocket, ServerSocket serverSocket, String IP, int port){
        this.clientSocket = clientSocket;
        this.serverSocket = serverSocket;
        this.IP = IP;
        this.port = port;
        this.uid = IP + ":" + port;
    }

    public void run(){
        uid_arr.add(uid);   //将当前客户端uid存入到数组中
        cm.put(uid,this);   //将当前服务线程存入map中
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            InputStream in = clientSocket.getInputStream();
            OutputStream out = clientSocket.getOutputStream();
            //向客户端输入连接成功的信息
            String done = sdf.format(new Date()) + "\n成功连接服务器...\n服务器IP：" +
                    serverSocket.getInetAddress().getLocalHost().getHostAddress() + "，端口：6666\n客户端IP：" +
                    IP + ",端口：" + port +"\n";
            out.write((done.getBytes()));
            updateOnlineList(out);

            byte[] buf = new byte[1024];    //准备缓冲区
            int len = 0;

            while (true){
                len = in.read(buf); //获取客户端给服务器发送的信息
                String msg = new String(buf,0,len);
                //消息类型：退出或者聊天
                String type = msg.substring(0,msg.indexOf('/'));
                //聊天内容：空或者聊天内容
                String chat = msg.substring(msg.indexOf('/')+1);

                //根据消息类型分别处理
                //如果退出
                if(type.equals("Exit")){
                    //更新ArrayList和Map
                    uid_arr.remove(uid_arr.indexOf(this.uid));
                    cm.remove(this.uid);
                    //广播更新在线名单
                    updateOnlineList(out);
                    break;
                }
                else if (type.equals("Chat")){
                    //提取收信者信息
                    String[] receiveArr = chat.substring(0,chat.indexOf('/')).split(",");
                    //提取聊天内容
                    String word = chat.substring(chat.indexOf('/')+1);
                    System.out.println(word);
                    //向收信者广播发出聊天信息
                    chatOnlineList(out,uid,receiveArr,word);
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void chatOnlineList(OutputStream out, String uid,String[] receiveArr,String word) throws Exception{
        for(String tmp:receiveArr){
            out = cm.get(tmp).clientSocket.getOutputStream();   //发送聊天信息
            out.write(("Chat/" + uid + "/" + word).getBytes());
        }
    }

    private void updateOnlineList(OutputStream out) throws Exception{
        for (String tmp:uid_arr){
            out = cm.get(tmp).clientSocket.getOutputStream();   //获取广播收听者的输出流
            StringBuilder sb = new StringBuilder("OnlineListUpdate/");  //将当前在线名单以逗号为分割组合成长字符串一次发送
            for (String member:uid_arr){
                sb.append(member);
                //以逗号分隔uid，除了最后一个
                if (uid_arr.indexOf(member) != uid_arr.size()-1)
                    sb.append(",");
            }
            out.write(sb.toString().getBytes());    //向每个客户端输入更新在线的名单
        }
    }
}

public class MonreeServer{
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(6666);     //建立服务器
        while (true){
            Socket clientSocket = serverSocket.accept();    //接收客户端Socket
            String IP = clientSocket.getInetAddress().getHostAddress();     //提取客户端IP
            int port = clientSocket.getPort();      //提取客户端端口号
            //建立新的服务器线程，向该线程提供服务器ServerSocket，客户端Socket，客户端IP和端口
            new Thread(new ServerThread(clientSocket,serverSocket,IP,port)).start();
        }
    }
}
