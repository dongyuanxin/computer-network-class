package expriment_2_problem_2;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import static javax.swing.JScrollPane.*;

public class ChatRoom2 {
    private static JFrame jFrame;
    private static JButton confirmButton;
    private static JButton clearButton;
    private static JButton exitButton ; // 退出按钮
    private static JTextArea sendArea,receiveArea;
    public static JScrollPane createAreaWithScroll(JTextArea jTextArea){ // 生成带有滚动条的文本框
        JScrollPane jScrollPane =new JScrollPane(jTextArea);
        // 默认的设置是超过文本框才会显示滚动条，以下设置让滚动条一直显示
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        return jScrollPane;
    }
    public static JFrame createFrame(){
        JFrame jf = new JFrame("Chat Room --董沅鑫");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(new Dimension(400,400));
        jf.setVisible(true);
        // 2行1列布局
        // 第一行：接收消息
        // 第二行：发送消息框和发送按钮
        jf.setLayout(new GridLayout(2,1));
        return jf;
    }
    public static void createAndShowGUI(){
        jFrame = createFrame();
        receiveArea = new JTextArea();
        sendArea = new JTextArea();
        //第一行
        JScrollPane jScrollPane = createAreaWithScroll(receiveArea);
        jFrame.add(jScrollPane);
        // 第二行
        // 在网格布局中调整button的大小
        confirmButton = new JButton("发送");
        clearButton = new JButton("清空");
        JPanel buttonPanel = new JPanel(new GridLayout(3,1));
        buttonPanel.add(confirmButton);
        buttonPanel.add(clearButton);
        exitButton = new JButton("退出");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        buttonPanel.add(exitButton);
        buttonPanel.setBorder(new EmptyBorder(30,30,30,30));

        jScrollPane = createAreaWithScroll(sendArea);
        // 最后一行：JScrollPane + Button
        JPanel bottomPanel = new JPanel(new GridLayout(1,2));
        bottomPanel.add(jScrollPane);
        bottomPanel.add(buttonPanel);
        bottomPanel.setBorder(new EmptyBorder(20,5,5,5));
        jFrame.add(bottomPanel);
    }
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
                int sendPort = 10001;
                int receivePort = 10000;
                String ipName = "127.0.0.1";
                Sender sender = new Sender();
                sender.setPORT(sendPort);
                sender.setIpName(ipName);
                sender.setjButton(confirmButton);
                sender.setjTextArea(sendArea);
                Receiver receiver = new Receiver();
                receiver.setPORT(receivePort);
                receiver.setjTextArea(receiveArea);
                receiver.setClearButton(clearButton);
                new Thread(sender).start();
                new Thread(receiver).start();

            }
        });
    }
}

