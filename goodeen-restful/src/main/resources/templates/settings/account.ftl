<#import "macro.ftl" as m>
<@c.html title="Goodeen / 设置" extJsfiles=["jquery/validate.js"]>
<@m.left activeNav="account"/>
<@m.main modelTitle="账号" modelIntroduce="修改你的账号。">
<form id="account-form" class="form-horizontal" method="POST" action="/settings/account/update">
	<@c.alertBox />
	<input type="hidden" name="_method" value="PUT" />
	<div class="form-group">
		<label for="password" class="col-xs-3 control-label">	
			当前密码
		</label>
		<div class="col-xs-6">
			<input id="password" name="password" type="password" class="form-control" />
			<input id="current-email" type="hidden" value="${currentUser.email!""}" />
			<p class="help-block forget-password">
        <a href="/account/resendPassword" id="forgot-password">忘记密码了?</a>
        <span id="reminder-sent" style="visibility: visible; opacity: 0;">重置密码邮件已发送，请注意查收!</span>
      </p>
		</div>
	</div>				
	<div class="form-group">
		<label for="screenName" class="col-sm-3 control-label">名号</label>
		<div class="col-sm-6">
			<input id="screenName" name="screenName" type="text" class="form-control" value="${currentUser.screenName}" />
		</div>
	</div>

	<div class="form-group">
		<label for="email" class="col-sm-3 control-label">邮件地址</label>
		<div class="col-sm-6">
			<input id="email" name="email" type="text" class="form-control" value="${currentUser.email}" />
<!-- 			<p class="form-control-static">${currentUser.email}</p> -->
		</div>
	</div>
	
	<div class="form-group">
		<div class="col-sm-offset-4 col-sm-8">	
			<button id="setting-button" type="submit" class="btn btn-primary" disabled="disabled">保存设置</button>
		</div>
	</div>
</form>
</@m.main>
</@c.html>
<script>
$(function() {
	var jsonFormInit = $("#account-form input").serialize();
	$("#account-form input").change(function () {  
	  var jsonFormCurr = $("#account-form input").serialize();  
	  if (jsonFormCurr != jsonFormInit) {
			$("#setting-button").removeAttr("disabled");  
	  } else {  
			$("#setting-button").attr("disabled", "disabled");
	  }  
	});
	<@c.alertBoxJs />
  $("#forgot-password").click (function () {
	  $.ajax({
	    type: "GET",
	    url: this.href,
	    data:{
	    	email : $("#current-email").val()
	    },
	    success: function(data){
	    	$("#reminder-sent").fadeTo(1000,1).fadeTo(3000,1).fadeTo(1000,0);
	    }
	  });
	  return false;
	});
	
	$("#account-form").validate({
			rules : {
				screenName : {
					required : true,
					username : true,
					remote : "/user/screenName/available"
				},
				password : {
					required : true,
					password : true
				},
				email : {
					required : true,
					email : true,
					remote : "/user/email/available"
				}
			},
			messages : {
				screenName : {
					required : "名号必填!你可以稍后进行修改。",
					remote : "该名号已经被使用。"
				},
				email : {
					required : "邮件地址必填。",
					email : "不是有效的电子邮箱。",
					remote : "该邮件地址已被注册。<br/>想要<a href=\"/login\">登录</a>或<a href=\"/account/resend_password\">恢复密码</a>吗?"
				}
			},
			focusInvalid : true,
			highlight : function(element) {
				$(element).closest('.form-group').removeClass('has-success').addClass('has-error');
			},
			unhighlight : function(element) {
				$(element).closest('.form-group').removeClass('has-error').addClass('has-success');
			},
			errorElement : 'b',
			errorClass : 'help-block'
		});
});
</script>	