<#import "macro.ftl" as m>
<@c.html title="Goodeen / 设置" extJsfiles=["jquery/validate.js"]>
<@m.left activeNav="password"/>
<@m.main modelTitle="密码" modelIntroduce="更改你的密码或找回当前密码。">
<form id="password-form" method="post" class="form-horizontal" action="/settings/password/update">
	<@c.alertBox />
	<input type="hidden" name="_method" value="PUT" />
	<div class="form-group">
		<label for="currentPassword" class="col-xs-3 control-label">	
			当前密码
		</label>
		<div class="col-xs-6">
			<input id="current-password" name="currentPassword" type="password" class="form-control" />
			<input id="current-email" type="hidden" value="${currentUser.email!""}" />
			<p class="help-block forget-password">
        <a href="/account/resendPassword" id="forgot-password">忘记密码了?</a>
        <span id="reminder-sent" style="visibility: visible; opacity: 0;">重置密码邮件已发送，请注意查收!</span>
      </p>
		</div>
	</div>
	<div class="form-group">
		<label for="password" class="col-xs-3 control-label">	
			新密码
		</label>
		<div class="col-xs-6">
			<input id="password" name="password" type="password" class="form-control" />
		</div>
	</div>
	
	<div class="form-group">
		<label for="confirm" class="col-xs-3 control-label">	
			确认密码
		</label>
		<div class="col-xs-6">
			<input id="confirm" name="confirm" type="password" class="form-control" />
		</div>
	</div>
	<hr/>
	<p/>
	<div class="form-group">
		<div class="col-xs-offset-3 col-xs-9">	
			<button id="setting-button" type="submit" class="btn btn-large btn-primary" disabled="disabled">保存更改</button>
		</div>		
	</div>
</form>
</@m.main>
</@c.html>
<script>
$(function() {
	var jsonFormInit = $("#password-form input[type='password']").serialize();
	$("#password-form input[type='password']").change(function () {  
	  var jsonFormCurr = $("#password-form input[type='password']").serialize();  
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
	$("#password-form").validate({
			rules : {
				currentPassword : {
					required : true,
					password : true
				},
				password : {
					required : true,
					password : true
				},
				confirm : {
					required : true,
					password : true,
					equalTo : "#password"
				}
			},
			messages : {
				confirm : {
					equalTo : "和新密码设置不一致。"
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