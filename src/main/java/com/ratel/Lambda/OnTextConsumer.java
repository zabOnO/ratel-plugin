package com.ratel.Lambda;

/**
 * 接收onText事件的lambda
 * @author bobo
 */
public interface OnTextConsumer {
    /**
     * 处理WebSocket的onText事件
     * @param text 接收到的文本
     */
    void onText(String text);
}
