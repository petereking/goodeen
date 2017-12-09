# pom.xml文件加入maven仓库不存在的包

### 一、**方法一**
> 首先直接在pom文件加入
```xml
<dependency>
    <groupId>${jar.groupId}</groupId>
    <artifactId>${jar.artifactId}</artifactId>
    <version>${jar.version}</version>
</dependency>
```

>比如
```xml
<dependency>
    <groupId>com.jagregory</groupId>
    <artifactId>shiro-freemarker-tags</artifactId>
    <version>0.1-SNAPSHOT</version>
</dependency>
```

然后在${MAVEN_HOME}\com\jagregory\shiro-freemarker-tags\0.1-SNAPSHOT下加入 shiro-freemarker-tags-0.1-SNAPSHOT.jar

### 二、**方法二**
参考[https://www.cnblogs.com/panxuejun/p/6527543.html][1]


  [1]: https://www.cnblogs.com/panxuejun/p/6527543.html