package com.ratel.dialog;

import com.intellij.openapi.ui.DialogWrapper;

import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;

/**
 * @author bobo
 */
public class StopConfirm extends DialogWrapper {

    public StopConfirm() {
        // use current window as parent
        super(true);
        setTitle("断开连接确认");
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("确认断开连接？");
        label.setPreferredSize(new Dimension(100, 30));
        dialogPanel.add(label, BorderLayout.CENTER);

        return dialogPanel;
    }
}