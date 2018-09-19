
###  目录
[一.SDK初始化](#1)  
    [1.1　初始化](#1.1)  
    [1.2　InitParam 构造方法](#1.2)  
    [1.3　InitParam 参数列表](#1.3)  
    [二.语音识别](#2)  
    [2.1　语音识别监听器](#2.1)   
    [2.2　开始语音识别](#2.2)  
    [2.3　停止语音识别](#2.3)  
    [2.4　取消语音识别](#2.4)    
    [三.语义解析](#3)  
    [3.1　AI语义解析监听器](#3.1)  
    [3.2　AI语义解析](#3.2)  
    [3.3　AI语义解析上下文](#3.3)  
    [3.4　设置AI语义解析的语言](#3.4)   
    [3.5　上报设备地理位置信息](#3.5)  
    [四.语音合成](#4)  
    [4.1　TTS监听器](#4.1)  
    [4.2　开始使用TTS](#4.2)  
    [4.3　停止使用TTS](#4.3)   
    [4.4　设置Speaker](#4.4)   
    [4.5　获取TTS音频数据](#4.5)   
    [五.其他](#5)  
    [5.1　资源释放](#5.1)  
    [5.2　设置Log等级](#5.2)   



<h2 id="1">一.SDK初始化</h2>
<h3 id="1.1"> 1.初始化</h3>

```Java
VUIApi.getInstance().init(context, initParam,initListener);
```
 
<h3 id="1.2"> 2. InitParam 构造方法 </h3>

```Java
VUIApi.InitParam.InitParamBuilder builder = new VUIApi.InitParam.InitParamBuilder();

builder.setUserInfo(userInfo)//设置用户信息，必须设置（userInfo 参数见快速开始）
        .setAudioGenerator(new CustomAndroidAudioGenerator())//设置音频源
        .setTTSType(RTTSPlayer.TTSType.TYPE_ONLINE)//设置语音合成方式,默认是离线,设置为在线
        .setVUIType(VUIApi.VUIType.AUTO);//设置交互方式；手动且单次识别，需要手动开始 和 手动停止
```               
*注意：以上为构造InitParam必填参数*

<h3 id="1.3"> 3. InitParam 参数列表 </h3>

参数 | 说明 | 必填  
------------ | ------------ | ------------ 
setUserInfo(UserInfo userInfo) | deviceID信息必须设置，根据项目的SN分配方式 ：【预分配并生产线烧录方式】就设置分配的SN号；【通过唯一标识在线注册方式】就设置设备的唯一标识 | 是
setVUIType(VUIType VUIType) | 设置VUI交互方式(唤醒后自动识别模式：VUIType.AUTO ， 手动且单次识别模式：VUIType.MANUAL) | 是
setTTSType(TTSType ttsType) | 设置TTS在线(TTSType.TYPE_ONLINE)/设置TTS在线(TTSType.TYPE_OFFLINE) |是
setTTSSpeaker(String speaker) | 如果TTS采用离线方式，这里设置是发音人。如果是采用在线方式，这是设置的是TTS语言 |否(默认"Li-Li")
setAudioGenerator(BaseAudioGenerator audioSource) | 设置语音识别的音源,设置为RooboAECRecorder,是带AEC功能 |是
setLanguage(String language) | 设置ASR/TTS/AI的语言(例如："cmn-CHN",代表中文) | 否
setTokenType(TokenType tokenType) | 设置token的类型,默认是内部维护token,如果是外部设置token，同时需要设置setDeviceInfo()的信息 | 否 
setDeviceInfo(DeviceInfo deviceInfo) | 设置SN号和token,同时需要设置setTokenType(VUIApi.TokenType.TYPE_TOKEN_EXTERNAL_SETTING) | 否 
 
<h2 id="2">二.语音识别</h2>   
    
<h3 id="2.1"> 1.语音识别监听器</h3>

```Java
//语音识别监听器
RASRListener asrListener = new RASRListener() {
        @Override
        public void onASRResult(ASRResult result) {
            Log.d(TAG,result.getResultText())//通过该方法获取识别出的文本
        }

        @Override
        public void onFail(RError message) {
        }

        @Override
        public void onWakeUp(String json) {
        }

        @Override
        public void onEvent(EventType event) {
        }
    };
    
//设置语音识别监听器
VUIApi.getInstance().setASRListener(asrListener)    
```
    
<h3 id="2.2">2.开始语音识别</h3>

```Java
VUIApi.getInstance().startRecognize();
```

<h3 id="2.3"> 3.停止语音识别</h3>

```Java
VUIApi.getInstance().stopRecognize();
//调用该方法后,可在RASRListener中得到处理结果
```

<h3 id="2.4"> 4.取消语音识别</h3>

```Java
VUIApi.getInstance().cancelRecognize();
//调用该方法后,无结果返回
```
  
<h2 id="3"> 三.语义解析</h2>

<h3 id="3.1"> 1.AI语义解析监听器</h3>

```Java
 OnAIResponseListener aiResponseListener = new OnAIResponseListener() {
        @Override
        public void onResult(String json) {
           //json返回了AI语义解析的结果
        }

        @Override
        public void onFail(RError rError) {
        }
    };
    
 VUIApi.getInstance().setOnAIResponseListener(aiResponseListener)
 //设置AI语义解析监听器
```

<h3 id="3.2"> 2.AI语义解析</h3>

```Java
VUIApi.getInstance().aiQuery(String text);//text为需要进行AI语义解析的文本
```
  
<h3 id="3.3"> 3.AI语义解析上下文</h3>

```Java
VUIApi.getInstance().setAIContext(String context);
//RooboAI后台的语义理解是支持上下文的，开发者可以设置上下文，就支持多轮对话。
//在AIResponseListener回调的中可以获取到outputContext。
```
  
<h3 id="3.4"> 4.设置AI语义解析的语言</h3>

```Java
VUIApi.getInstance().setCloudRecognizeLang(String lang);
//设置在线ASR语音识别的语言（见语言对应列表附录）
```
  
<h3 id="3.5"> 5.上报设备地理位置信息</h3>

设备地理位置信息用于基于位置的AI语义解析

```Java
 /**
     * List<ScanResult> scanResultList = getApplication()
     * 					.getApplicationContext()
     * 					.getSystemService(Context.WIFI_SERVICE).getScanResults();
     */
    
//使用系统扫描Wi-Fi结果上报地理位置信息         
VUIApi.getInstance().reportLocationInfo(List<ScanResult> scanResultList);

/**
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @param country
     * @param province
     * @param city
     * @param detail    详细地理位置描述
     *                  例如：
     *                  "latitude": 22.5375738,
     *                  "longitude": 113.9568349,
     *                  "address": {
     *                  "country": "中国",
     *                  "province": "广东省",
     *                  "city": "深圳市",
     *                  "detail": "广东省 深圳市 南山区 科技南十二路 靠近交通银行(深圳高新园支行)"
     * @see ScanResult
     */
       
//使用AI语义的结果上报地理位置信息      
VUIApi.getInstance().reportLocationInfo(String latitude, String longitude, String country, String province, String city, String detail);
```
  
<h2 id="4"> 四.语音合成</h2>
    
<h3 id="4.1">1.TTS监听器</h3>

```Java
RTTSListener mRTTSListener = new RTTSListener() {
            @Override
            public void onSpeakBegin() {
            //TTS开始
            }

            @Override
            public void onCompleted() {
            //TTS结束
            }

            @Override
            public void onError(int code) {
            }
        }
```
    
<h3 id="4.2"> 2.开始使用TTS</h3>

```Java
VUIApi.getInstance().speak(String text);//text为需要进行语音播报的文本

VUIApi.getInstance().speak(String text, RTTSListener listener);//RTTSListener为TTS监听器

```

<h3 id="4.3"> 3.停止使用TTS</h3>

```Java
VUIApi.getInstance().stopSpeak();
```
  
<h3 id="4.4"> 4.设置Speaker</h3>

```Java
VUIApi.getInstance().setSpeaker(String speaker);

注意：在线模式设置的是Language,离线模式设置的是发音人的名字．(初始化之后可以动态设置)
```
  
<h3 id="4.5"> 5.获取TTS音频数据 </h3> 

```Java
VUIApi.getInstance().getTTSAudioData(text, new RTTSAudioDataListener() {
    @Override
    public void onSpeakAudio(VUIApi.VUITTSAudioType type, byte[] data, boolean isFinish) {
        if (type == VUIApi.VUITTSAudioType.TYPE_AUDIO_DATA) {
            //离线TTS返回data是音频数据
            //当 isFinish 为 true 时，表示TTS结束,最后一块数据 data 是 null
        } else {
            //data是在线的TTS返回的url地址;
        }
    }
});
```

<h2 id="5"> 五.其他</h2>
   
<h3 id="5.1"> 1.资源释放</h3> 

```Java
VUIApi.getInstance().release();
```
    
<h3 id="5.2">2.设置Log等级</h3> 

```Java
VUIApi.getInstance().setLogLevel(int logLevel);
//logLevel 日志级别0-5,默认是0, 最高级别是5
```








