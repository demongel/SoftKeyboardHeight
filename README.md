
**关键内容参考了：https://github.com/siebeprojects/samples-keyboardheight
以及
https://stackoverflow.com/questions/16788959/is-there-any-way-in-android-to-get-the-height-of-virtual-keyboard-of-device  
下，Siebe Brouwer 的回答。**    
** 保留了相关GNU声明**


#### 如果不需要全屏，使用adjustResize即可

1. 写一个布局，logo是marginTop，底部是marginBottom，中间的空间高度大于底部控件的总高度。
2. 主题中设置非全屏也非状态栏透明。
```
<activity
            android:name=".login.NoFullScreenLoginActivity"
            android:theme="@style/AppTheme.NoActionBar"
            android:windowSoftInputMode="stateHidden|adjustResize" />
```
3. 其中因为AppTheme系列没有NoAcionBar主题，需要自己添加
```
    <style name="AppTheme.NoActionBar" parent="AppTheme">
        <!-- Customize your theme here. -->
        <item name="windowActionBar">false</item>
        <item name="windowNoTitle">true</item>
    </style>
```
4. 可以通过设置背景颜色和colorPrimaryDark相同，可以有类似沉浸式的效果（伪）

#### 全屏状态下，使用AndroidBug5497Workaround类
1. 全网搜索一下，大部分都可以指向这个类。因为当主题为FullScreen（或者状态栏透明）时，windowSoftInputMode=adjustResize 会失效。会出现整个布局上移或者有些是完全不上移，此时可以使用使用AndroidBug5497Workaround这个类来实现Resize的效果。
2. 缺点：
> 1. 部分手机不适配（手上刚好一台红米note2，测试不通过，中间会有空白，而且不是statusbar的高度问题）
> 2. 可能出现误算，当界面上有View隐藏或消失时可能触发
> 3. 设置背景不便（键盘弹出时会被压缩）

#### 透明状态栏的做法（红米note2 有效）
1. 只是针对上一种做一点点调整，Theme中不适用FullScreen，而是状态栏隐藏。状态栏隐藏时，resize依然无效，但此种状态下针对红米note2使用AndroidBug5497Workaround处理，可以实现上推效果且没有空白。
```
    <style name="AppTheme.NoActionBar.Immersed" parent="AppTheme">
        <item name="windowActionBar">false</item>
        <item name="windowNoTitle">true</item>
        <item name="android:windowNoTitle">true</item>
        <item name="android:windowTranslucentStatus">false</item>
        <item name="android:windowTranslucentNavigation">true</item>
    </style>
```
2. 缺点：除了使用用AndroidBug5497Workaround这个类的缺点外，windowTranslucentStatus需要v19以上版本才支持。

#### 终极的解决方案(使用PopWindow来获取键盘高度)
1. 事实上，每个人都可以想到，如果有办法获取键盘高度，然后根据键盘高度来调整底部的空间的margin值就可以实现动态移动控件的效果。将屏幕固定也有好处，这样设置背景不会因为键盘弹出而变形。但目前的矛盾是**要获取控件高度，需要设置adjustResize，但全屏下有冲突，使用AndroidBug5497Workaround有适配问题且存在误报，如果不设置adjustResize，View图层不会变化，无法获取键盘高度和状态**
那么，问题的关键是：能不能在全屏且windowSoftInputMode=adjustNothing（不随键盘移动）的状态下，获取键盘高度？  
答案当然是有！  
不过很奇怪的是，好像国内的博客完全没有文章提到，这个答案在StackOverflow 也没有获得很高的赞（感觉是国外没有那么复杂的适配问题，可能基本上都用AndroidBug5497Workaround解决了），但经过测试，确认可以在adjustNothing状态下获取键盘高度（这个时候你想怎么样就怎么样了）

2. 参见：https://github.com/siebeprojects/samples-keyboardheight
以及
https://stackoverflow.com/questions/16788959/is-there-any-way-in-android-to-get-the-height-of-virtual-keyboard-of-device  
下，Siebe Brouwer 的回答。

3. 这个方法其实用的也是ViewTree的观察模式，只是套上了一层PopWindow，而且这个PopWindow的宽度是0，高度是match_parent，这样你就感觉不到这一层的存在。通过PopWindow来获取键盘高度，回调给Activity，然后手动计算空间即可。