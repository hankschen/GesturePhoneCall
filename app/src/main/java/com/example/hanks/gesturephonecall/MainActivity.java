package com.example.hanks.gesturephonecall;

import android.content.Intent;
import android.content.SharedPreferences;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    GestureOverlayView overlayView;
    Button btnAddgesture,btnListGesture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findviews();
    }

    void findviews(){
        overlayView=(GestureOverlayView)findViewById(R.id.gestureOverlayView);
        overlayView.addOnGesturePerformedListener(performedListener);
        btnAddgesture=(Button)findViewById(R.id.button);
        btnListGesture=(Button)findViewById(R.id.button2);
    }

    //監聽劃出手勢圖形
    GestureOverlayView.OnGesturePerformedListener performedListener=new GestureOverlayView.OnGesturePerformedListener() {
        @Override
        public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
            File path= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File file=new File(path,"gesture");
            GestureLibrary library= GestureLibraries.fromFile(file);
            ArrayList<Prediction> predictions=library.recognize(gesture);

            SharedPreferences phoneBook=getSharedPreferences("phoneBook", MODE_PRIVATE);

            String phoneNum=phoneBook.getString(predictions.get(0).name, "");
            Uri uri=Uri.parse("tel:" + phoneNum);
//            Intent it=new Intent(Intent.ACTION_CALL); //����n�[�v��android.premission.CALL_PHONE
            Intent it=new Intent(Intent.ACTION_VIEW);
            it.setData(uri);
            startActivity(it);
        }
    };

    public void addGesture(View v){
        Intent it=new Intent(MainActivity.this,AddGestureActivity.class);
        startActivity(it);
    }
    public void listGesture(View v){
        Intent it=new Intent(MainActivity.this,ListGestureActivtiy.class);
        startActivity(it);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
