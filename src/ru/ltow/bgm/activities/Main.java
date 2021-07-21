package ru.ltow.game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Main extends BaseA {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
  }

  public void gotoActivity(View v) {
    Class<?> c = getClass();
    switch(v.getId()) {
      case R.id.optionsB: c = Options.class; break;
      case R.id.aboutB: c = About.class; break;
    }
    startActivity(new Intent(this, c));
  }
}