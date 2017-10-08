<@c.html title="Goodeen / 创建账号" extJsfiles=["jquery/validate.js"]>
<div class="signup-canvas well">
	<div class="signup-wrapper">
	<h3><b>现在就加入Goodeen。</b></h3>
	<form action="/user/create" id="signup-form" method="POST" >
		<div class="form-group">
			<label for="name">姓名</label>
			<div>
				<input id="name" class="form-control" type="text" name="name" maxlength="15" />
			</div>
		</div>
		<div class="form-group">
			<label for="email">邮箱</label>
			<div>
			<input id="email" class="form-control" type="text" name="email" value="${email!''}" maxlength="60"/>
			</div>
		</div>
		<div class="form-group">
			<label for="password">密码</label>
			<div>
			<input id="password" class="form-control" type="password" name="password" />
			</div>
		</div>
		<div class="form-group">
			<label for="screenName">名号</label>
			<div>
			<input id="screenName" class="form-control" type="text" name="screenName" maxlength="15"/>
			</div>
		</div>
		<div class="form-group">
			<input id="signup-button" class="btn btn-success" type="submit" value="创建我的账号"/>
		</div>
	</form>
	</div>
</div>
</@c.html>
<script>
	$(function() {
		$("#signup-form").validate({
			rules : {
				name : {
					required : true
				},
				email : {
					required : true,
					email : true,
					remote : "user/email/available",
				},
				password : {
					required : true,
					password : true
				},
				screenName : {
					required : true,
					username : true,
					remote : "user/screenName/available"
				}
			},
			messages : {
					name : {
						required : "姓名必填!"
					},
					email : {
						required : "邮件地址必填!",
						email : "不像是有效的电子邮箱。",
						remote : "该邮箱已经被注册过了。"
					},
					password : {
						required : "密码不能为空!"
					},
					screenName : {
						required: "名号必填!你可以稍后进行修改。",
						remote : "该名号已经被占用了。"
					}
			},
			focusInvalid : true,
			highlight : function(element) {
				$(element).closest('.form-group').removeClass('has-success').addClass('has-error');
			},
			unhighlight : function(element) {
				$(element).closest('.form-group').removeClass('has-error').addClass('has-success');
			},
			// errorElement : 'b',
			errorClass : 'help-block',
			errorPlacement : function(error, element) {
				if (element.parent('.input-group').length) {
					error.insertAfter(element.parent());
				} else {
					error.insertAfter(element);
				}
			},
			submitHandler : function(form) {
		    //按钮动画效果
/* 		    $('#signup-button').css({position: 'relative'})
				 						   	.animate({left:-5}, 100)
			 					        .animate({left:5}, 100)
			 					        .animate({left:-4}, 100)
			 					        .animate({left:4}, 100)
			 					        .animate({left:-3}, 100)
			 					        .animate({left:0}, 100);			 */	
				form.submit();
			 	$('#signup-button').attr("disabled", "disabled");
			 	$('#signup-button').val("账号创建中...");
			}
		});
	});
</script>