<@c.html title="Goodeen"
	extCssfiles=["bootstrap/typeahead/typeahead.css",
		"bootstrap/tokenfield/tokenfield.css",
		"bootstrap/datepicker/css/datepicker.css",
		"bootstrap/fileinput/css/fileinput.css"]
	extJsfiles=["jquery/limitTextarea.js",
		"jquery/validate.js","jquery/tmpl.js",
		"bootstrap/tokenfield/tokenfield.js",
		"bootstrap/typeahead/bundle.js",
		"bootstrap/datepicker/js/datepicker.js",
		"bootstrap/datepicker/js/locales/datepicker.zh-CN.js",
		"bootstrap/fileinput/js/fileinput.js",
		"bootstrap/fileinput/js/locale_zh.js"]>
<form id="add-form" method="post" enctype="multipart/form-data" class="form-horizontal well white-bg" action="/trip">
	<@c.formgroup label={"for":"tags", "value":"特色标签", "class":"col-xs-3 control-label"} content={"class":"col-xs-7"}>
		<input type="text" id="tags" name="tags" placeholder="请为本次旅行设置一些特色标签吧" class="form-control" />
	</@c.formgroup>
	<@c.formgroup label={"for":"title", "value":"出行主题", "class":"col-xs-3 control-label"} content={"class":"col-xs-7"}>
		<input type="text" id="title" name="title" placeholder="请输入出行主题" class="form-control"/>
	</@c.formgroup>
<!-- 	<@c.formgroup label={"for":"cover", "value":"封面图片", "class":"col-xs-3 control-label"} content={"class":"col-xs-7"}>
		<input id="cover" name="cover" type="file" class="file-loading" placeholder="请选择上传封面图片">
	</@c.formgroup> -->
	<@c.formgroup label={"for":"departureTime", "value":"出发时间", "class":"col-xs-3 control-label"} content={"class":"col-xs-7"}>
		<div class="departureTime input-group date">
		  <input type="text" id="departureTime" name="departureTime" placeholder="请输入出发时间" readonly="true" class="form-control">
		  <span class="input-group-addon"><i class="icon-calendar"></i></span>
		</div>
	</@c.formgroup>
	<@c.formgroup label={"for":"arrivalTime", "value":"到达时间", "class":"col-xs-3 control-label"} content={"class":"col-xs-7"}>
		<div class="arrivalTime input-group date">
		  <input type="text" id="arrivalTime" name="arrivalTime" placeholder="请输入到达时间" readonly="true" class="form-control">
		  <span class="input-group-addon"><i class="icon-calendar"></i></span>
		</div>			
	</@c.formgroup>
	<@c.formgroup label={"for":"departure", "value":"出发地点", "class":"col-xs-3 control-label"} content={"class":"col-xs-7"}>
		<div class="bloodhound">
		  <input name="departure.id" type="hidden">
		  <input id="departure" name="departure.name" class="form-control typeahead" type="text" placeholder="中文/拼音/简拼">
		</div>			
	</@c.formgroup>
	<@c.formgroup label={"for":"destination", "value":"到达地点", "class":"col-xs-3 control-label"} content={"class":"col-xs-7"}>
		<div class="bloodhound">
		  <input name="destination.id" type="hidden">
		  <input id="destination" name="destination.name" class="form-control typeahead" type="text" placeholder="中文/拼音/简拼">
		</div>			
	</@c.formgroup>
	<@c.formgroup label={"for":"tripMode", "value":"出行方式", "class":"col-xs-3 control-label"} content={"class":"col-xs-7"}>
		<@c.checkButtonGroup name="tripMode" model=tripModeModel />
	</@c.formgroup>
	<@c.formgroup label={"for":"transport", "value":"交通工具", "class":"col-xs-3 control-label"} content={"class":"col-xs-7"}>
		<@c.checkButtonGroup name="transport" model=transportModel /> 
	</@c.formgroup>
	<@c.formgroup label={"for":"summary", "value":"行程概要", "class":"col-xs-3 control-label"} content={"class":"col-xs-7"}>
		<textarea id="summary" name="summary" placeholder="用少于160简单下描述本次行程吧！" rows="5" class="form-control"  style="resize: none;"></textarea>
		<p class="bio-label help-block">简单描述本次行程吧！ <span class="trip-summary-count"></span></p>		
	</@c.formgroup>
	<@c.formgroup content={"class":"col-xs-offset-3 col-xs-9"}>
		<button id="setting-button" type="submit" class="btn btn-large btn-primary">保存更改</button>
	</@c.formgroup>
</form>
</@c.html>
<#noparse>
<script id="header-tmpl" type="text/x-tmpl">
	<h3 class="league-name">${query}，若要缩小范围，请输入更多条件。</h3>
</script>
<script id="suggestion-tmpl" type="text/x-tmpl">
	<p>${fullname}</p>
</script>
</#noparse>
<script>
$(function(){
	$("#cover").fileinput({
		language : 'zh', //设置语言
		uploadUrl : "/gallerys/createPhoto4Gallery",
		uploadExtraData : function(previewId, index) {
			var obj = {};
			obj["name"] = $("#title").val();
			return obj;
		},
		allowedFileExtensions : ["jpg", "jpeg", "png", "gif"],
		maxFileSize : 2048,
		showPreview : false,
		browseClass : "btn btn-primary"
	}).on('fileuploaded', function(event, data, previewId, index) {
		alert("gid"+data.response[0].gallery.id);
	});
  
	var cities = new Bloodhound({
	  datumTokenizer: Bloodhound.tokenizers.obj.whitespace('match'),
	  queryTokenizer: Bloodhound.tokenizers.whitespace,
	  prefetch: '/data/region.json',
	  limit: 20
	});
	
	cities.clearPrefetchCache();
	cities.initialize();
	$('.bloodhound .typeahead').typeahead({
	  hint: true,
	  highlight: true,
	  minLength: 1
	},
	{
		name:'cities',
		restrictInputToDatum: true,
		displayKey: 'fullname',
    source: cities.ttAdapter(),
	  templates: {
		  empty: '<p class="empty-message">没有找到匹配的地区。</p>',
    	header: function(q){
    		return $('#header-tmpl').tmpl(q);
    	},
    	suggestion: function(city){
    		return $('#suggestion-tmpl').tmpl(city);
    	}
  	}
	}).on("typeahead:selected typeahead:autocompleted", function(e,datum) {
		$(this).closest(".twitter-typeahead").prev().val(datum.id);
		$(this).typeahead('close');
	});
	
	
	$('#tags').tokenfield();

  $("#summary").limitTextarea({
    countClass:"trip-summary-count"
  });
  
	$(".input-group.date").datepicker({
    language: "zh-CN",
    autoclose: true,
    format: "yyyy-mm-dd",
    todayHighlight: true,
    startDate:"-1w"
	});
	
	$(".departureTime").datepicker().on('changeDate', function(ev) {
	     $(".arrivalTime").datepicker("setStartDate",ev.date);
	});	

	$("#add-form").validate({
		rules : {
			"title" : {
				required : true
			},
			"departureTime" : {
				required : true
			},
			"arrivalTime" : {
				required : true
			},
			"departure.name" : {
				required : true
			},
			"destination.name" : {
				required : true
 			},
			"summary" : {
				maxlength:160
			}
		},
		messages : {
			title : {
				required : "主题不能为空。"
			},
			"departureTime" : {
				required : "出发日期不能为空。"
			},
			"arrivalTime" : {
				required : "到达日期不能为空。"
			},			
			"departure.name" : {
				required : "出发地不能为空。"
			},
			"destination.name" : {
				required : "目的地不能为空。"
			},
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
			form.submit();
		}
	});  
});
</script>
