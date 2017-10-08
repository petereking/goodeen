<#macro html
	title="Goodeen"
	hasNavbar=true
	route=""
	baseCssfiles=["bootstrap/css/bootstrap.css","core/core.css"]
	baseJsfiles=["jquery/jquery.js","bootstrap/js/bootstrap.js"]
	extCssfiles=[]
	extJsfiles=[]>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title}</title>
<link href="/favicon.ico" rel="shortcut icon" type="image/x-icon">
<#list baseCssfiles as baseCssfile>
<link rel="stylesheet" href="/${baseCssfile}" />
</#list> 
<#list extCssfiles as extCssfile>
<link rel="stylesheet" href="/${extCssfile}" />
</#list>
<#if currentUser??>
<style id="user-style-${currentUser.screenName}-bg-img" class="js-user-style-bg-img">
	body.user-style-${currentUser.screenName} {
		background-image: url(${currentUser.theme.backgroundImage});
		background-position: ${currentUser.theme.backgroundPosition} 40px;
		background-attachment: fixed;
		background-repeat: no-repeat;
		background-color: ${currentUser.theme.backgroundColor};
	}
</style>
</#if>
</head>
<#if currentUser??>
<body class="user-style-${currentUser.screenName}">
<#else>
<body class="bg1">
</#if>
<div id="doc"${(route=="")?string("", " class=\"" + route + "\"")}>
	<@navbar hasNavbar/> 
	<div id="page-outer">
		<div id="page-container" class="appcontent container wrapper">
			<@alertBanner/>
			<#nested/>
		</div>
	</div>
</div>
<@alertMessages/>
</body>
</html>
<#list baseJsfiles as baseJsfile>
<script type="text/javascript" src="/${baseJsfile}"></script>
</#list> 
<#list extJsfiles as extJsfile>  
<script type="text/javascript" src="/${extJsfile}"></script>
</#list>
<#if currentUser??&&currentUser.state="UNACTIVATED">
<script type="text/javascript">
	$(".resend-activate-email").click(function(){
		$.get("/account/resendActivateEmail",function(data,status){
			if(status == "success") {
				$('#activateWarningDiv').fadeIn(1000).fadeOut(4000);
			}
		});
	})
</script>
</#if>	
</#macro>

<#macro navbar hasNavbar> 
<#if hasNavbar && currentUser??>
<div class="navbar navbar-default navbar-fixed-top">
	<div class="container">
 		<div class="navbar-header">
			<button class="navbar-toggle" type="button" data-toggle="collapse" data-target="#navbar-main">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>		
		</div>
		<div class="navbar-collapse collapse" id="navbar-main">
			<ul class="nav navbar-nav">
				<li class="active"><a href="/"><b class="icon-home text-info"></b>谷钉</a></li>
				<li><a href="/notifications"><b class="icon-bell-alt"></b>通知</a></li>
				<li><a href="/gallerys">相册</a></li>
				<li><a href="/${currentUser.screenName}"><b class="icon-user"></b>我</a></li>
			</ul>				
			<ul class="nav navbar-nav navbar-right">
				<li>
		      <form class="navbar-form navbar-left form-search " action="/search" id="global-nav-search" role="search">
	          <#if searchMode??>
	          <input type="hidden" id="search-mode" name="mode" value="${searchMode}">
	          </#if>
						<div class="input-group">
	          	<#if q??>
		          <input type="text" id="search-query" name="q" class="form-control" placeholder="搜索" value="${q}"/>
		          <#else>
		          <input type="text" id="search-query" name="q" class="form-control" placeholder="搜索" />
		          </#if>
				      <span class="input-group-btn">
				        <button type="submit" class="btn btn-info active" type="button"><b class="icon-search"></b></button>
				      </span>
				    </div>		      
		      </form>				
				</li>
				<li><a href="/messages"><i class="icon-mail text-primary"></i></a></li>
				<li><a href="/trip/add"><i class="icon-road text-primary"></i></a></li>
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="icon-cog text-primary"></i></a>
					<ul class="dropdown-menu">
						<li class="current-user">
							<a href="/settings/profile" class="account-summary account-summary-small">
								<div class="content">
								  <div class="account-group">
								    <img class="avatar size32" src="/images/login.jpg">
								    <b class="fullname">${currentUser.name}</b>
								    <small class="metadata">编辑个人资料</small>
								  </div>
								</div>						
							</a>
						</li>
						<li role="presentation" class="divider"></li>
						<li><a href="/settings/account">设置</a></li>
						<li><a href="/logout">退出</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
</div>
</#if>
</#macro>

<#macro left navMap={} default=navMap?keys[0]!""> 
	<div class="col-xs-3">
		<div id="navigation" class="list-group">
			<#list navMap?keys as key> 
		  <a href="${(key!=default)?string("/${key}","")}" class="list-group-item${(default==key)?string(" active","")}">${navMap[key]}</a>
			</#list> 
		</div>
	</div>
</#macro>

<#macro formgroup label={"for":"", "class":"col-xs-3 control-label" , "value":""} content={"class":"col-xs-7"}>
	<div class="form-group">
		<#if label??>
		<label for='${label["for"]}' class='${label["class"]}'>${label["value"]}</label>
		</#if>
		<div class='${content["class"]}'>
			<#nested/>
		</div>
	</div> 
</#macro>

<#macro checkButtonGroup name="" model={} checkedSeq=[model?keys[0]!""] type="radio"> 
	<div class="btn-group" data-toggle="buttons">
		<#list model?values as value>
		<#assign isChecked=checkedSeq?seq_contains(value)>
	  <label class="btn btn-primary${isChecked?string(" active", "")}">
	    <input type="${type}" id="${name}_${value}" name="${name}" value="${value}"${isChecked?string(" checked", "")}>${value.display}
	  </label>
		</#list> 
	</div>
</#macro>

<#macro alertBanner>
<#if currentUser??&&currentUser.state="UNACTIVATED">
	<div class="alert alert-banner">
		<div class="first-banner-row">
			<span>确认你的邮件地址方可访问 Goodeen 的所有功能。</span>
	    <span>确认邮件已发往 <b>${currentUser.email}</b>。</span>
	  </div>
		<div class="second-banner-row">
     	<button type="button" class="resend-activate-email btn btn-default">重新发送确认邮件</button>
      <a href="/settings/account?change_email=true">更新邮件地址</a> ·
      <a href="//support.twitter.com/articles/82050-i-m-having-trouble-confirming-my-email">了解更多</a>
			<b id="activateWarningDiv" class="alert alert-success" style="display: none;">
				激活邮件已重发，请注意查收!
			</b>		    
    </div>	  
	</div> 			
</#if>
</#macro>

<#macro alertMessages>
<div class="alert-messages${(alertMsg??)?string("", " hide")}">
	<div class="message alert alert-info alert-dismissable">
	  <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
			${alertMsg!"忽略"}
	</div> 			
</div>
</#macro>

<#macro alertBoxJs boxId="alert-box">
	$("[data-hidden]").on("click", function(){
		$("#${boxId}").addClass("hide");
		$("#message").html("");
	});
</#macro>

<#macro alertBox boxId="alert-box">
<div id="${boxId}" class="alert alert-warning${(message??)?string("", " hide")}">
  <button type="button" class="close" data-hidden="alert">&times;</button>
  <div id="message">${message!""}</div>		
</div>
</#macro>

<#macro streamFooter url emptyText icon="icon-user"> 
<div class="stream-footer">
	<div class="timeline-end${(total>0)?string(" has-items", "")}${(total>1)?string(" has-more-items", "")}">
		<div class="stream-end">
		  <div class="stream-end-inner">
				<i class="${icon!"icon-user"}"><input type="hidden" id="total" value="${total}" /></i>
				<p class="empty-text">
						${emptyText}
				</p>
				<p><button type="button" class="btn btn-link back-to-top">回到顶部 ↑</button></p>
		  </div>
		</div>
		<div class="stream-loading">
		  <div class="stream-end-inner">
				<span class="spinner" title="正在载入..."></span>
		  </div>
		</div>
	</div>
	<div id="next">
		<a href="${url}"></a>
	</div>
</div>
</#macro>
