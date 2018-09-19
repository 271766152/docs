package com.roobo.ratn.demo.activity;

import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.roobo.ratn.demo.R;

import java.lang.reflect.Method;

/**
 * Created by chengyijun on 2018/9/12.
 */

public class DemoMainActivity extends BaseActivity implements View.OnClickListener {
    private final static String TAG = "DemoMainActivity";
    public final static String DEVICE_ID = "device_id";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_main);

        TextView tv = (TextView) findViewById(R.id.text_device_id);
        String exchange = getResources().getString(R.string.sn_instruction);

        tv.setText(Html.fromHtml(exchange));
        findViewById(R.id.vui_mode_btn_1).setOnClickListener(this);
        findViewById(R.id.vui_mode_btn_2).setOnClickListener(this);
        findViewById(R.id.vui_mode_btn_3).setOnClickListener(this);
        checkRecordStateSuccess();
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.vui_mode_btn_1://WAKEUP + VAD + ASR +TTS
                intent = new Intent(this, WakeupModeActivity.class);
                break;
            case R.id.vui_mode_btn_2://VAD + ASR +TTS
                intent = new Intent(this, VADModeActivity.class);
                break;
            case R.id.vui_mode_btn_3://ASR + TTS
                intent = new Intent(this, ASRModeActivity.class);
                break;
            default:
                break;
        }
        String deviceID = getDeviceID();
        if (deviceID == null || deviceID.isEmpty()) {
            Toast.makeText(getApplicationContext(), getString(R.string.device_id_empty), Toast.LENGTH_SHORT).show();
            return;
        }
        if (intent != null) {
            intent.putExtra(DEVICE_ID, getDeviceID());
            startActivity(intent);
        }
    }

    /**
     * 获取设备唯一标识符，分为两种方式：
     * 1 预分配并生产线烧录方式，直接将分配的SN号赋值给deviceID；
     * 2 通过唯一字串在线注册方式，获取设备的唯一标识符赋值给deviceID；
     *
     * @return
     */
    private String getDeviceID() {
        String deviceID = "";
        return deviceID;
    }

    /**
     * 获取设备的唯一标识符，此函数做为参考，不保证返回标识符的唯一性，开发者需要根据自身设备的特性自己实现
     *
     * @return
     */
    private String getDeviceSerialNO() {
        String serial = "";
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            serial = (String) get.invoke(c, "ro.serialno");
        } catch (Exception ignored) {
        }
        if (TextUtils.isEmpty(serial)) {
            serial = android.os.Build.SERIAL;
        }
        return serial;
    }

    /**
     * checkRecordStateSuccess 检查录音是否正常
     *
     * @return
     */
    public static boolean checkRecordStateSuccess() {
        int minBuffer = AudioRecord.getMinBufferSize(16000, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
        AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.DEFAULT, 16000, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, (minBuffer * 2));
        short[] point = new short[minBuffer];
        int readSize = 0;
        try {
            audioRecord.startRecording();//检测是否可以进入初始化状态
        } catch (Exception e) {
            if (audioRecord != null) {
                audioRecord.release();
                Log.d("CheckAudioPermission", "无法进入录音初始状态");
            }
            return false;
        }
        if (audioRecord.getRecordingState() != AudioRecord.RECORDSTATE_RECORDING) {
            //6.0以下机型都会返回此状态，故使用时需要判断bulid版本
            //检测是否在录音中
            if (audioRecord != null) {
                audioRecord.stop();
                audioRecord.release();
                Log.d("CheckAudioPermission", "录音机被占用");
            }
            return false;
        } else {
            //检测是否可以获取录音结果

            readSize = audioRecord.read(point, 0, point.length);
            if (readSize <= 0) {
                if (audioRecord != null) {
                    audioRecord.stop();
                    audioRecord.release();

                }
                Log.d("CheckAudioPermission", "录音的结果为空");
                return false;

            } else {
                if (audioRecord != null) {
                    audioRecord.stop();
                    audioRecord.release();
                }

                return true;
            }
        }
    }
}
