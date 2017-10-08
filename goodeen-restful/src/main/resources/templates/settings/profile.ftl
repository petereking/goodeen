<#import "macro.ftl" as m>
<@c.html title="Goodeen / 设置" extJsfiles=["jquery/limitTextarea.js","jquery/validate.js"]>
<@m.left activeNav="profile"/>
<@m.main modelTitle="个人资料" modelIntroduce="该信息将出现在你的公开个人资料、搜索结果等地方。">
<form id="profile-form" method="post" enctype="multipart/form-data" class="form-horizontal" action="/settings/profile/update">
	<@c.alertBox />
	<input type="hidden" name="_method" value="PUT" />
	<div class="form-group">
		<label for="name" class="col-xs-3 control-label">姓名</label>
		<div class="col-xs-7">
			<input id="name" name="name" maxlength="20" type="text" class="form-control" value="${currentUser.name}" />
			<p class="help-block">输入你的真实姓名,这样可以让你认识的人认出你。</p>			
		</div>
	</div>
	<div class="form-group">
		<label for="location" class="col-xs-3 control-label">位置</label>
		<div class="col-xs-7">
			<input name="location" type="text" class="form-control" value="${currentUser.location!""}" />
			<p class="help-block">你在什么地方?</p>			
		</div>
	</div>
	<div class="form-group">
		<label for="url" maxlength="60" class="col-xs-3 control-label">网页</label>
		<div class="col-xs-7">
			<input id="url" name="url" type="text" class="form-control" value="${currentUser.url!""}" />
			<p class="help-block">你有一个主页或是博客? 可以把网址填在这里吧。</p>			
		</div>
	</div>
	<div class="form-group">
		<label for="summary" class="col-xs-3 control-label">简介</label>
		<div class="col-xs-7">
			<textarea id="summary" name="summary" rows="5" class="form-control" style="resize: none;">${currentUser.summary!""}</textarea>
			<p class="bio-label help-block">简单描述一下你自己吧！ <span class="user-summary-count"></span></p>	
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
</@m.main>
</@c.html>
<script>
$(function(){
	<@c.alertBoxJs />
  
  $("#summary").limitTextarea({
    countClass:"user-summary-count"
  });
  
	$("#profile-form").validate({
		rules : {
			"name" : {
				required : true
			},
			"url" : {
				url : true,
				maxlength : 60
			},
			"summary" : {
				maxlength : 160
			}
		},
		messages : {
			"summary" : {
				maxlength : ""
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
		errorClass : 'help-block',
		errorPlacement : function(error, element) {
			if (element.parent('.input-group').length) {
				error.insertAfter(element.parent());
			} else {
				error.insertAfter(element);
			}
		}
	});      
});
</script>