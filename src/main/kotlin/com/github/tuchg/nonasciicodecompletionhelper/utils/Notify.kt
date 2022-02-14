package com.github.tuchg.nonasciicodecompletionhelper.utils

import com.intellij.notification.*
import com.intellij.notification.impl.NotificationsManagerImpl
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.Balloon
import com.intellij.openapi.wm.WindowManager
import com.intellij.ui.BalloonLayoutData
import com.intellij.ui.awt.RelativePoint
import java.awt.Point


/**
 * @author: github.com/izhangzhihao
 * @description:
 */
fun createNotification(
    title: String, content: String,
    type: NotificationType, listener: NotificationListener
): Notification {
    //2020.3 later
    return NotificationGroupManager.getInstance().getNotificationGroup("Pinyin Completion Helper Notification Group")
        .createNotification(title, content, type).setListener(listener)
}

fun showFullNotification(project: Project, notification: Notification) {
    val frame = WindowManager.getInstance().getIdeFrame(project)
    if (frame == null) {
        notification.notify(project)
        return
    }
    val bounds = frame.component.bounds
    val target = RelativePoint(frame.component, Point(bounds.x + bounds.width, 20))

    try {
        val balloon = NotificationsManagerImpl.createBalloon(
            frame,
            notification,
            true, // showCallout
            false, // hideOnClickOutside
            BalloonLayoutData.fullContent(),
            project
        )
        balloon.show(target, Balloon.Position.atLeft)
    } catch (e: Exception) {
        notification.notify(project)
    }
}
