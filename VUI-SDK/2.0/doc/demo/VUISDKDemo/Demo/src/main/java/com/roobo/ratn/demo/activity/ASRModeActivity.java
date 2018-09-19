package com.roobo.ratn.demo.activity;

/**
 * Created by chengyijun on 2018/9/12.
 */

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.roobo.ratn.demo.R;
import com.roobo.ratn.demo.source.CustomAndroidAudioGenerator;
import com.roobo.toolkit.RError;
import com.roobo.toolkit.login.UserInfo;
import com.roobo.toolkit.recognizer.OnAIResponseListener;
import com.roobo.vui.api.InitListener;
import com.roobo.vui.api.VUIApi;
import com.roobo.vui.api.asr.RASRListener;
import com.roobo.vui.api.tts.RTTSListener;
import com.roobo.vui.api.tts.RTTSPlayer;
import com.roobo.vui.common.recognizer.ASRResult;
import com.roobo.vui.common.recognizer.EventType;

/**
 * 该模式为：ASR +TTS
 */
public class ASRModeActivity extends BaseActivity {
    private static final String TAG = VADModeActivity.class.getSimpleName();
    private final static int MSG_SHOW_RESULT = 0;
    private final static int MSG_TTS_PLAYER = 1;
    private final static int MSG_INIT_COMPLETE = 2;

    private Button btnStart;
    private TextView textViewShowResult;
    private VUIApi vuiApi;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SHOW_RESULT:
                    textViewShowResult.setText((String) msg.obj);
                    break;
                case MSG_TTS_PLAYER:
                    ttsSpeak((String) msg.obj);
                    break;
                case MSG_INIT_COMPLETE:
                    btnStart.setText(getString(R.string.down_start_asr_mode));
                    btnStart.setEnabled(true);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_asr);

        initView();
        initVUIParam(getIntent().getStringExtra(DemoMainActivity.DEVICE_ID));
    }

    /**
     * 初始化VUI SDK
     *
     * @param deviceID 设备SN号。 如果是预分配模式（请填写SN号）；如果是动态注册的模式（请填写设备的唯一标识符，必须保证唯一性）
     */
    private void initVUIParam(String deviceID) {
        Log.d(TAG, "deviceID= " + deviceID);
        UserInfo userInfo = new UserInfo();
        userInfo.setDeviceID(deviceID); //必须设置此字段
        VUIApi.InitParam.InitParamBuilder builder = new VUIApi.InitParam.InitParamBuilder();

        builder.setUserInfo(userInfo)  //设置用户信息，必须设置
                .setTTSType(RTTSPlayer.TTSType.TYPE_ONLINE) //设置语音合成方式,默认是离线,设置为在线
                .setVUIType(VUIApi.VUIType.MANUAL) //设置交互方式；手动且单次识别， 需要手动开始 和 手动停止
                .setAudioGenerator(new CustomAndroidAudioGenerator());// 设置音频源

        vuiApi = VUIApi.getInstance();
        vuiApi.init(this, builder.build(), mInitListener);//绑定初始化监听器
    }

    private void initView() {
        textViewShowResult = (TextView) findViewById(R.id.ai_result_text);
        btnStart = (Button) findViewById(R.id.vad_start_btn);
        btnStart.setOnTouchListener(mTouchListener);
    }


    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    btnStart.setText(R.string.recording);
                    //开始识别
                    vuiApi.startRecognize();
                    break;
                case MotionEvent.ACTION_UP:
                    btnStart.setText(R.string.down_start_asr_mode);
                    //结束识别
                    vuiApi.stopRecognize();
                    break;
            }
            return true;
        }
    };

    InitListener mInitListener = new InitListener() {
        @Override
        public void onSuccess() {
            btnStart.setClickable(true);
            handler.obtainMessage(MSG_TTS_PLAYER, getString(R.string.tts_hint_init_complete)).sendToTarget();
            reprotLocation();
            vuiApi.setASRListener(asrListener);//绑定ASR监听器,没有AI结果；
            vuiApi.setOnAIResponseListener(aiResponseListener); //绑定AI监听器；如果不需要AI结果，可以不设置
        }

        @Override
        public void onFail(RError message) {
            Toast.makeText(ASRModeActivity.this, "初始化失败！！！", Toast.LENGTH_SHORT).show();
        }
    };

    RASRListener asrListener = new RASRListener() {
        @Override
        public void onASRResult(ASRResult result) {
            //如果是需要带AI的结果，此回调结果可以不做处理；
            Toast.makeText(ASRModeActivity.this, "识别结果 ： "
                    + result.getResultText(), Toast.LENGTH_SHORT).show();
            Log.e(TAG, "asr result: " + result.getResultText());
        }

        @Override
        public void onFail(RError message) {
            Log.e(TAG, "asr error: " + message);
        }

        @Override
        public void onWakeUp(String json) {
            Log.e(TAG, "asr wakeup: " + json);
        }

        @Override
        public void onEvent(EventType event) {
            Log.e(TAG, "asr onEvent: " + event.toString());
        }
    };

    //设置AI回调接口。AI返回的结果都在此接口中回调，如果不需要AI结果，可以不设置此回调接口。
    OnAIResponseListener aiResponseListener = new OnAIResponseListener() {
        @Override
        public void onResult(final String json) {
            Log.d(TAG, "onAIResponse: " + json);
            handler.obtainMessage(MSG_SHOW_RESULT, json).sendToTarget();
        }

        @Override
        public void onFail(final RError rError) {
            handler.obtainMessage(MSG_SHOW_RESULT,
                    "AI error " + rError.getFailCode() + " " + rError.getFailDetail()).sendToTarget();
        }
    };

    /**
     * reprotLocation wifi信息，只有上报了wifi信息，当用户查询跟位置相关的信息时才会返回结果，比如：今天的天气怎么样
     */
    private void reprotLocation() {
        WifiManager wifiManager = (WifiManager) getApplication().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        vuiApi.reportLocationInfo(wifiManager.getScanResults());
    }

    /**
     * ttsSpeak 播放tts
     *
     * @param message tts播放的内容
     */
    private void ttsSpeak(String message) {
        vuiApi.speak(message, new RTTSListener() {

            @Override
            public void onSpeakBegin() {
                Log.d(TAG, "onSpeakBegin");
                Toast.makeText(getApplicationContext(), "speak", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
                handler.obtainMessage(MSG_INIT_COMPLETE).sendToTarget();
            }

            @Override
            public void onError(int code) {
                Log.e(TAG, "onError " + code);
            }
        });
    }
}
