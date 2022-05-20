package com.softvelum.larixfragment.larixjavasdk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int CAMERA_REQUEST = 500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if (savedInstanceState == null) {
            checkPermissionsThenSetFragment();
        }
    }

    private void setFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.streamer, StreamerFragment.newInstance(
                        "0",
                        1280, 720,
                        "rtmp://origin-v2.vewbie.com:1935/origin/2b866520-11c5-4818-9d2a-6cfdebbb8c8a"))
                        //"rtmp://192.168.1.77:1937/live/demo"))
                .commit();
    }

    public void checkPermissionsThenSetFragment() {
        boolean cameraAllowed = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        boolean audioAllowed = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;

        if (cameraAllowed && audioAllowed) {
            setFragment();
        } else {
            String[] permissions = new String[2];
            int n = 0;
            if (!cameraAllowed) {
                permissions[n++] = Manifest.permission.CAMERA;
            }
            if (!audioAllowed) {
                permissions[n] = Manifest.permission.RECORD_AUDIO;
            }
            ActivityCompat.requestPermissions(this, permissions, CAMERA_REQUEST);
        }
    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.v("SALVE", "bora bora bora");
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.v("SALVE", "landscape");
            //Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Log.v("SALVE", "portrait");
            //Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST) {
            for (int result : grantResults) {
                if (result == PackageManager.PERMISSION_DENIED) {
                    return;
                }
            }
            setFragment();
        }
    }
}