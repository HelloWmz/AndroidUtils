package com.example.admin.androidutils.net;

import android.util.Log;

import com.google.gson.TypeAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 获取数据解密数据
 */

class DecodeResponseBodyConverter<T> implements Converter<ResponseBody, T> {

  private final TypeAdapter<T> adapter;

  public DecodeResponseBodyConverter(TypeAdapter<T> adapter) {
    this.adapter = adapter;
  }

  @Override
  public T convert(ResponseBody responseBody) throws IOException {
    String response = responseBody.string();
    try {
      JSONObject jsonObject = new JSONObject(response);
      String Code = jsonObject.getString(NetConfig.RtnCode);
      Log.e("http","ResultInfo===:" + jsonObject);
    } catch (JSONException e) {
      e.printStackTrace();
    }



    //String strResult = response.substring(1, response.length() - 1);
    //  String result = XXTEA.Decrypt(strResult, HttpConstant.KEY);//解密
    //PageBean pageBean = mGson.fromJson(result, PageBean.class);
    //需要注意的是，response是将string转换成T，string需要先解密成json再转换成T，同样要注意你的T指代的谁。
    //return (T) pageBean;
    return null;
  }
}
