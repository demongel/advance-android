package com.shakespace.advanceandroid.chapter01newfeature

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.shakespace.advanceandroid.R
import kotlinx.android.synthetic.main.fragment_notification.*

/**
 * A simple [Fragment] subclass.
 */
class NotificationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        /**
         * 1. 创建notificationManager
         * 2. 8.0以上，创建notificationChannel ，并调用createNotificationChannel
         * 3. 创建一个intent
         * 4. 根据intent创建一个pendingIntent
         * 5. 创建Notification， 注意channelld 要和notificationChannel 第一个参数相同
         * 6. 调用 notificationManager?.notify(1,notification)
         *
         * refer link https://developer.android.google.cn/guide/topics/ui/notifiers/notifications
         */
        val notificationManager =
            ContextCompat.getSystemService(requireContext(), NotificationManager::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel("android", "advance", NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.vibrationPattern = longArrayOf(10, 20, 40)
            notificationManager?.createNotificationChannel(notificationChannel)
        }

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.baidu.com"))

//        还可以 PendingIntent.getActivities()
        val pendingIntent = PendingIntent.getActivity(requireContext(), 0, intent, 0)

        val builder = NotificationCompat.Builder(requireContext(), "android").apply {
            setAutoCancel(true)
            /**
             *  setContentIntent(pendingIntent) : 点击时触发
             *   setDeleteIntent(pendingIntent) ： 清除时触发
             *  setFullScreenIntent(pendingIntent,true) : 当有交互时就触发，10以上需要权限
             *  Use a full-screen intent only for the highest-priority alerts where you
            have an associated activity that you would like to launch after the user
            interacts with the notification. Also, if your app targets Android 10
            or higher, you need to request the USE_FULL_SCREEN_INTENT permission in
            order for the platform to invoke this notification.
             */
            setContentIntent(pendingIntent)
            setContentTitle("普通Notification")
            setContentText("这是一个普通notification")
            setSmallIcon(R.drawable.apple)
            setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.strawberry))
        }

        val remoteView =
            RemoteViews(requireContext().packageName, R.layout.fragment_notification_fold)
        val remoteViewSmall =
            RemoteViews(requireContext().packageName, R.layout.fragment_notification_fold_small)

        /**
         * 模拟器上可以看到smallIcon，16X看不到
         */
        tv_common.setOnClickListener {
            notificationManager?.notify(1, builder.build())
        }

        /**
         * 在模拟器上先展示普通横幅，下拉可以展开，但不点横幅，直接下拉通知来看到的是大图
         * 参考 https://developer.android.google.cn/training/notify-user/custom-notification
         * 需要同时设置
         * setStyle(NotificationCompat.DecoratedCustomViewStyle())
         * setCustomContentView(remoteViewSmall)
         *  setCustomBigContentView(remoteView)
         */
        tv_folder.setOnClickListener {
            notificationManager?.notify(2, with(builder) {
                setContentTitle("折叠Notification")
                setContentText("这是一个折叠notification")
                setStyle(NotificationCompat.DecoratedCustomViewStyle())
                setCustomContentView(remoteViewSmall)
                setCustomBigContentView(remoteView)
            }.build())
        }

        tv_hand.setOnClickListener {
            notificationManager?.notify(3, with(builder) {
                setContentTitle("悬挂Notification")
                setContentText("这是一个悬挂notification")
                setStyle(
                    NotificationCompat.BigPictureStyle().bigPicture(
                        BitmapFactory.decodeResource(resources, R.drawable.banner)
                    )
                )
                /**
                 * VISIBILITY_PUBLIC
                 * VISIBILITY_PRIVATE
                 * VISIBILITY_SECRET
                 */
                setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            }.build())
        }

    }

}
