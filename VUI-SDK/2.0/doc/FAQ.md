FAQ
=
*我们会不定期更新FAQ列表，如果这没能解决您的问题，也请将您的问题整理好后发给我们，向我们咨询。*

*问：如果我想设置不同发音的speaker，我该怎么办？*  

**答：1. 修改在线发音人(参考附录中在线发音人范围)**  
```Java
    VUIApi.getInstance().setSpeaker("jpn-JPN");
```  
**2. 修改离线发音人(参考附录中离线发音人范围)**  
  - 1. 在assets/vexpressive/config.xml中添加发音人的配置
  ```Java
      <speakers>
        <speaker name="Li-Li" language="cmn-CHN"/>
      </speakers>
  ```  
  - 2. 添加发音人的配置文件到assets/vexpressive中  
  - 3. 修改发音人
  ```Java
        VUIApi.getInstance().setSpeaker("Li-Li");

  ```
*问：在线识别，支持哪些语言？*  
**答：在线识别支持多种语言设置，具体请在接口文档中查看关于在线识别支持哪些语言的介绍。**

- *问：语音点播歌曲的时候，如何不播放提示音？*  
- *问：如何进行一键中译英、一键英译中?*  
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
*问：返回Denny Access的错误提示?*  
*问：如何选择在线TTS还是离线TTS?*  
*问：如何选择TTS的声音?*  
*问：客户端可以调整TTS的语速吗?*  
*问：播放的时候，播放经常被打断，为什么?*  
*问：设备放在早嘈杂环境，经常会主动搭话，应该如何处理?*  
*问：返回can not found config file.(大概是这个意思，具体请帮忙看看Demo的输出日志是什么提示语)?*  
