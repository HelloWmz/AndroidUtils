package com.example.admin.androidutils.net;

import com.example.admin.androidutils.BuildConfig;
import com.example.admin.androidutils.ui.MyApplication;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * RetrofitManager
 */

public class RetrofitManager {

  //短缓存1分钟
  private static final int CACHE_AGE_SHORT = 60;
  //长缓存有效期为1天
  private static final int CACHE_STALE_LONG = 60 * 60 * 24;
  private static String BASE_HOST_URLS = "http://192.168.100.147:8080/api/";

  private static OkHttpClient mOkHttpClient;
  private static final String PATH_DATA = MyApplication.getContextObject().getCacheDir().getAbsolutePath() + File.separator + "data";
  private static final String PATH_CACHE = PATH_DATA + File.separator + "NetCache";


  private static RetrofitManager instance = new RetrofitManager();
  private final Retrofit mRetrofit;

  public static RetrofitManager getInstance() {
    return instance;
  }

  private RetrofitManager() {
    initOkHttpClient();
    mRetrofit = new Retrofit.Builder()
         .baseUrl(BASE_HOST_URLS)
         .client(mOkHttpClient)
         .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
         //.addConverterFactory(GsonConverterFactory.create())
         .addConverterFactory(DecodeConverterFactory.create())
         .build();
  }

  private void initOkHttpClient() {
    if (mOkHttpClient == null) {
      synchronized (RetrofitManager.class) {
        if (mOkHttpClient == null) {
          HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
          if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
          } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
          }
          OkHttpClient.Builder builder = new OkHttpClient.Builder();
          // 设置缓存文件路径
          File cacheFile = new File(PATH_CACHE);
          Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
          mOkHttpClient = builder
               //设置缓存
               //.addNetworkInterceptor(mCacheControlInterceptor)
               // .cache(cache)
               //设置超时
               .connectTimeout(60, TimeUnit.SECONDS)
               .readTimeout(60, TimeUnit.SECONDS)
               .writeTimeout(60, TimeUnit.SECONDS)
               //错误重连
               .retryOnConnectionFailure(true)
               .addInterceptor(interceptor)
              // .addInterceptor(mHeaderInterceptor)
               .build();
        }
      }
    }

  }

  private Interceptor mHeaderInterceptor = chain -> {
    Request request = chain.request()
         .newBuilder()
         .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
         .addHeader("Content-Type", "application/json")
         .addHeader("Content-Type", "text/json")
         .build();
    return chain.proceed(request);
  };
  /**
   * 缓存拦截器
   */
  private Interceptor mCacheControlInterceptor = chain -> {
    Request request = chain.request();
    if (!NetWorkUtil.isNetworkAvailable()) {
      request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
    }
    Response response = chain.proceed(request);
    if (NetWorkUtil.isNetworkAvailable()) {
      // 有网络时
      response.newBuilder()
           .header("Cache-Control", "public, max-age=" + CACHE_AGE_SHORT)
           .removeHeader("Pragma")
           .build();
    } else {
      // 无网络时，设置缓存
      response.newBuilder()
           .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_LONG)
           .removeHeader("Pragma")
           .build();
    }
    return response;
  };

  /**
   * @param <T> api 类
   */
  public <T> T createServiceFrom(Class<T> serviceClass) {
    return mRetrofit.create(serviceClass);
  }
}
