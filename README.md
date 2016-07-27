# Android自定义Lint检查

## 支持Lint类型：

1. **UseLogger**: 查找代码中使用android.os.Log和System.out.print的地方，提示使用自己的Logger
2. **AvoidEnum**: 查找代码中使用Enum的地方，提示使用常量代替（android建议避免使用Enum，因Enum较耗内存）
3. **UseSparseArray**: 推荐使用SparseArray代替HashMap
4. **UseTextUtils**: 字符串判空，避免使用 "".equals(str)或 str.equals("")，提示使用TextUtils.isEmpty(str)

## 用法：

###1.  编译生成**jar**，执行如下命令后在**build/libs**目录中找到**com.custom.lint-1.0.jar**<br/>

```js
    ./gradlew build
```    

###2.  将生成的**jar**拷贝到主工程的**lint**目录(需要自己创建lint目录)，并将下列代码拷贝粘贴到主工程的**build.gradle**文件中<br/>

```js
    task customLint() {
        dependsOn lint
    }
    
    // 拷贝自定义lint.jar到用户的/.android/lint/目录
    task copyCustomLintJarToUserHomeLintDir(type: Copy) {
        from "lint/com.custom.lint-1.0.jar"
        into System.getProperty('user.home') + '/.android/lint/'
    }
    
    customLint.dependsOn copyCustomLintJarToUserHomeLintDir
```
###3.  在**build.gradle**文件中添加如下

```js
    lintOptions {
        enable 'UseLogger', 'AvoidEnum', 'UseSparseArray', 'UseTextUtils'
        check 'UseLogger', 'AvoidEnum', 'UseSparseArray', 'UseTextUtils'
    }
```
###4.  执行命令，生成lint报告

```js
    ./gradlew customLint
```

## 其它

1. 删除用户目录下的lint.jar

```js
    task deleteCustomLintJar() {
        doLast {
            def lintDir = System.getProperty('user.home') + '/.android/lint/';
            def lintFile = 'com.custom.lint-1.0.jar';
            File file = new File(lintDir + lintFile);
            file.delete();
        }
    }
```

## 参考
[Google](http://tools.android.com/tips/lint-custom-rules)

[美团](http://tech.meituan.com/android_custom_lint.html)

[LinkedIn](https://engineering.linkedin.com/android/writing-custom-lint-checks-gradle)

[https://github.com/bignerdranch/linette](https://github.com/bignerdranch/linette)

[https://github.com/googlesamples/android-custom-lint-rules](https://github.com/googlesamples/android-custom-lint-rules)

[https://github.com/skyisle/sonar-android](https://github.com/skyisle/sonar-android)

[https://github.com/hehonghui/android-tech-frontier/blob/master/issue-33/如何自定义Lint规则.md](https://github.com/hehonghui/android-tech-frontier/blob/master/issue-33/如何自定义Lint规则.md)

