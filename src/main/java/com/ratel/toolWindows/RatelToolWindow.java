package com.ratel.toolWindows;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.ui.Gray;
import com.ratel.RatelBundle;
import com.ratel.dialog.StopConfirm;
import com.ratel.notifier.RatelNotifier;
import com.ratel.webSocket.WsClient;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicLabelUI;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author bobo
 */
public class RatelToolWindow {
    private JPanel context;
    private JButton restart;
    private JTextField input;
    private JButton clean;
    private JTextArea textContent;
    private JSplitPane splitPane;
    private JScrollPane scroll;
    private JLabel authorLink;
    private JLabel qqLink;
    private JLabel ruleLink;
    private JPanel title;
    private JLabel copyQQ;
    private JButton stop;

    private boolean isConnect;
    private boolean isClose;
    private WsClient wsClient;

    private final List<JLabel> links = List.of(authorLink, qqLink, ruleLink, copyQQ);

    public RatelToolWindow() {
        this.initStyle();
        this.initListener();
        this.printStart();
    }

    public void initStyle() {
        //设置分割线为黑色，去掉边框
        splitPane.setUI(new BasicSplitPaneUI() {
            @Override
            public BasicSplitPaneDivider createDefaultDivider() {
                return new BasicSplitPaneDivider(this) {
                    @Override
                    public void paint(Graphics g) {
                        g.setColor(Gray._43);
                        g.fillRect(0, 0, getWidth(), getHeight());
                        super.paint(g);
                    }

                    @Override
                    public void setBorder(Border border) {
                        super.setBorder(null);
                    }
                };
            }
        });

        links.forEach(link -> link.setUI(new BasicLabelUI() {
            @Override
            public void paint(Graphics g, JComponent component) {
                super.paint(g, component);
                JLabel c = (JLabel) component;
                Rectangle r = g.getClipBounds();
                int y1;
                int y2 = y1 = r.height - c.getFontMetrics(c.getFont()).getDescent() + 2;
                g.drawLine(0, y1, c.getFontMetrics(c.getFont()).stringWidth(c.getText()), y2);
            }
        }));

    }

    public void initListener() {
        restart.addActionListener(e -> restart());

        clean.addActionListener(e -> clean());

        input.addActionListener(e -> inputHandle());

        links.forEach(link -> link.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                e.getComponent().setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                e.getComponent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }));

        authorLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                BrowserUtil.browse(RatelBundle.message("toAuthorLink"));
            }
        });

        qqLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                BrowserUtil.browse(URLDecoder.decode(RatelBundle.message("toQQGroupLink"), StandardCharsets.UTF_8));
            }
        });

        ruleLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                BrowserUtil.browse(RatelBundle.message("toRatleGitHubLink"));
            }
        });
        copyQQ.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CopyPasteManager.getInstance().setContents(new StringSelection(RatelBundle.message("QQGroupNo")));
                RatelNotifier.notifyInfo(null, RatelBundle.message("copied"));
            }
        });
        stop.addActionListener(e -> {
            if (new StopConfirm().showAndGet()){
                stop();
            }
        });
    }

    public JPanel getContext() {
        return this.context;
    }

    private void inputHandle() {
        if (isConnect) {
            wsClient.sendMsg(input.getText());
        } else if (!isClose){
            String text = input.getText();
            if (text == null || text.isEmpty()) {
                textContent.append("\nNickname不能为空");
                this.printStart();
            } else if (text.length() > 10) {
                textContent.append("\nNickname不能超出10个字符");
                this.printStart();
            } else {
                textContent.append(text);
                this.wsClient = new WsClient(RatelBundle.message("serverUrl"), text);
                try {
                    wsClient.init(msg -> {
                        textContent.append("\n" + msg);
                        SwingUtilities.invokeLater(this::roll2Bottom);
                    });
                    this.isConnect = true;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    textContent.append("\n连接失败，请重试！");
                }
            }
        }
        input.setText("");
    }

    private void stop() {
        if (!isClose && isConnect) {
            wsClient.abort();
            isClose = true;
            isConnect = false;
            textContent.append("已关闭");
            RatelNotifier.notifyInfo(null, RatelBundle.message("closed"));
        }
    }

    private void clean() {
        textContent.setText("");
        title.setVisible(false);
    }

    private void restart() {
        wsClient.abort();
        textContent.setText("");
        title.setVisible(true);
        this.printStart();
        isConnect = false;
        isClose = false;
    }

    private void roll2Bottom() {
        scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
    }

    private void printStart() {
        textContent.append("\nNickname: ");
    }
}
