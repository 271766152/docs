<h1 >附录</h3>

<h3 id="3">1.错误对应码</h3>

| 错误码 | 备注 |
| ------------- |:-------------:|
100 | please check user info
101 | session begin error
102 | token invalid
103 | server result error
104 | can not link to server,please check net work
200 | not have tts content
201 | vad init error
202 | speech timeout
203 | wait server response result time out
400 | BadRequest
401 | Unauthorized
402 | UnGranted
410 | 公私钥校验检查失败;请联系项目负责人check公钥信息
500 | Internal
501 | NotSupported
503 | TooManyRequests
601 | ServiceUnAvailable
602 | ServiceUnknownFormat
603 | ServiceMismatch
604 | GetAsrServiceError
605 | SpeechRecognizeError
606 | SpeechRecognizeEmpty
607 | GetAiServiceError
608 | AiInternalError
609 | AiNotSupported
610 | GetTtsServiceError
611 | TtsServiceInternalError
614 | AsrTimeOut int （一次会话超过最大允许时长）
615 | AsrStreamingTimeOut int （用户传输音频过程中存在超过，5s钟未传输数据）
901 | no network
900 | unkown error,please check the exception info in log
5005 | deny access;如果是预分配SN号的模式;请check是否调用了setUserInfo函数,设置了SN号;否则联系项目负责人check是否开通了权限
5005 | app config not found; 请联系项目负责人开通相关权限
10001 | 初始化language错误 
10002 | 重复初始化
10101 | 发送语音数据时 无法解析IP地址
10102 | 发送语音数据时 连接失败
10103 | 发送语音数据时 系统创建socket文件描述符失败
10104 | 发送语音数据时 执行send操作失败，通常是由于连接异常断开
10105 | 发送语音数据时 执行recv操作失败，通常是由于连接异常断开
10201 | 发送语音数据时 有错误的调用API的行为，如在一开始联网失败的情况下，还以ROOBOAUDIO_SAMPLE_LAST调用了RooboAudioWrite，目前这样的错误可以忽略。
21101 | 进行联网token校验时 无法解析IP地址
21102 | 进行联网token校验时 连接失败
21103 | 进行联网token校验时 系统创建socket文件描述符失败
21104 | 进行联网token校验时 执行send操作失败，通常是由于连接异常断开
21105 | 进行联网token校验时 执行recv操作失败，通常是由于连接异常断开
21201 | 进行联网token校验时 URL解析失败，URL在目前的封装下都是写在so内部，此错误不应该出现
21301 | 进行联网token校验时 服务端返回的http | body大小超出了8K的限制
21401 | 进行联网token校验时 在构造请求时，由于内存不足申请内存失败
21402 | 进行联网token校验时 在构造请求时创建请求线程失败，通常由于系统可用资源不足引起
22101 | 执行TTS文本转播放URL时 无法解析IP地址
22102 | 执行TTS文本转播放URL时 连接失败
22103 | 执行TTS文本转播放URL时 系统创建socket文件描述符失败
22104 | 执行TTS文本转播放URL时 执行send操作失败，通常是由于连接异常断开
22105 | 执行TTS文本转播放URL时 执行recv操作失败，通常是由于连接异常断开
22201 | 执行TTS文本转播放URL时 URL解析失败，URL在目前的封装下都是写在so内部，此错误不应该出现
22301 | 执行TTS文本转播放URL时 服务端返回的http | body大小超出了8K的限制
22401 | 执行TTS文本转播放URL时 在构造请求时，由于内存不足申请内存失败
22402 | 执行TTS文本转播放URL时 在构造请求时创建请求线程失败，通常由于系统可用资源不足引起
23101 | 在上传wifi列表获取位置时 无法解析IP地址
23102 | 在上传wifi列表获取位置时 连接失败
23103 | 在上传wifi列表获取位置时 系统创建socket文件描述符失败
23104 | 在上传wifi列表获取位置时 执行send操作失败，通常是由于连接异常断开
23105 | 在上传wifi列表获取位置时 执行recv操作失败，通常是由于连接异常断开
23201 | 在上传wifi列表获取位置时 URL解析失败，URL在目前的封装下都是写在so内部，此错误不应该出现
23301 | 在上传wifi列表获取位置时 服务端返回的http | body大小超出了8K的限制
23401 | 在上传wifi列表获取位置时 在构造请求时，由于内存不足申请内存失败

<h3 id="8">2.在线AI支持的语言列表</h3>

| 语言码| 备注 |
| ------------- |:-------------:|
|	US English	|	eng-USA	|
|	Mandarin	|	cmn-CHN	|

<h3 id="4">3.AI场景服务对应表</h3>

|	场景	|	Service内容	|
| ------------- |:-------------:|
|	新闻	|	News	|
|	天气	|	Flight	|
|	星座	|	Star	|
|	股票	|	Stock	|
|	导航	|	Navigation	|
|	闹钟	|	Clock	|
|	计算器	|	Calculator	|
|	货币查询	|	Currency	|
|	定时关机	|	ShutDown	|
|	翻译	|	Translator	|
|	单位换算	|	UnitConversion	|
|	儿歌	|	ChildrenSong	|
|	笑话	|	Joke	|
|	音乐	|	Music	|
|	小众音频	|	NicheAudio	|
|	电台	|	Radio	|
|	故事	|	Story	|
|	音效	|	SoundEffects	|
|	菜谱	|	Cook	|
|	节假日查询	|	holiday	|
|	十万个为什么	|	Whys	|
|	成语接龙	|	Idiom	|
|	诗词	|	Poetry	|
|	聊天	|	Chat	|
|	百科	|	Baike	|
|	系统画像	|	Portray	|
|	时间	|	TimeDates	|
|	实时英译汉	|	EnToZhTranslator	|
|	影讯查询	|	LatestMovieSearcher	|
|	机票预订	|	InquiryAirTickets	|
