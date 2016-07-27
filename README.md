# Android自定义Lint检查

## 支持Lint类型：
1. **UseLogger**: 提示代码中使用android.os.Log和System.out.print的地方，推荐使用自己的Logger
2. **AvoidEnum**: 提示代码中使用Enum的地方，android建议避免使用Enum，请使用常量代替（Enum较耗内存）
3. **UseSparseArray**: 推荐使用SparseArray代替HashMap
4. **UseTextUtils**: 字符串判空，避免使用 "".equals(str)或 str.equals("")，推荐使用TextUtils.isEmpty(str)

## 用法：
1. 编译生成jar:
```js
    ./gradlew build
```    

2. 将生成的jar拷贝到主工程的**lint**目录，并将下列代码拷贝粘贴到主工程的**build.gradle**文件中：
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
3. 执行命令:
```js
    ./gradlew customLint
```

## 参考
[Google](http://tools.android.com/tips/lint-custom-rules)

[美团](http://tech.meituan.com/android_custom_lint.html)

[LinkedIn](https://engineering.linkedin.com/android/writing-custom-lint-checks-gradle)