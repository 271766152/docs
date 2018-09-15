AI 解析
=

*接下来，我们来看一下如何利用AI接口返回得数据。还记得我们得OnAIResponseListener嘛！*  

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
*我们来看下接口返回*





*是不是有点头大，没关系我们看下协议概览。更多更详细协议概览请[点击](https://github.com/271766152/docs/blob/master/Bot/3-ApiReference/rosai-client-development-protocol-intent.md#33-semantic%E5%AE%9A%E4%B9%89)。* 

- **协议概览**  

Name	| Type	| Description	| Required
------------ | ------------ | ------------ | ------------
reqId	| String	| 请求的唯一ID	| Required
status | Status 对象 | Status | Required
query |	String | 纠错后的Text query | Required
semantic | Semantic 对象 | 语义部分 | Optional
results | Result 对象 | Result | Optional
timeout | Timeout 对象 | 超时参数,deprecated | Optional
