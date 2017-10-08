# [谷钉出行社群](http://www.goodeen.top)简介
## 一、何谓『谷钉』？
芸芸众生都是这浩瀚的宇宙的游客。世界太大，每个人都如同**沧海一粟(谷钉之谷也)**一般。但是我相信无论何时，总有那么一些爱好自由、乐于分享，并如**钉子般执着精神的人(谷钉之钉也)**，这类人我称之为**谷钉**。

## 二、愿景
为**谷钉们(执着、爱好自由、乐于分享的人们)**打造最好的**出行类LBSNS**。

## 三、通过谷钉出行社群你可以做什么？
谷钉们登陆谷钉出行社群平台后后就发布自己的行程后。就可以以行程为基本单位衍生如下新内容：
    1.主动邀请@其他谷钉进行组团出行；
    2.行程的团员们可以通过日志、游记、相册丰富行程，记录各自精彩的人生轨迹；
    3.搜索自己感兴趣的谷钉。对感兴趣的谷钉，可以进行互动，建立自己的人脉；
    4.搜索自己感兴趣的行程。对感兴趣的行程，可以申请组团，抑或其他互动。
    
## 四、谷钉出行社群志在给您提供哪些帮助？
秉承**爱拼(拼盘的拼)才会赢**的合作原则，尽可能实现资源共享，实现多赢。具体体现在：
    1.通过行程及其相关的日志、游记、相册、评论互动等等记录您的人生轨迹；
    2.更方便的找到志趣相投的朋友；
    3.可以将自己的多余资源分享、交换、赠送抑或售卖出去；
    4.更方便找到自己想要的资源。
    
## 五、主要技术
### 5.1概况：
	基于springboot微服务的maven项目。
### 5.2项目树形图如下：
	+ goodeen
	  + goodeen-core(基础包，包含常用的utils，model，enums)
	  + goodeen-db-service(数据库服务包)
	  + goodeen-mail-service(邮件发送服务包)
	  + goodeen-solr-service(solr全文搜索服务包，需要依赖对应solr项目)
	  + goodeen-restful(web主项目，后期如果有时间做app)
	  + goodeen-task(定时任务项目，现在包括solr索引定时生存，数据库备份)
### 5.3技术要点：    	
    1.通过nginx实现项目集群、负载均衡、动静分离、文件压缩；
    2.使用shiro和redis实现项目权限控制，和session共享；
    3.使用spring-data-solr实现全文搜索；
    4.通过mybatis访问mysql数据库，并用spring aop统一实现事务控制；
    5.前端主要用的是freemarker、jquery和bootstrap3;
    6.通过git实现项目代码同步，git服务用的是gogs。
### 5.4部署注意事项：    	
    1.数据库文件在项目根目录下，出事了两个用户：test,test1,密码都是：666666，其中test1；
    2.开发环境数据库默认用户密码都是root，生产环境密码需要自己设置。配置都在application*.properties里面设置，修改的地方我都用“##you”打头的字符做了替换，可以全文搜素“##you”来修改初始配置；
    3.邮件发送的邮箱信息也需要自定义，我做了同上面一样的处理；
    4.goodeen-restful项目的搜索功能是基于solr的，如果项目要用搜素功能，不然搜索框点击回到错误页面。必须需要开一个solr服务，solr版本要求在5.0以上，默认配置solr访问路径是“http://localhost:8088/solr”,可根据自己实际情况配置，solr的索引更新是通过goodeen-task定时任务项目来更新的（增量更新时间为1分钟，每天凌晨两点自动全量更新）。

    
## 六、界面预览
![image](https://raw.githubusercontent.com/petereking/shots/master/1.png)
![image](https://raw.githubusercontent.com/petereking/shots/master/2.png)
![image](https://raw.githubusercontent.com/petereking/shots/master/3.png)
![image](https://raw.githubusercontent.com/petereking/shots/master/4.png)
![image](https://raw.githubusercontent.com/petereking/shots/master/5.png)
![image](https://raw.githubusercontent.com/petereking/shots/master/6.png)
![image](https://raw.githubusercontent.com/petereking/shots/master/7.png)