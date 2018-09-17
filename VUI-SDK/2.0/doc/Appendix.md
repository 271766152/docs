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

<h3 id="4">2.在线识别支持的语言列表</h3>

| 语言| 语言对应的code |
| ------------- |:-------------:|
|	Arabic (Egypt)	|	ara-EGY	|
|	Arabic (Saudi Arabia)	|	ara-SAU	|
|	Arabic (International)	|	ara-XWW	|
|	Bahasa (Indonesia)	|	ind-IDN	|
|	Cantonese (Traditional)	|	yue-CHN	|
|	Catalan	|	cat-ESP	|
|	Croatian 	|	hrv-HRV	|
|	Czech	|	ces-CZE	|
|	Danish	|	dan-DNK	|
|	Dutch	|	nld-NLD	|
|	English (Australia)*	|	eng-AUS	|
|	English (GB)*	|	eng-GBR	|
|	English (US)*	|	eng-USA	|
|	English (India) 	|	eng-IND	|
|	Finnish	|	fin-FIN	|
|	French (Canada)	|	fra-CAN	|
|	French (France)*	|	fra-FRA	|
|	German*	|	deu-DEU	|
|	Greek	|	ell-GRC	|
|	Hebrew	|	heb-ISR	|
|	Hindi	|	hin-IND	|
|	Hungarian	|	hun-HUN	|
|	Italian	|	ita-ITA	|
|	Japanese	|	jpn-JPN	|
|	Korean	|	kor-KOR	|
|	Malay	|	zlm-MYS	|
|	Mandarin (China/Simplified)	|	cmn-CHN	|
|	Mandarin (Taiwan/Traditional)	|	cmn-TWN	|
|	Norwegian	|	nor-NOR	|
|	Polish	|	pol-POL	|
|	Portuguese (Brazil)	|	por-BRA	|
|	Portuguese (Portugal)	|	por-PRT	|
|	Romanian	|	ron-ROU	|
|	Russian	|	rus-RUS	|
|	Slovak	|	slk-SVK	|
|	Spanish (Spain)	|	spa-ESP	|
|	Spanish (LatAm)	|	spa-XLA	|
|	Swedish	|	swe-SWE	|
|	Thai	|	tha-THA	|
|	Turkish	|	tur-TUR	|
|	Ukrainian	|	ukr-UKR	|
|	Vietnamese	|	vie-VNM	|

<h3 id="5">3.离线识别支持的语言列表</h3>

| 语言| 语言对应的code |
| ------------- |:-------------:|
|	US English	|	eng-USA	|
|	Spanish(LatAM)	|	spa-XLA	|
|	Russian	|	rus-RUS	|
|	Spanish(Spain)	|	spa-ESP	|
|	French for France	|	fra-FRA	|
|	German	|	deu-DEU	|
|	Danish	|	dan-DNK	|
|	Italian	|	ita-ITA	|
|	Dutch	|	nld-NLD	|
|	Mandarin	|	cmn-CHN	|
|	Cantonese Traditional	|	yue-CHN	|
|	Korean	|	kor-KOR	|
|	Japanese	|	jpn-JPN	|
|	arabic	|	ara-XWW	|
|	Polish	|	pol-POL	|
|	turkish	|	tur-TUR	|

<h3 id="6">4.在线发音人列表</h3>

| 语言| 语言对应的code |
| ------------- |:-------------:|
|	Arabic (Egypt)	|	ara-EGY	|
|	Arabic (Saudi Arabia)	|	ara-SAU	|
|	Arabic (International)	|	ara-XWW	|
|	Bahasa (Indonesia)	|	ind-IDN	|
|	Cantonese (Simplified)	|	yue-CHN	|
|	Catalan	|	cat-ESP	|
|	Croatian 	|	hrv-HRV	|
|	Czech	|	ces-CZE	|
|	Danish	|	dan-DNK	|
|	Dutch	|	nld-NLD	|
|	English (Australia)*	|	eng-AUS	|
|	English (GB)*	|	eng-GBR	|
|	English (US)*	|	eng-USA	|
|	English (India) 	|	eng-IND	|
|	Finnish	|	fin-FIN	|
|	French (Canada)	|	fra-CAN	|
|	French (France)*	|	fra-FRA	|
|	German*	|	deu-DEU	|
|	Greek	|	ell-GRC	|
|	Hebrew	|	heb-ISR	|
|	Hindi	|	hin-IND	|
|	Hungarian	|	hun-HUN	|
|	Italian	|	ita-ITA	|
|	Japanese	|	jpn-JPN	|
|	Korean	|	kor-KOR	|
|	Malay	|	zlm-MYS	|
|	Mandarin (China/Simplified)	|	cmn-CHN	|
|	Mandarin (Taiwan/Traditional)	|	cmn-TWN	|
|	Norwegian	|	nor-NOR	|
|	Polish	|	pol-POL	|
|	Portuguese (Brazil)	|	por-BRA	|
|	Portuguese (Portugal)	|	por-PRT	|
|	Romanian	|	ron-ROU	|
|	Russian	|	rus-RUS	|
|	Slovak	|	slk-SVK	|
|	Spanish (Spain)	|	spa-ESP	|
|	Spanish (LatAm)	|	spa-XLA	|
|	Swedish	|	swe-SWE	|
|	Thai	|	tha-THA	|
|	Turkish	|	tur-TUR	|
|	Ukrainian	|	ukr-UKR	|


<h3 id="7">5.离线发音人列表</h3>

| 语言 | 语言对应的code | 发音人 |
| ------------- |:-------------|:-------------:|
|	Arabic	|	ara-XWW	|	Laila	|
|	Bahasa (Indonesia)	|	ind-IDN	|	Damayanti	|
|	Basque	|	baq-ESP	|	Miren	|
|	Cantonese	|	yue-CHN	|	Sin-Ji	|
|	Catalan	|	cat-ESP	|	Jordi	|
|	Czech	|	ces-CZE	|	Iveta	|
|	Danish	|	dan-DNK	|	Magnus	|
|	Dutch	|	nld-NLD	|	Claire	|
|	Dutch (Belgium)	|	nld-BEL	|	Ellen	|
|	English (Australia)	|	eng-AUS	|	Karen	|
|	English (GB)	|	eng-GBR	|	Daniel	|
|	English (India)	|	eng-IND	|	Veena	|
|	English (Ireland)	|	eng-IRL	|	Moira	|
|	English (Scotland)	|	eng-SCT	|	Fiona	|
|	English (South Africa)	|	eng-ZAF	|	Tessa	|
|	English (US)	|	eng-USA	|	Allison	|
|	Finnish	|	fin-FIN	|	Satu	|
|	French	|	fra-FRA	|	Aurelie	|
|	French (Canada)	|	fra-CAN	|	Amelie	|
|	Galician	|	glg-ESP	|	Carmela	|
|	German	|	deu-DEU	|	Markus	|
|	Greek	|	ell-GRC	|	Melina	|
|	Hebrew	|	heb-ISR	|	Carmit	|
|	Hindi	|	hin-IND	|	Lekha	|
|	Hungarian	|	hun-HUN	|	Mariska	|
|	Italian	|	ita-ITA	|	Alice	|
|	Japanese	|	jpn-JPN	|	Ichiro	|
|	Korean	|	kor-KOR	|	Sora	|
|	Mandarin (China)	|	cmn-CHN	|	Li-Li	|
|	Mandarin (Taiwan)	|	cmn-TWN	|	Mei-Jia	|
|	Norwegian	|	nor-NOR	|	Nora	|
|	Polish	|	pol-POL	|	Zosia	|
|	Portuguese (Brazil)	|	por-BRA	|	Luciana	|
|	Portuguese (Portugal)	|	por-PRT	|	Joana	|
|	Romanian	|	ron-ROU	|	Ioana	|
|	Russian	|	rus-RUS	|	Katya	|
|	Slovak	|	slk-SVK	|	Laura	|
|	Spanish (Castilian)	|	spa-ESP	|	Jorge	|
|	Spanish (Columbia)	|	spa-COL	|	Soledad	|
|	Swedish	|	swe-SWE	|	Alva	|
|	Thai	|	tha-THA	|	Kanya	|
|	Turkish	|	tur-TUR	|	Cem	|
|	Valencian	|	spa-ESP	|	Empar	|
| Malay | zlm-MYS	 | Amira |



<h3 id="8">6.在线AI支持的语言列表</h3>

| 语言码| 备注 |
| ------------- |:-------------:|
|	US English	|	eng-USA	|
|	Mandarin	|	cmn-CHN	|

