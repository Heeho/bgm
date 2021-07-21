package ru.ltow.game;

import android.os.Bundle;
import android.widget.TextView;
import android.view.View;

public class About extends BaseA {
  TextView about;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.about);
    about = findViewById(R.id.aboutTV);
    about.setText(getText(R.string.about));
  }

  public void close(View v) {
    finish();
  }
}