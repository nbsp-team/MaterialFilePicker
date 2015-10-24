package com.dimorinny.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import com.nbsp.materialfilepicker.ui.FilePickerActivity;

public class MainActivity extends AppCompatActivity {

    private FrameLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContainer = (FrameLayout) findViewById(R.id.container);

        Intent intent = new Intent(this, com.nbsp.materialfilepicker.ui.FilePickerActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Log.d("dmitry", String.valueOf(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH)));
        }
    }
}
