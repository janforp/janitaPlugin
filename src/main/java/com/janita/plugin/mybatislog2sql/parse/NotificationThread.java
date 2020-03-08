package com.janita.plugin.mybatislog2sql.parse;

import com.intellij.notification.Notification;
import com.intellij.notification.Notifications;

import java.util.concurrent.TimeUnit;

/**
 * 类说明：
 *
 * @author zhucj
 * @since 2020/3/8 - 下午4:41
 */
public class NotificationThread extends Thread {

    private Notification notification;

    private int sleepTime;

    NotificationThread(Notification notification, int sleepTime) {
        this.notification = notification;
        this.sleepTime = sleepTime;
    }

    public NotificationThread(Notification notification) {
        this(notification, 4);
    }

    @Override
    public void run() {
        Notifications.Bus.notify(this.notification);
        try {
            TimeUnit.SECONDS.sleep(this.sleepTime);
        } catch (InterruptedException e) {
            //empty
        }
        this.notification.expire();
    }

}
