## MusicPlayer
#### 音乐播放器和Jsoup爬虫库的demo
* 之前做过一个音乐播放器，实现了后台Service的播放音乐，暂停，滚动条等等一些基本的功能，最终卡死在了歌词滚动和歌词解析，（全部都是自造轮子）----然而这两个应该是有开源库的，并不需要造轮子。。。（其实是懒。）  
* 这里主要是学习了Jsoup爬虫库的使用
* 结合后台Bmob的使用，可以将爬出来的信息上传到云服务器，这样一个人也可以做成一个完成的app，（但是处理数据的操作只能放在客户端）
* 但是Jsoup貌似只能爬取文字，图片。音频和视频一般都有加密，而且如果有iframe标签的存在可能还需要Post操作
* Jsoup的原理主要是Dom树解析，当我去学JS,HTML的时候有了那么一点点基础吧。
