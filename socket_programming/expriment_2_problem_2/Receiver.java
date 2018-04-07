package expriment_2_problem_2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

public class Receiver implements Runnable {
    private DatagramSocket ds;
    private DatagramPacket dp;
    private JTextArea jTextArea;
    private JButton clearButton; // 清空显示屏按钮
    private int port; // 监听端口（接收）
    private String infoStr;
    public Receiver(){
        infoStr = "";
    }
    public void setjTextArea(JTextArea jTextArea) {
        this.jTextArea = jTextArea;
        jTextArea.setEditable(false);
    }
    public void setClearButton(JButton button) {
        this.clearButton = button ;
    }
    public void setPORT(int p) { this.port = p; }
    public int getPORT(){ return this.port; }
    @Override
    public void run() {
        // 监听清空按钮
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jTextArea.setText(""); // 清空显示屏
            }
        });
        try{
            ds = new DatagramSocket(port);
            int len;
            while(true){
                byte[] info = new byte[1024];
                dp = new DatagramPacket(info,info.length);
                ds.receive(dp);
                String loc = dp.getAddress().getHostAddress()+":"+dp.getPort();
                String info_ = new String(dp.getData(),0,dp.getLength());
                len = info_.length();
                if(info_.equals("\n") || len==0) // 如果为空，不追加文本款内容
                    continue;
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 定制时间
                String now = df.format(new Date());
                loc = "At: "+now+"\nFrom: "+loc;
                infoStr = infoStr+"\n"+loc + "\n" + info_;
                jTextArea.setText(infoStr);
            }
        } catch (Exception e){
            System.out.println("Can't receive info from server");
        }
    }
}

