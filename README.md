# Weixin Java Tools 微信公众号/企业号开发Java SDK
## [![Open Source Love](https://badges.frapsoft.com/os/v1/open-source.svg?v=103)](https://github.com/ellerbrock/open-source-badge/)     ![Maven Central](https://img.shields.io/maven-central/v/com.github.binarywang/weixin-java-parent.svg)  [![Build Status](https://travis-ci.org/wechat-group/weixin-java-tools.svg?branch=develop)](https://travis-ci.org/wechat-group/weixin-java-tools)


### 注意：
1. ***本项目Fork自chanjarster/weixin-java-tools，但由于原项目已停止维护，故单独维护和发布，且发布到maven上的groupId也会不同，详细信息见下文。***
1. ***自2.0.0版本以来，公众号的接口调整比较大，主要是为了解决主接口类过于庞大不方便管理的问题，将接口实现代码按模块进行拆分。***
1. 本项目最低要求的JDK版本是7，如果需要较低版本，比如支持JDK6的版本，请参考 https://github.com/binarywang/weixin-java-tools-for-jdk6 ，其他版本则需要自己实现；
1. 最新更新：2016-09-30 发布2.2.0正式版！

===========

## 开发交流方式：
1. ***入群须知：为保证入群成员质量，请申请入群前，先Star本项目，然后在申请入群时，输入您的Github帐号ID，以便管理员核对，ID即你的github主页地址https://github.com/XXXX 中最后的部分XXXX的内容，或者在github网页右上角点击头像后查看Signed in as后方内容。***
1. QQ群：343954419 [![Join QQ Group](http://pub.idqqimg.com/wpa/images/group.png)](http://shang.qq.com/wpa/qunwpa?idkey=078f7a153d243853e24cf2b542e7a6ccbf2a592bc138080f84d11297f736ec46)
1. 微信群： 因二维码有时间限制，如有想加入微信群的，请入QQ群后咨询获取最新入群二维码；
1. 有功能需求或由于微信官方接口调整导致的代码问题，可以直接提出issue，便于讨论追踪问题；
1. 详细开发文档请看 [Wiki](https://github.com/wechat-group/weixin-java-tools/wiki)。

===========

## 版本说明
1. 本项目定为每月发布一次正式版，版本号格式为X.X.0（如2.1.0，2.2.0等），月初或月底发布新版本，遇到重大问题需修复会及时提交新版本，欢迎大家随时提交Pull Request；
1. BUG修复和新特性一般会先发布成小版本作为临时版本（如2.0.1，2.0.2等，即尾号不为0，以区别于正式版）；
1. 目前最新版本号为 ![Maven Central](https://img.shields.io/maven-central/v/com.github.binarywang/weixin-java-parent.svg) ，也可以通过访问链接 [【公众号】](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.github.binarywang%22%20AND%20a%3A%22weixin-java-mp%22) 、[【企业号】](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.github.binarywang%22%20AND%20a%3A%22weixin-java-cp%22)
分别查看所有最新的版本。 

## Maven & Gradle

* 公众号（订阅号、服务号）：

maven：
```xml
<dependency>
  <groupId>com.github.binarywang</groupId>
  <artifactId>weixin-java-mp</artifactId>
  <version>2.2.0</version>
</dependency>
```
gradle：
```groovy
compile 'com.github.binarywang:weixin-java-mp:2.2.0'
```

* 企业号：

maven：
```xml
<dependency>
  <groupId>com.github.binarywang</groupId>
  <artifactId>weixin-java-cp</artifactId>
  <version>2.2.0</version>
</dependency>
```
gradle：
```groovy
compile 'com.github.binarywang:weixin-java-cp:2.2.0'
```

===========

#### 本项目主要存放在github上，地址为 :
* https://github.com/wechat-group/weixin-java-tools
* ===========但同时会在其他几个网站同步更新，地址分别是:
* https://bitbucket.org/binarywang/weixin-java-tools
* http://git.oschina.net/binary/weixin-java-tools
* https://git.coding.net/binarywang/weixin-java-tools.git

===========
## 目前可参考的Demo项目有两个:
1. https://github.com/wechat-group/weixin-mp-demo
1. https://github.com/wechat-group/weixin-java-tools-springmvc

===========
## 关于代码贡献
1. 非常欢迎和感谢对本项目发起Pull Request的同学，本项目代码风格为使用2个空格代表一个Tab，因此在提交代码时请注意一下，否则很容易在IDE格式化代码后与原代码产生大量diff，这样会给其他人阅读代码带来极大的困扰。
1. 为了便于设置，本项目引入editorconfig插件，请使用eclipse的同学在贡献代码前安装相关插件，IntelliJ IDEA则自带支持，无需额外安装插件。
1. 本项目可以采用两种方式接受代码贡献：

  1. 第一种就是基于[Git Flow](https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow)开发流程，因此在发起Pull Request的时候请选择develop分支。
  1. 另外一种贡献代码的方式就是加入SDK Developers开发组，前提是对自己的代码足够自信就可以申请加入，加入之后可以随时直接提交代码，但要注意对所做的修改或新增的代码进行单元测试，保证提交代码没有明显问题，具体加入方式，请咨询管理员。
