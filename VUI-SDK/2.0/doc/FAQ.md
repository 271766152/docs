FAQ
=
*我们会不定期更新FAQ列表，如果这没能解决您的问题，也请将您的问题整理好后发给我们，向我们咨询。*

*1. 问：在线识别，支持哪些语言？*  
**答：目前仅支持中英语言，如需要更多请联系roobo工作人员。**
 
*2. 问：如何进行一键中译英、一键英译中?*  
**答：在开始识别之前，需要调用setAiContext(String context)接口进行翻译场景的设置，并且还需要调用setCloudRecognizeLang(String lang)接口设置想要翻译的原语音类型。具体代码如下：**
```Java
        //设置翻译场景
        VUIApi.getInstance().setAIContext("[{"context":"inquiry", "service":"Translator" }]");
        //中译英
        //VUIApi.getInstance().setCloudRecognizeLang("cmn-CHN");
        //英译中
        //VUIApi.getInstance().setCloudRecognizeLang("eng-USA");
        //开始识别
        VUIApi.getInstance().startRecognize();
```
*3. 问：返回Denny Access的错误提示?*  
**答：sn号错误，请联系roobo工作人员。**  

*4. 问：如何选择在线TTS还是离线TTS?*  
**答：请使用setTTSType(RTTSPlayer.TTSType.TYPE_ONLINE)来设置语音合成方式,默认是离线。**

*5. 问：如何选择TTS的声音?*  
**答：目前只支持修改在线发音人，具体请联系roobo工作人员。**  

*6. 问：客户端可以调整TTS的语速吗?*  
**答：暂时不支持调整语速。**  

*7. 问：返回can not found config file.(大概是这个意思，具体请帮忙看看Demo的输出日志是什么提示语)?*  
**答：后台服务器配置问题，请联系roobo工作人员。**
