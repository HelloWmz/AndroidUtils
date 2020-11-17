package com.example.admin.androidutils.ui;

/**
 * Created by Admin on 2018/5/30.
 */

public class TestJni {
  static {
    System.loadLibrary("Jnilib");
  }

  public static native String testJni();
}
