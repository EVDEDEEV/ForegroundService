package my.project.foregroundservice

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import my.project.foregroundservice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            startStopService()
        }

    }

    private fun startStopService() {
        if (isMyServiceRunning(MyService::class.java)) {

            Toast.makeText(this,
            "Service Stopped",
            Toast.LENGTH_SHORT
                ).show()

            stopService(Intent(this,
                MainActivity::class.java))

        }else{

            Toast.makeText(this,
                "Service Started",
                Toast.LENGTH_SHORT
            ).show()

            startService(Intent(this,
                MainActivity::class.java))
        }
    }

    private fun isMyServiceRunning(mClass: Class<MyService>): Boolean {

        val manager: ActivityManager = getSystemService(
            Context.ACTIVITY_SERVICE
        ) as ActivityManager

        for (service: ActivityManager.RunningServiceInfo in
        manager.getRunningServices(Integer.MAX_VALUE)) {

            if (mClass.name.equals(service.service.className)) {
                return true
            }
        }
        return false
    }
}