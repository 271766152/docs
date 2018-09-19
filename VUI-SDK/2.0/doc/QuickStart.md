
快速开始
=

**VUISDK提供了多种输出模式，Demo中我们为您提供了3种最常用的输出模式。**

- 模式一： WAKEUP + VAD + ASR + TTS  
- 模式二： VAD + ASR + TTS  
- 模式三： ASR + TTS  

<center>
<img src="https://github.com/271766152/docs/blob/master/VUI-SDK/2.0/doc/img/demo1.png" width="30%" height="30%" />
</center>

**功能介绍**  

- [WAKEUP]： 为您提供语音唤醒设备的能力，默认唤醒词为“智能管家”。  
- [VAD]： 为您提供语音端点检测的能力，检测语音的开始和结束。  
- [ASR]： 为您提供语音识别的能力，支持在线和离线。为您返回语音识别的结果和语义结果。  
- [TTS]： 为您提供文本转语音的能力，支持在线和离线。  

<br></br>

**现在以模式二 VAD+ASR+TTS 为例开始我们的Demo。**  

**1. 导入sdk**  
目前SDK是以aar形式提供，所以需要使用Android Studio开发。把"ratn-release-xx-online.aar"拷贝到Libs文件夹下。在muoudle的build.gradle文件中添加。

``` gradle
    repositories {
        flatDir { dirs 'libs' }
    }

    dependencies {
        compile(name:'ratn-release-1.0-online',ext:'aar')
    }
```

**2. 创建一个带有按钮的页面**  

<center>
<img src="https://github.com/271766152/docs/blob/master/VUI-SDK/2.0/doc/img/demo2.png" width="30%" height="30%" />
</center>


**3. onCreate**  
```Java
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_vad);

        initView();
        initVUIParam();
    }
``` 

**4. view 初始化**  
```Java
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
                    //开始识别后，将控制器设置成已经唤醒的状态
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
```

**5. initVUIParam(String deviceID)初始化参数**    
```Java
    private void initVUIParam(String deviceID) {
        Log.d(TAG, "deviceID= " + deviceID);
        UserInfo userInfo = new UserInfo();
        userInfo.setDeviceID(deviceID); //必须设置此字段

        VUIApi.InitParam.InitParamBuilder builder = new VUIApi.InitParam.InitParamBuilder();

        builder.setUserInfo(userInfo)//设置用户信息，必须设置
                .setAudioGenerator(new CustomAndroidAudioGenerator())//设置音频源
                .addOfflineFileName("test_offline")//设置离线词文件
                .setTTSType(RTTSPlayer.TTSType.TYPE_ONLINE)//设置语音合成方式,默认是离线
                .setVUIType(VUIApi.VUIType.AUTO);//设置交互方式，AUTO（唤醒后自动开启cloud识别，说唤醒词开始，包含VAD, 直到手动停止）

        vuiApi = VUIApi.getInstance();
        vuiApi.init(this, builder.build(), initListener);//绑定初始化监听器
    }
```

**6. getDeviceID** *注：deveceID可以查看如何[申请SN号](https://github.com/271766152/docs/blob/master/VUI-SDK/2.0/doc/%E8%B4%A6%E5%8F%B7%E7%94%B3%E8%AF%B7%E6%96%B9%E6%B3%95.md)。*  
```Java
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
```

**7. 其它重要的方法和监听器设置**

- **InitListener  初始化回调**  
```Java 
    InitListener initListener = new InitListener() {
        @Override
        public void onSuccess() {
            btnStart.setClickable(true);
            handler.obtainMessage(MSG_TTS_PLAYER, "初始化成功，现在可以使用识别功能了").sendToTarget();
            vuiApi.setASRListener(asrListener);//绑定ASR监听器,没有AI结果；
            vuiApi.setOnAIResponseListener(aiResponseListener); //绑定AI监听器；如果不需要AI结果，可以不设置
            reprotLocation(); //上报wifi信息，用于识别中用到位置信息

        }

        @Override
        public void onFail(RError message) {
            Toast.makeText(
                VADModeActivity.this, 
                "初始化失败！！！message = " + message.getFailDetail(),
                Toast.LENGTH_SHORT).show();
        }
    };
```

- **reprotLocation() 用于相关语义的解析**
```Java
    /**
     * reprotLocation wifi信息，只有上报了位置信息，才能使用跟位置相关的查询服务，比如：今天的天气怎么样
     */
    private void reprotLocation() {
        WifiManager wifiManager = (WifiManager) getApplication().getApplicationContext()
            .getSystemService(Context.WIFI_SERVICE);
        vuiApi.reportLocationInfo(wifiManager.getScanResults());
    }
```

- **RASRListener 识别结果回调**  
```Java
   //设置ASR回调接口。ASR返回的结果都在此接口中回调,不带AI。
    RASRListener asrListener = new RASRListener() {
        @Override
        public void onASRResult(ASRResult result) {
            //如果是需要带AI的结果，此回调结果可以不做处理；
            Log.d(TAG, "ASRResult " + (result.getResultType() == ASRResult.TYPE_OFFLINE ? "offline " : " online ") + " text " + result.getResultText());
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
```
- **OnAIResponseListener  AI语义结果回调**  
```Java
    //设置AI回调接口。AI返回的结果都在此接口中回调，如果不需要AI结果，可以不设置此回调接口。
    OnAIResponseListener aiResponseListener = new OnAIResponseListener() {
        @Override
        public void onResult(String json) {
            Log.e(TAG, "ai json: " + json);
            handler.obtainMessage(MSG_SHOW_RESULT, json).sendToTarget();
        }

        @Override
        public void onFail(RError message) {
            Log.e(TAG, "ai fail: " + message.getFailDetail());
        }
    };
```
- **TTS**
```Java
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
```

- **Handler**
```Java
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
```

**现在就可以运行你的App体验效果了。**  
