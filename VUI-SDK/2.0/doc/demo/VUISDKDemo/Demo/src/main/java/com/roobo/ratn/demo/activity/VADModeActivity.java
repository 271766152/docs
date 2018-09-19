package com.roobo.ratn.demo.activity;

/**
 * Created by  on 2018/9/12.
 */

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.roobo.ratn.demo.R;
import com.roobo.ratn.demo.source.CustomAndroidAudioGenerator;
import com.roobo.ratn.demo.util.AIResultParser;
import com.roobo.ratn.demo.util.MediaPlayerUtil;
import com.roobo.toolkit.RError;
import com.roobo.toolkit.login.UserInfo;
import com.roobo.toolkit.recognizer.OnAIResponseListener;
import com.roobo.vui.api.AutoTypeController;
import com.roobo.vui.api.InitListener;
import com.roobo.vui.api.VUIApi;
import com.roobo.vui.api.asr.RASRListener;
import com.roobo.vui.api.tts.RTTSListener;
import com.roobo.vui.api.tts.RTTSPlayer;
import com.roobo.vui.common.recognizer.ASRResult;
import com.roobo.vui.common.recognizer.EventType;


/**
 * 该模式为：VAD + ASR + TTS
 */
public class VADModeActivity extends BaseActivity {
    private static final String TAG = VADModeActivity.class.getSimpleName();
    private final static int MSG_SHOW_RESULT = 0;
    private final static int MSG_TTS_PLAYER = 1;
    private final static int MSG_INIT_COMPLETE = 2;

    private boolean isRecording = false;
    private VUIApi vuiApi;
    private Button btnStart;
    private TextView asrResultText;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SHOW_RESULT:
                    asrResultText.setText((String) msg.obj);
                    break;
                case MSG_TTS_PLAYER:
                    ttsSpeak((String) msg.obj);
                    break;
                case MSG_INIT_COMPLETE:
                    btnStart.setText(getString(R.string.start_vad_mode));
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
        setContentView(R.layout.activity_mode_vad);

        initView();
        initVUIParam(getIntent().getStringExtra(DemoMainActivity.DEVICE_ID));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VUIApi.getInstance().stopRecognize();
        VUIApi.getInstance().release();
        MediaPlayerUtil.stop();
    }

    private void initVUIParam(String deviceID) {
        Log.d(TAG, "deviceID= " + deviceID);
        UserInfo userInfo = new UserInfo();
        userInfo.setDeviceID(deviceID); //必须设置此字段

        VUIApi.InitParam.InitParamBuilder builder = new VUIApi.InitParam.InitParamBuilder();

        builder.setUserInfo(userInfo)  //设置用户信息，必须设置
                .setAudioGenerator(new CustomAndroidAudioGenerator()) // 设置音频源
                .addOfflineFileName("test_offline") //设置离线词文件
                .setTTSType(RTTSPlayer.TTSType.TYPE_ONLINE)  //设置语音合成方式,默认是离线
                .setVUIType(VUIApi.VUIType.AUTO);  //设置交互方式，AUTO（唤醒后自动开启cloud识别 ， 说唤醒词开始， 包含 vad , 直到手动停止）

        vuiApi = VUIApi.getInstance();
        vuiApi.init(this, builder.build(), initListener);//绑定初始化监听器
    }

    private void initView() {
        asrResultText = (TextView) findViewById(R.id.result_vad_mode);
        btnStart = (Button) findViewById(R.id.btn_start_vad_mode);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRecording) {
                    isRecording = true;
                    btnStart.setText("点击结束识别");
                    //开始识别
                    AutoTypeController controller = (AutoTypeController) vuiApi.startRecognize();
                    //开始识别后，将控制器设置成已经唤醒的状态；如果在进行语音播放的时候需要调用pseudoSleep()关闭在线识别
                    //在音乐播放完成后调用cancelPseudoSleep()打开在线识别；
                    if (controller instanceof AutoTypeController) {
                        ((AutoTypeController) controller).manualWakeup();
                    }
                } else {
                    btnStart.setText("点击开始识别");
                    isRecording = false;
                    //结束识别
                    vuiApi.stopRecognize();
                }
            }
        });
    }

    InitListener initListener = new InitListener() {
        @Override
        public void onSuccess() {

            vuiApi.setASRListener(asrListener);//绑定ASR监听器,没有AI结果；
            vuiApi.setOnAIResponseListener(aiResponseListener); //绑定AI监听器；如果不需要AI结果，可以不设置
            reprotLocation(); //上报wifi信息，用于识别中用到位置信息

            handler.obtainMessage(MSG_INIT_COMPLETE).sendToTarget();
        }

        @Override
        public void onFail(RError message) {
            Toast.makeText(VADModeActivity.this, "初始化失败！！！message = " + message.getFailDetail(), Toast.LENGTH_SHORT).show();
        }
    };

    //设置ASR回调接口。ASR返回的结果都在此接口中回调,不带AI。
    RASRListener asrListener = new RASRListener() {
        @Override
        public void onASRResult(ASRResult result) {
            //如果是需要带AI的结果，此回调结果可以不做处理；
            Log.d(TAG, "ASRResult " + (result.getResultType() == ASRResult.TYPE_OFFLINE ? "offline " : " online ") + " text " + result.getResultText());
//                handler.obtainMessage(MSG_SHOW_RESULT, result.getResultText()).sendToTarget();
        }

        @Override
        public void onFail(RError message) {
            Log.e(TAG, "asr error: " + message.getFailDetail());
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

    /**
     * reprotLocation wifi信息，只有上报了wifi信息，当用户查询跟位置相关的信息时才会返回结果，比如：今天的天气怎么样
     */
    private void reprotLocation() {
        WifiManager wifiManager = (WifiManager) getApplication().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        vuiApi.reportLocationInfo(wifiManager.getScanResults());
    }

    //设置AI回调接口。AI返回的结果都在此接口中回调，如果不需要AI结果，可以不设置此回调接口。
    OnAIResponseListener aiResponseListener = new OnAIResponseListener() {
        @Override
        public void onResult(final String json) {
            Log.e(TAG, "ai json: " + json);
            handler.obtainMessage(MSG_TTS_PLAYER, json).sendToTarget();
            if (AIResultParser.isStartPlayer(json)) {
                String url = AIResultParser.parserMP3UrlFromAIResultJSON(json);
                if (!TextUtils.isEmpty(url)) {
                    MediaPlayerUtil.playByUrl(url);
                }
            } else if (AIResultParser.isExitPlayer(json)) {
                MediaPlayerUtil.stop();
            }

            String hint = AIResultParser.parserHintFromAIResultJSON(json);
            if (!TextUtils.isEmpty(hint)) {
                handler.obtainMessage(MSG_TTS_PLAYER, hint).sendToTarget();
            }
        }

        @Override
        public void onFail(RError message) {
            Log.e(TAG, "ai fail: " + message.getFailDetail());
        }
    };

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
            }

            @Override
            public void onError(int code) {
                Log.e(TAG, "onError " + code);
            }
        });
    }
}
