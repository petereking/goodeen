<@c.html title="Goodeen -重置密码" extJsfiles=["jquery/validate.js"]>
<form id="password-form" method="post" class="form-horizontal well white-bg" action="/user/resetPassword">
	<input type="hidden" name="_method" value="PUT" />
	<div class="form-group">
		<label for="password" class="col-xs-3 control-label">	
			新密码
		</label>
		<div class="col-xs-6">
			<input id="key" name="key" type="hidden" value="${key!""}" />
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
			<button id="setting-button" type="submit" class="btn btn-large btn-primary">保存更改</button>
		</div>		
	</div>
</form>
</@c.html>

<script>
$(function() {
	$("#password-form").validate({
			rules : {
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