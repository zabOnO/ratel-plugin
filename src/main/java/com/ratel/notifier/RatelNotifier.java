package com.ratel.notifier;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;

import javax.annotation.Nullable;

/**
 * @author bobo
 */
public class RatelNotifier {

    public static void notifyInfo(@Nullable Project project, String content){
        notify(project, content,MessageType.INFO);
    }
    public static void notify(@Nullable Project project, String content,MessageType messageType){
        NotificationGroupManager.getInstance()
                .getNotificationGroup("ratel balloon")
                .createNotification(content, messageType)
                .notify(project);
    }
}
