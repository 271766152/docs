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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.roobo.ratn.demo.R;
import com.roobo.ratn.demo.source.CustomAndroidAudioGenerator;
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
 * 该模式为：WAKEUP + VAD + ASR +TTS
 */
public class WakeupModeActivity extends BaseActivity {
    private final static String TAG = "WakeupModeActivity";
    private final static int MSG_SHOW_RESULT = 0;
    private final static int MSG_TTS_PLAYER = 1;
    private final static int MSG_INIT_COMPLETE = 2;

    private VUIApi vuiApi = null;
    private Button btnStart;
    private TextView textView;
    private RecordState state = RecordState.START_RECORDER;
    private AutoTypeController controller;

    private enum RecordState {
        START_RECORDER,
        STOP_RECORDER,
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SHOW_RESULT:
                    textView.setText((String) msg.obj);
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
        setContentView(R.layout.activity_mode_wakeup);

        btnStart = (Button) findViewById(R.id.btn_start_vad_mode);
        textView = (TextView) findViewById(R.id.result_asr);
        btnStart.setOnClickListener(listener);

        vuiApi = VUIApi.getInstance();
        initVUIParam(getIntent().getStringExtra(DemoMainActivity.DEVICE_ID));
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startRecognize();
        }
    };

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
                .setAudioGenerator(new CustomAndroidAudioGenerator()) // 设置音频源
                .addOfflineFileName("test_offline") //设置离线词文件
                .setTTSType(RTTSPlayer.TTSType.TYPE_ONLINE)  //设置语音合成方式,默认是离线
                .setVUIType(VUIApi.VUIType.AUTO);  //设置交互方式，AUTO（唤醒后自动开启cloud识别 ， 说唤醒词开始， 包含 vad , 直到手动停止）

        vuiApi.init(WakeupModeActivity.this, builder.build(),
                new InitListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "vuiApi.init onSucess: called");
                        handler.obtainMessage(MSG_TTS_PLAYER, getString(R.string.tts_hint_init_complete)).sendToTarget();
                        setListener();  //当初始化成功后要设置ASR等回调接口，
                        reprotLocation(); //上报wifi信息，用于识别中用到位置信息
                    }

                    @Override
                    public void onFail(RError rError) {
                        Log.d(TAG, "onFail: " + rError.getFailDetail());
                    }
                });
    }

    /**
     * setListener 设置ASR，AI的回调接口
     */
    private void setListener() {
        //设置ASR回调接口。ASR返回的结果都在此接口中回调,不带AI。
        vuiApi.setASRListener(new RASRListener() {
            @Override
            public void onASRResult(final ASRResult result) {
                //如果是需要带AI的结果，此回调结果可以不做处理；
                Log.d(TAG, "ASRResult " + (result.getResultType() == ASRResult.TYPE_OFFLINE ? "offline " : " online ") + " text " + result.getResultText());
//                handler.obtainMessage(MSG_SHOW_RESULT, result.getResultText()).sendToTarget();
            }

            @Override
            public void onFail(final RError message) {
                handler.obtainMessage(MSG_SHOW_RESULT,
                        "asr error " + message.getFailCode() + " " + message.getFailDetail()).sendToTarget();
            }

            @Override
            public void onWakeUp(final String json) {
                Log.d(TAG, "onWakeup: " + json);
                handler.obtainMessage(MSG_SHOW_RESULT, json).sendToTarget();
            }

            @Override
            public void onEvent(EventType event) {
                Log.d(TAG, "EventType: " + event);
            }
        });

        //设置AI回调接口。AI返回的结果都在此接口中回调，如果不需要AI结果，可以不设置此回调接口。
        vuiApi.setOnAIResponseListener(new OnAIResponseListener() {
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
        });
    }

    /**
     * reprotLocation wifi信息，只有上报了wifi信息，当用户查询跟位置相关的信息时才会返回结果，比如：今天的天气怎么样
     */
    private void reprotLocation() {
        WifiManager wifiManager = (WifiManager) getApplication().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        vuiApi.reportLocationInfo(wifiManager.getScanResults());
    }

    /**
     * startRecognize 识别状态
     * 在TTS或者音乐播放的时候需要把controller设置成sleep()，进入等待唤醒状态
     */
    private void startRecognize() {
        if (state == RecordState.START_RECORDER) {
            btnStart.setText(getString(R.string.stop_vad_mode));
            state = RecordState.STOP_RECORDER;
            //开始录音，唤醒后会一直在检查人声，如果要停止需要调用stopRecognize
            controller = (AutoTypeController) vuiApi.startRecognize();
        } else if (state == RecordState.STOP_RECORDER) {
            btnStart.setText(getString(R.string.start_vad_mode));
            state = RecordState.START_RECORDER;
            //停止录音
            vuiApi.stopRecognize();
        }
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

