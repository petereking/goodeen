<#import "macro.ftl" as m>
<@c.html title="Goodeen / 设置"
	extCssfiles=["jquery/colpick/css/colpick.css","bootstrap/fileinput1/fileinput.css"]
	extJsfiles=["jquery/colpick/js/colpick.js","jquery/validate.js","bootstrap/fileinput1/fileinput.js"]>
<@m.left activeNav="design"/>
<@m.main modelTitle="主题" modelIntroduce="个性化你的Goodeen 主题和个人资料页面。">
<form id="profile-form" method="post" enctype="multipart/form-data" class="form-horizontal" action="/settings/design/update">
	<@c.alertBox />
	<input type="hidden" name="_method" value="PUT" />
	<input id="id" name="theme.id" type="hidden" value="${currentUser.theme.id}">
	<input id="background-image" name="theme.backgroundImage" type="hidden" value="${currentUser.theme.backgroundImage}">
	<h3 class="settings-header">选择预制的样式</h3>
	<div class="form-group">
		<ul class="theme-list clearfix">
		<#list themeList as theme> 
			<li class="theme-item">
			  <button type="button" class="theme${(currentUser.theme.id==theme.id)?string(" selected", "")}" data-image="/images/themes/theme${theme.id}/${theme.backgroundImage}" data-id="${theme.id}" data-tiled="${theme.backgroundTiled?string("true","false")}" data-background_color="${theme.backgroundColor}" data-link_color="${theme.linkColor}">
			    <img class="theme-image" src="/images/themes/theme${theme.id}/swatch.gif" alt="">
			  </button>
			</li>
		</#list>	
		</ul>
	</div>	
	<hr/>
	<p/>	
	<h3 class="settings-header">自定义</h3>
	<p>你的修改将立即可见,但在你点击“保存更改”之前它们不会生效。<a href="/articles/15357" target="_blank">了解更多</a>。</p>	
	<div class="form-group">
	  <label class="col-xs-3 control-label">背景图</label>
	  <div class="col-xs-6">
			<div class="uploader-image uploader-background fileinput fileinput-new clearfix" data-provides="fileinput">
				<div id="background-image-preview" data-trigger="fileinput" class="fileinput-new image-preview" style="background-image: url(${currentUser.theme.backgroundImage});">
				</div>
				<div data-trigger="fileinput" class="fileinput-preview fileinput-exists image-preview">
				</div>
				<div class="uploader-tools">
	        <a class="btn btn-success btn-file">
	          <span class="fileinput-new"><i class="icon-plus"></i>选择已有图片</span>
	          <span class="fileinput-exists"><i class="icon-edit"></i>更改背景图片</span>
	          <input type="file" name="backgroundImageFile">
	      	</a>
					<div class="display-file-requirement">文件最大为 2MB</div>				
					<div class="checkbox">
			   		<input id="background-tiled" name="theme.backgroundTiled" type="checkbox" value="true" ${currentUser.theme.backgroundTiled?string(" checked=\"checked\"", "")}>
				    <input name="theme.backgroundTiled" type="hidden" value="false"> 平铺背景
					</div>          
        </div>				
		  </div>
	  </div>
	</div>	
	<div class="form-group">
	  <label class="col-xs-3 control-label">背景图片位置</label>
	  <div class="col-xs-6">
      <div class="radio">
        <input type="radio" value="LEFT" name="theme.backgroundPosition" ${(currentUser.theme.backgroundPosition=="LEFT")?string(" checked=\"checked\"", "")}>居左
      </div>
      <div class="radio">
        <input type="radio" value="CENTER" name="theme.backgroundPosition" ${(currentUser.theme.backgroundPosition=="CENTER")?string(" checked=\"checked\"", "")}>居中
      </div>
      <div class="radio">
        <input type="radio" value="RIGHT" name="theme.backgroundPosition" ${(currentUser.theme.backgroundPosition=="RIGHT")?string(" checked=\"checked\"", "")}>居右
      </div>
	  </div>
	</div>	
	<div class="form-group">
		<label for="background-color" class="col-xs-3 control-label">背景颜色</label>
		<div class="col-xs-6">
			<div class="input-group">
			  <span id="background-color-pick" class="color-pick input-group-addon" style="width:60px;background-color:${currentUser.theme.backgroundColor};"></span>
				<input id="background-color" name="theme.backgroundColor" class="color-preview form-control" type="text" value="${currentUser.theme.backgroundColor}" />
			</div>		
		</div>
	</div>
	<div class="form-group">
		<label for="link-color" class="col-xs-3 control-label">主题颜色</label>
		<div class="col-xs-6">
			<div class="input-group">
			  <span id="link-color-pick" class="color-pick input-group-addon" style="width:60px;background-color:${currentUser.theme.linkColor};"></span>
				<input id="link-color" name="theme.linkColor" class="color-preview form-control" type="text" value="${currentUser.theme.linkColor}" />
			</div>		
		</div>
	</div>
	<div class="form-group">
	  <label class="col-xs-3 control-label">覆盖</label>
	  <div class="col-xs-6">
	  	<div class="radio">
	      <input type="radio" name="theme.overlayColor" value="BLACK" ${(currentUser.theme.overlayColor=="BLACK")?string(" checked=\"checked\"", "")}>黑色
	    </div>
	    <div class="radio">
	      <input type="radio" name="theme.overlayColor" value="WHITE" ${(currentUser.theme.overlayColor=="WHITE")?string(" checked=\"checked\"", "")}>白色
	    </div>
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
$(function() {
	<@c.alertBoxJs />
  $(".color-preview").on("change", function(){
		$(this).prev().css("background-color", $(this).val());
  });
  $(".color-pick").colpick({
		layout:"hex",
		submit:1,
		submitText:"完成",
		onSubmit:function(hsb,hex,rgb,el) {
			$(el).next().val("#"+hex);
			$(el).css("background-color", "#"+hex);
			$(el).colpickHide();
		}
	});
  $(".theme").on("click", function(){
  	$(".theme.selected").removeClass("selected");
  	$(this).addClass("selected");
		var id = $(this).attr("data-id");
		$("#id").val(id);
		var image = $(this).attr("data-image");
		$("#background-image").val(image.substring("".length));
		$('.fileinput').fileinput('clear');
		$("#background-image-preview").css({"background-image": "url(" + image + ")"});
		var tiled = $(this).attr("data-tiled");
		$("#background-tiled").prop("checked",tiled=="true")
		var backgroundColor = $(this).attr("data-background_color");
		$("#background-color").val(backgroundColor);
		$("#background-color-pick").css({"background-color":backgroundColor});
		var linkColor = $(this).attr("data-link_color");
		$("#link-color").val(linkColor);
		$("#link-color-pick").css({"background-color":linkColor});
  });
	$("#profile-form").validate({
		rules : {
			"theme.backgroundColor" : {
				required : true,
				color : true
			},
			"theme.linkColor" : {
				required : true,
				color : true
			}
		},
		messages : {
			"theme.backgroundColor": {
				required : "请设置背景颜色。",
				color : "背景颜色格式不正确"
			},
			"theme.linkColor" : {
				required : "请设置主题颜色。",
				color : "主题颜色格式不正确"
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