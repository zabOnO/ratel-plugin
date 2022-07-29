package com.ratel.notifier;

import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;

import javax.annotation.Nullable;

/**
 * @author bobo
 */
public class RatelNotifier {

    private static final NotificationGroup NOTIFICATION_GROUP =
            new NotificationGroup("ratel balloon", NotificationDisplayType.BALLOON, true);

    public static void notifyInfo(@Nullable Project project, String content){
        notify(project, content,MessageType.INFO);
    }

    public static void notify(@Nullable Project project, String content,MessageType messageType){
        NOTIFICATION_GROUP.createNotification(content, messageType)
                .notify(project);
    }
}
