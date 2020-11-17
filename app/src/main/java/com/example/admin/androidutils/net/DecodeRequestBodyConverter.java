package com.example.admin.androidutils.net;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * 数据加密
 */

class DecodeRequestBodyConverter<T> implements Converter<T, RequestBody> {
  private final Gson gson;
  private final TypeAdapter<T> adapter;
  private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

  public DecodeRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
    this.gson = gson;
    this.adapter = adapter;
  }

  @Override
  public RequestBody convert(T value) throws IOException {
    Log.e("http", "requestParams===:" + value.toString());
    String json = new Gson().toJson(value);
    // 对请求数据进行加密
    String secret = TripleDesUtils.getSecretKey();
    String reqSecret = null;
    try {
      reqSecret = RSAEncryptUtils.encrypt(RSAEncryptUtils.loadPublicKeyFromString(NetConfig.PUBLIC_KEY_STRING), secret);
    } catch (Exception e) {
      e.printStackTrace();
    }
    String reqInfo = TripleDesUtils.encrypt(json, secret);
    // 发送请求数据
    Map<String, String> maps = new HashMap<>();
    maps.put("reqInfo", reqInfo);
    maps.put("reqSecret", reqSecret);
    String postBody = new Gson().toJson(maps);
    return RequestBody.create(MEDIA_TYPE, postBody);
  }
}
