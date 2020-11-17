package com.example.admin.androidutils.net;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Admin on 2018/6/25.
 */

public interface NetApi {
  /**
   * 登录
   */
  @POST("user/supplierLogin")
  Observable<Void> Login(@Body User user);
}
