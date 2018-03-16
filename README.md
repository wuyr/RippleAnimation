# RippleAnimation
## 仿酷安客户端的主题切换动画效果: (左边:酷安, 右边:RippleAnimation)

![preview](https://github.com/wuyr/RippleAnimation/raw/master/preview1.gif)![preview](https://github.com/wuyr/RippleAnimation/raw/master/preview2.gif)

### 注意: RippleAnimation本身不提供主题切换, 只负责动画.

### 使用非常简单: 

```
    public void onClick(View view) {

        RippleAnimation.create(view).setDuration(duration).start();
        
        //在下面切换你的主题
        //do something...
    }

```
