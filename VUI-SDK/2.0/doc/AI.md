AI 解析
=

*接下来，我们来看一下如何利用AI接口返回得数据。还记得我们得OnAIResponseListener嘛！我们对它说“Hey~北京的天气怎么样？”。看看会发生什么。*  

- **OnAIResponseListener  AI语义结果回调**  
```Java
    //设置AI回调接口。AI返回的结果都在此接口中回调，如果不需要AI结果，可以不设置此回调接口。
    OnAIResponseListener aiResponseListener = new OnAIResponseListener() {
        @Override
        public void onResult(final String json) {
            Log.d(TAG, "onAIResponse: " + json);

            String hint = AIResultParser.parserAudioUrlFromAIResultJSON(json);
            if (!TextUtils.isEmpty(hint)) {
                handler.obtainMessage(MSG_SHOW_RESULT, json).sendToTarget();
                Message message = new Message();
                message.obj = hint;
                message.what = MSG_TTS_PLAYER;
                handler.sendMessageDelayed(message, 1000);
            }
        }

        @Override
        public void onFail(final RError rError) {
            handler.obtainMessage(MSG_SHOW_RESULT,
                    "AI error " + rError.getFailCode() + " " + rError.getFailDetail()).sendToTarget();
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

*是不是有点头大，没关系我们看下协议概览。更多更详细协议概览[传送门](https://github.com/271766152/docs/blob/master/Bot/3-ApiReference/rosai-client-development-protocol-intent.md#33-semantic%E5%AE%9A%E4%B9%89)。* 

- **协议概览**  

Name	| Type	| Description	| Required
------------ | ------------ | ------------ | ------------
reqId	| String	| 请求的唯一ID	| Required
status | Status 对象 | Status | Required
query |	String | 纠错后的Text query | Required
semantic | Semantic 对象 | 语义部分 | Optional
results | Result 对象 | Result | Optional
timeout | Timeout 对象 | 超时参数,deprecated | Optional

*拿到了结果，我们去播放。*  

*当然我们也可以让它给我们播放一首音乐，比如“给我播放东风破！”。*

*接口返回*

```Json

```

*那么问题来了，我们如何确定返回得Json中，要去做什么呢？这里就要引入一个概念，意图。关于意图得概念[传送门](https://github.com/271766152/docs/blob/master/Bot/2-RosAiDocument/1-SkillsKit/important-concept/intent.md)。*

*解析 "service": "","action": "" 得到action做相应得处理*




*当然除了这些，我们还丰富了多种场景。[我是传送门](https://github.com/271766152/docs/tree/master/Bot/4-SkillDocument)。*  
