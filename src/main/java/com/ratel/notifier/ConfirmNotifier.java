package com.ratel.notifier;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import org.jetbrains.annotations.Nullable;

/**
 * @author bobo
 */
public class ConfirmNotifier {

    public static void notifyInfo(@Nullable Project project, String content){
        notify(project, content,MessageType.INFO);
    }
    public static void notify(@Nullable Project project, String content,MessageType messageType){
        NotificationGroupManager.getInstance()
                .getNotificationGroup("confirm balloon")
                .createNotification(content, messageType)
                .setListener((notification, hyperlinkEvent) -> hyperlinkEvent.getEventType())
                .notify(project);
    }
}
