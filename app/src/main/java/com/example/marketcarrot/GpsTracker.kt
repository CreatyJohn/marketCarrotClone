package com.example.marketcarrot

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.*

 /**
  *  거리를 계산하려면 싱글톤을 유지하고 계산된 거리를 여기에 추가합니다.
  *  예를 들어,
  *  DistanceTracker.totalDistance += calculatedDistance */

object DistanceTracker {
    var totalDistance: Long = 0L
}

class GpsTrackerService : Service() {

    private var startId = 0

    private lateinit var locationCallback: LocationCallback
    private lateinit var fusedClient: FusedLocationProviderClient

    private var lastLocation: Location? = null
    private val actionReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            when (intent?.extras?.getString(ACTION_NAME)) {
                ACTION_STOP_TRACKING -> stopTrackingService()
            }
        }
    }

    companion object {

        private const val GPS_ACTION = "GPS_ACTION"
        private const val ACTION_NAME = "ACTION_NAME"

        const val ACTION_START_TRACKING = "ACTION_START_TRACKING"
        const val ACTION_STOP_TRACKING = "ACTION_STOP_TRACKING"

        fun getIntent(context: Context) = Intent(context, GpsTrackerService::class.java)

        fun stopTracking(context: Context) =
            context.sendBroadcast(Intent(GPS_ACTION).apply {
                putExtra(
                    ACTION_NAME,
                    ACTION_STOP_TRACKING
                )
            })
    }

    /** 안드로이드 서비스 : 바인딩 */
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    /** 안드로이드 서비스 : 스타트 커맨드 */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        DistanceTracker.totalDistance = 0L

        this.startId = startId

        startForeground(startId, getNotification())

        startLocationTracking()

        registerReceiver(actionReceiver, IntentFilter(GPS_ACTION))

        return super.onStartCommand(intent, flags, startId)
    }


    override fun onDestroy() {
        kotlin.runCatching { unregisterReceiver(actionReceiver) }
        super.onDestroy()
    }

    private fun stopTrackingService() {

        stopForeground(true)

        stopLocationTracking()

        stopSelf(startId)

    }

    /** 권한 작업을 수행해야 한다. */
    @SuppressLint("MissingPermission")
    private fun startLocationTracking() {

        val locationRequest = LocationRequest().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 5000
            smallestDisplacement = 10.0F
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult?) {

                result?.let {
                    if (lastLocation == null) {
                        lastLocation = it.lastLocation
                        return@let
                    }

                    it.lastLocation?.let { its_last ->

                        val distanceInMeters = its_last.distanceTo(lastLocation)

                        DistanceTracker.totalDistance += distanceInMeters.toLong()

                        if (BuildConfig.DEBUG) {
                            Log.d("TRACKER", "Completed: ${DistanceTracker.totalDistance} meters, (added $distanceInMeters)")
                        }

                    }

                    lastLocation = it.lastLocation

                }

                super.onLocationResult(result)

            }

        }

        fusedClient = LocationServices.getFusedLocationProviderClient(this)
        fusedClient.requestLocationUpdates(locationRequest, locationCallback, null)

    }

    private fun stopLocationTracking() {
        fusedClient.removeLocationUpdates(locationCallback)
    }

    private fun getNotification(): Notification? {

        val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel("gps_tracker", "GPS_Tracker")
        } else {
            /** 이전 버전의 채널 ID를 사용하지 않는 경우,
            https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context) */
            ""
        }

        val b = NotificationCompat.Builder(this, channelId)

        b.setOngoing(true)
            .setContentTitle("Currently tracking GPS location...")
            .setSmallIcon(R.mipmap.ic_launcher)

        return b.build()

    }

    /** RequiresApi는 해당 함수가 해당 버전에서만 호출 되어야 한다는 것을 IDE(통합개발환경)에 알리는 역할을 함 */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String{
        val chan = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_NONE)
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }


}