AI Protocol
=

*接下来，我们来看一下如何利用AI接口返回得数据。还记得我们得OnAIResponseListener嘛！  
我们对它说“北京的天气怎么样？”。看看会发生什么。*  

- **OnAIResponseListener  AI语义结果回调**  
```Java
    //设置AI回调接口。AI返回的结果都在此接口中回调，如果不需要AI结果，可以不设置此回调接口。
    OnAIResponseListener aiResponseListener = new OnAIResponseListener() {
        @Override
        public void onResult(final String json) {
            Log.e(TAG, "ai json: " + json);
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
```
*我们来看下接口返回*

```Json
{
	"apiVersion": "1.0",
	"status": {
		"code": 0,
		"errorType": "success"
	},
	"asr": {
		"text": "北京的天气怎么样？"
	},
	"tts": {
		"content": "http://ros.roobo.net/voice/static/usertts/2018-09-15/662/reply.18446744073170258879.e0dad16f-3415-4db2-875b-cf695b1123c1.mp3",
		"music": "",
		"text": "北京今天多云，气温15度到26度，东北风3级",
		"format": "mp3",
		"cookie": ""
	},
	"ai": {
		"status": {
			"code": 0,
			"errorType": "success"
		},
		"query": "北京的天气怎么样",
		"semantic": {
			"service": "Weather",
			"action": "WeatherForADay",
			"params": {
				"city": {
					"orgin": "北京",
					"norm": "北京",
					"code": 0
				},
				"focus": {
					"orgin": "天气怎么样",
					"norm": "天气",
					"code": 0
				}
			},
			"outputContext": {
				"context": "oneDay",
				"service": "Weather"
			}
		},
		"result": {
			"hint": "北京今天多云，气温15度到26度，东北风3级",
			"data": [{
				"alter": "",
				"city": "北京",
				"date": "2018-09-15",
				"focus": "weather",
				"humidity": "31",
				"index": 1,
				"maxTemp": "26",
				"minTemp": "15",
				"pm25": "17",
				"quality": "优",
				"temperature": "25",
				"weather": "多云",
				"windDay": "东北",
				"windDayLevel": "3",
				"windDir": "东北",
				"windLevel": "3",
				"windNight": "东北",
				"windNightLevel": "3"
			}]
		}
	}
}
```

- **协议概览**  

Name | Description	
------------ | ------------ 
apiVersion | sdk version 
status | 本次请求是否成功
asr | 语音识别结果
tts | tts相关内容 
ai | AI请求相关 

- **AI部分概览**  

Name | Description	
------------ | ------------ 
status | 本次AI请求是否成功  
query | 纠正后的Text  
semantic | 语义部分  
result | 返回的结果部分 

- **语义部分概览**  

Name | Description	
------------ | ------------ 
service |  场景名称
action |   动作名称
outputContext | 当前语义上下文 


*当然我们也可以让它给我们播放一首音乐，比如“我要听东风破！”。*

*接口返回*

```Json
{
	"apiVersion": "1.0",
	"status": {
		"code": 0,
		"errorType": "success"
	},
	"asr": {
		"text": "我要听东风破。"
	},
	"tts": {
		"content": "http://ros.roobo.net/voice/static/usertts/2018-09-15/962/reply.15931625739899993222.2014ac12-6339-4490-9fd1-078ca58f92c2.mp3",
		"music": "",
		"text": "为您播放 周杰伦 东风破",
		"format": "mp3",
		"cookie": ""
	},
	"ai": {
		"status": {
			"code": 0,
			"errorType": "success"
		},
		"query": "我要听东风破",
		"semantic": {
			"service": "Media",
			"action": "Play",
			"outputContext": {
				"context": "media,ack",
				"service": "RMUSIC_1206"
			}
		},
		"result": {
			"hint": "为您播放 周杰伦 东风破",
			"data": {
				"album": "叶惠美",
				"artist": "周杰伦",
				"audio": "http://ms-dwn.roo.bo/resource/music_bk/775/30097775.mp3",
				"extra": null,
				"hqAudio": "",
				"hqImage": "http://y.gtimg.cn/music/photo_new/T002R300x300M000000MkMni19ClKG.jpg",
				"image": "http://y.gtimg.cn/music/photo_new/T002R300x300M000000MkMni19ClKG.jpg",
				"length": 315,
				"name": "东风破",
				"playMode": "",
				"resId": "music:4041702",
				"sid": "3007738066-1537012676417",
				"size": 5050583,
				"start": 0,
				"type": "MUSIC"
			}
		}
	}
}
```

*如果不想听音乐，对他说“关闭”。*
*接口返回*
```Json
{
	"apiVersion": "1.0",
	"status": {
		"code": 0,
		"errorType": "success"
	},
	"asr": {
		"text": "关闭。"
	},
	"tts": {
		"content": "",
		"music": "",
		"format": "",
		"cookie": ""
	},
	"ai": {
		"status": {
			"code": 0,
			"errorType": "success"
		},
		"query": "关闭",
		"semantic": {
			"service": "Media",
			"action": "Exit",
			"inputContext": {
				"context": "media,ack",
				"service": "RMUSIC_1206"
			},
			"outputContext": {
				"context": "",
				"service": "RMUSIC_1206"
			}
		}
	}
}
```

*解析 "service": "","action": "" 得到action做相应得处理*
```Java
  public static boolean isStartPlayer(String resultJson) {
        if (!TextUtils.isEmpty(resultJson)) {
            try {
                JSONObject jsonObject = new JSONObject(resultJson);
                JSONObject aiJsonObject = jsonObject.optJSONObject("ai");
                if (aiJsonObject != null) {
                    JSONObject semanticJsonObject = aiJsonObject.optJSONObject("semantic");
                    if (semanticJsonObject != null) {
                        if ("Media".equals(semanticJsonObject.optString("service")) &&
                                "Play".equals(semanticJsonObject.optString("action"))) {
                            return true;
                        }
                    }
                }
            } catch (JSONException e) {
                Log.e(TAG, "parser error!");
            }
        }
        return false;
    }
```
```Java
    public static boolean isExitPlayer(String resultJson) {
        if (!TextUtils.isEmpty(resultJson)) {
            try {
                JSONObject jsonObject = new JSONObject(resultJson);
                JSONObject aiJsonObject = jsonObject.optJSONObject("ai");
                if (aiJsonObject != null) {
                    JSONObject semanticJsonObject = aiJsonObject.optJSONObject("semantic");
                    if (semanticJsonObject != null) {
                        if ("Media".equals(semanticJsonObject.optString("service")) &&
                                "Exit".equals(semanticJsonObject.optString("action"))) {
                            return true;
                        }
                    }
                }
            } catch (JSONException e) {
                Log.e(TAG, "parser error!");
            }
        }
        return false;
    }
```


*MediaPlayer 去播放音乐*  
```Java
 public static void playByUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void stop(){
        mediaPlayer.stop();
    }
```

