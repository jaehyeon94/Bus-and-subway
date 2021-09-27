package com.example.a1.whereami;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private GpsObject gpsObject;
    int MY_PERMISSIONS_REQUEST_FINE_LOCATION;

    @Override
    protected void onStart() {
        super.onStart();
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSIONS_REQUEST_FINE_LOCATION);
        }
        else{

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //권한 허가
            }
            else{
                Log.e("권한거부!" , "사용자가 권한을 거부 하셧습니다.");
                //권한 거부
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GpsInfo gpsInfo = new GpsInfo(this);

        button = findViewById(R.id.startbtn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(MainActivity.this,R.anim.thansmaller);
                anim.setDuration(1000);
                v.startAnimation(anim);
                gpsObject = gpsInfo.getLocation();
                Toast.makeText(MainActivity.this,"lat : " + gpsObject.getLat() + "lng : " + gpsObject.getLng(),Toast.LENGTH_LONG);
                Intent intent = new Intent(MainActivity.this, TypeChoice.class);
                startActivity(intent);
            }
        });
    }
}
