# 音频播放器

## 由来
某些耳机、音响设备连接电脑后由于未知原因（驱动、干扰）出现以下现象：
* 音频延迟，QQ/微信提示音总是丢失前0.几秒
* 滋滋滋噪音，播放视频时又不会出现，停止播放后过几秒又开始

可以尝试以下方法：

* 更换USB电源为插头，部分设备有效
* 接手机和台式却正常，笔记本异常

因此开发了此程序，此程序会一直循环播放空白音频，虽然治标不治本，但是舒服了很多。

## 说明
* 运行时需增加`-Dfile.encoding=GB18030`参数，否则托盘中文乱码