FAQ
=
*FAQ列表会不定期更新，如果FAQ没能解决您的问题，请您将问题整理并发给我们，我们会为您提供相应解答。*

*1. 问：在线识别，支持哪些语言？*  
**答：目前仅支持中文、英文，如需使用其他语种请联系ROOBO工作人员。**
 
*2. 问：如何进行一键中译英、一键英译中?*  
**答：在开始识别之前，需要调用setAiContext(String context)接口进行翻译场景的设置，并且还需要调用setCloudRecognizeLang(String lang)接口设置想要翻译的原语音类型。具体代码如下：**
```Java
        //一键中译英的代码如下：
        //设置翻译场景
        VUIApi.getInstance().setAIContext("[{"context":"inquiry", "service":"Translator" }]");
        //中译英
        //VUIApi.getInstance().setCloudRecognizeLang("cmn-CHN");
        //开始识别
        VUIApi.getInstance().startRecognize();
        
        //一键英译中的代码如下：
        //设置翻译场景
        VUIApi.getInstance().setAIContext("[{"context":"inquiry", "service":"Translator" }]");
        //英译中
        //VUIApi.getInstance().setCloudRecognizeLang("eng-USA");
        //开始识别
        VUIApi.getInstance().startRecognize();
```
*3. 问：返回Denny Access的错误提示?*  
**答：该产品在后台配置的是预分配SN模式，设备在初始化时传递的SN号在后台没有找到，请确认传递的SN号是否正确。**  

*4. 问：如何选择在线TTS还是离线TTS?*  
**答：请使用setTTSType来设置语音合成方式,RTTSPlayer.TTSType.TYPE_ONLINE为在线，RTTSPlayer.TTSType.TYPE_OFFLINE为离线，默认是离线。**

*5. 问：如何选择TTS的声音?*  
**答：目前只支持修改在线发音人，具体请联系roobo工作人员。**  

*6. 问：客户端可以调整在线TTS的语速吗?*  
**答：暂时不支持调整语速。**  

*7. 问：返回can not found config file?*  
**答：后台服务器配置问题，请联系roobo工作人员。**
