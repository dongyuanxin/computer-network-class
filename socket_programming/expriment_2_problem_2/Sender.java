package expriment_2_problem_2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Sender implements Runnable {
    private DatagramSocket ds;
    private DatagramPacket dp;
    private JTextArea jTextArea;
    private JButton confirmButton; // 确定按钮
    private int port; // 监听端口（目标）
    private String ipName; // 锁定远程IP
    public void setjTextArea(JTextArea jTextArea){ this.jTextArea = jTextArea;}
    public void setjButton(JButton jButton) { this.confirmButton= jButton;}
    public void setIpName(String IP){ ipName = IP; }
    public String getIpName(){ return ipName; }
    public void setPORT(int p) { this.port = p; }
    public int getPORT(){ return this.port; }
    @Override
    public void run() {
        try {
            ds = new DatagramSocket();
        } catch (Exception e){
            System.out.println("Can't send info");
        }
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String str = jTextArea.getText();
                    if(str.length()==0 || str.equals("\n"))
                        return;
                    byte[] info = str.getBytes("UTF-8");
                    dp = new DatagramPacket(info,info.length, InetAddress.getByName(ipName),port);
                    ds.send(dp);
                    jTextArea.setText(""); // 发送后，清空输入框
                } catch (Exception error){}
            }
        });

    }
}
