package com.example.admin.androidutils.ui;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.androidutils.R;

import java.util.Locale;

/**
 * @ProjectName AndroidUtils
 * @CreateDate: 2019/12/31
 * @Author: wmz
 * @Description:
 */
public class SpeechActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    //定义控件
    private Button speechButton;
    private TextView speechText;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);
        //初始化TTS
        tts = new TextToSpeech(this, this);
        //获取控件
        speechText = (TextView)findViewById(R.id.speech_view);
        speechButton = (Button)findViewById(R.id.speechButton);
        //为button添加监听
        speechButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                tts.speak(speechText.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

    }

    @Override
    public void onInit(int status){
        // 判断是否转化成功
        if (status == TextToSpeech.SUCCESS){
            //默认设定语言为中文，原生的android貌似不支持中文。
            int result = tts.setLanguage(Locale.ENGLISH);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(SpeechActivity.this, "不支持当前语言！", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        tts.stop();
        tts.shutdown();
    }


}
