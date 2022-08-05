package com.ratel.webSocket;

import com.alibaba.fastjson2.JSON;
import com.ratel.Lambda.OnTextConsumer;
import com.ratel.entity.User;
import com.ratel.entity.WsData;

import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

/**
 * @author bobo
 */
public class WsClient {
    private static final String INTERACTIVE_SIGNAL_START = "INTERACTIVE_SIGNAL_START";
    private static final String INTERACTIVE_SIGNAL_STOP = "INTERACTIVE_SIGNAL_STOP";

    private boolean is = true;
    private WebSocket webSocket;

    private String nickname;
    private String url;

    public WsClient(String url, String nickname) {
        this.url = url;
        this.nickname = nickname;
    }

    public void init(OnTextConsumer onTextConsumer) throws ExecutionException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().proxy(ProxySelector.getDefault()).build();
        CompletableFuture<WebSocket> ws = client.newWebSocketBuilder().buildAsync(URI.create(url), new WebSocket.Listener() {
            @Override
            public void onOpen(WebSocket webSocket) {
                System.out.println("onOpen");
                User user = new User(System.currentTimeMillis(), nickname, 100);
                WsData data = new WsData(JSON.toJSONString(user));
                webSocket.sendText(JSON.toJSONString(data), true);
                WebSocket.Listener.super.onOpen(webSocket);
            }

            @Override
            public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
                System.out.println("onText:" + data.toString());
                WsData wsData = JSON.parseObject(data.toString(), WsData.class);
                if (INTERACTIVE_SIGNAL_START.equals(wsData.getData())) {
                    is = true;
                } else if (INTERACTIVE_SIGNAL_STOP.equals(wsData.getData())) {
                    is = false;
                } else {
                    onTextConsumer.onText(wsData.getData());
                }
                return WebSocket.Listener.super.onText(webSocket,data,last);
            }
        });
        is = true;
        this.webSocket = ws.get();
    }

    public void sendMsg(String msg) {
        if (is) {
            this.webSocket.sendText(JSON.toJSONString(new WsData(msg)), true);
        }
    }

    public void close() {
        this.webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "ok");
    }

    public void close(int statusCode, String reason) {
        this.webSocket.sendClose(statusCode, reason);
    }

    public void abort() {
        this.webSocket.abort();
    }

    public boolean isIs() {
        return is;
    }

    public void setIs(boolean is) {
        this.is = is;
    }

    public WebSocket getWebSocket() {
        return webSocket;
    }

    public void setWebSocket(WebSocket webSocket) {
        this.webSocket = webSocket;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
