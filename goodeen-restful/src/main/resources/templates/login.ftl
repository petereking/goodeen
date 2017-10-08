<@c.html title="登录Goodeen" route="route-login login-responsive"> 
<@c.alertBox />
<div class="page-canvas panel panel-default">
  <div class="signin-wrapper panel-body">
		<h3><b>登录Goodeen</b></h3>
		<form action="/login" method="POST">
			<div class="form-group">
				<input class="form-control" type="text" id="name" name="name" value="${email!""}" placeholder="名号或邮件地址" />
			</div>
			<div class="form-group">
				<input class="form-control" type="password" id="password" name="password" placeholder="密码" />
				<#if msgInfo??>
				<p class="help-block" style="color:red;">${msgInfo}</p>
				</#if>				
			</div>
			<div class="form-group">
				<input type="submit" value="登录" class="btn btn-primary" />
				<span class="remember-me">
					<input type="checkbox" name="rememberMe" value="true" /> 记住我 
					<span>·</span>						
					<a id="forgot-password" href="/account/resendPassword">忘记密码?</a>
					<span id="reminder-sent" style="visibility: visible; opacity: 0;">提醒已发送!</span>
				</span>
			</div>
		</form>
  </div>
  <div class="panel-footer mobile">
  	<p class="signup-helper">
		新来 Goodeen? <a href="/signup">现在就注册吧 »</a>
  	</p>
	</div>
</div>
</@c.html>
<script>
$(function() {
	<@c.alertBoxJs />
  $("#forgot-password").click (function () {
	  $.ajax({
	    type: "GET",
	    url: this.href,
	    data:{
	    	email : $("#name").val()
	    },
	    success: function(res){
	    	$("#reminder-sent").fadeTo(1000,1).fadeTo(3000,1).fadeTo(1000,0);
	    }
	  });
	  return false;
	});
});
</script>