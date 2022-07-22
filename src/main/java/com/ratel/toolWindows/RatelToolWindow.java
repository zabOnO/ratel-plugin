package com.ratel.toolWindows;

import io.netty.handler.codec.http.websocketx.WebSocket00FrameEncoder;
import org.jetbrains.io.webSocket.WebSocketClient;

import javax.swing.*;
import java.awt.event.*;

public class RatelToolWindow {
    private JPanel context;
    private JButton restart;
    private JTextField input;
    private JButton clean;
    private JTextArea textContent;

    public RatelToolWindow() {

        restart.addActionListener(e -> textContent.append("\n"+input.getText()));

        clean.addActionListener(e -> textContent.setText(""));

        input.addActionListener(e -> textContent.append("\n"+input.getText()));


    }



    public JPanel getContext() {
        return this.context;
    }
}
