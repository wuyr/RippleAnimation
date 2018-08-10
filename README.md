## 仿酷安客户端的主题切换动画效果
### 博客详情: http://blog.csdn.net/u011387817/article/details/79604418

### 使用方式:
#### 添加依赖：
```
implementation 'com.wuyr:rippleanimation:1.0.0'
```

### APIs:
|Method|Description|
|------|-----------|
|create(View onClickView)|创建动画对象(静态方法)|
|setDuration(long duration)|设置动画时长|
|setOnAnimationEndListener(Listener listener)|动画播放完毕监听器|
|start()|开始播放动画|

### 使用示例:

```
    public void onClick(View view) {

        RippleAnimation.create(view).setDuration(duration).start();
        
        //在这里切换你的主题
    }

```

## Demo下载: [app-debug.apk](https://github.com/wuyr/RippleAnimation/raw/master/app-debug.apk)
### 库源码地址： https://github.com/Ifxcyr/RippleAnimation
<br/>

## 效果图: (左边:酷安, 右边:RippleAnimation)
### 注意: RippleAnimation本身不提供主题切换, 只负责动画.

![preview](https://github.com/wuyr/RippleAnimation/raw/master/previews/preview1.gif)![preview](https://github.com/wuyr/RippleAnimation/raw/master/previews/preview2.gif)