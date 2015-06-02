package com.changfeng.mytest;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = (Button) findViewById(R.id.start_button);
        Button exitButton = (Button) findViewById(R.id.exit_button);
        TextView textView = (TextView) findViewById(R.id.text_view);

        startButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);

        textView.setText(getString(R.string.elapse_time) + ":" +Utils.getElapsedTimes());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_button:
                startService(new Intent(this, LongRunningService.class));
                break;
            case R.id.exit_button:
                finish();
                break;
        }
    }
}
