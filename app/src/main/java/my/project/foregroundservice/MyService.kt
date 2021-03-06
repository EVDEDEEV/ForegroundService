package my.project.foregroundservice

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import my.project.foregroundservice.Constants.CHANNEL_ID
import my.project.foregroundservice.Constants.MUSIC_NOTIFICATION_ID

class MyService : Service() {

    private lateinit var musicPlayer: MediaPlayer

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        initMusic()
        crateNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showNotification()
        startMusic()

        return START_STICKY_COMPATIBILITY
    }

    private fun startMusic() {
        musicPlayer.start()
    }


    @SuppressLint("NewApi")
    private fun showNotification() {
        val notificationIntent = Intent(this, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent, PendingIntent.FLAG_MUTABLE
        )

        val notification = Notification
            .Builder(this, CHANNEL_ID)
            .setContentText("Music Player")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(MUSIC_NOTIFICATION_ID, notification)

    }

    private fun crateNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val serviceChannel = NotificationChannel(
                CHANNEL_ID, "My Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager = getSystemService(
                NotificationManager::class.java
            )

            manager.createNotificationChannel(serviceChannel)
        }
    }


    private fun initMusic() {
        musicPlayer = MediaPlayer.create(this,
            R.raw.classic)
        musicPlayer.isLooping = true
        musicPlayer.setVolume(100F, 100F)
    }

    override fun onDestroy() {
        super.onDestroy()
        musicPlayer.stop()
    }


}