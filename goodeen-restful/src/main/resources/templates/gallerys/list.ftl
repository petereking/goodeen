<@c.html title="Goodeen"
	extCssfiles=["light-gallery/css/lightgallery.min.css","bootstrap/fileinput/css/fileinput.css"]
	extJsfiles=["light-gallery/js/lightgallery-all.min.js",
		"bootstrap/fileinput/js/fileinput.js","bootstrap/fileinput/js/locale_zh.js"]>
<style type="text/css">
.lg-outer {
    position: fixed;
    left: 0;
    z-index: 10000;
    }
.lg-toolbar {
    background-color: black;
    height:60px;
   // z-index: 10000;
}    
.t_img { margin-right:2px; height:400px; overflow:hidden; border-bottom:4px solid #fff;}
.lt_img { padding-right:2px; height:300px; overflow:hidden; border-bottom:4px solid #fff; border-right:2px solid #fff;}
.rt_img { padding-left:2px; height:300px; overflow:hidden; border-bottom:4px solid #fff; border-left:2px solid #fff;}
.l_img { padding-right:2px; height:145px; overflow:hidden; border-right:2px solid #fff;}
.r_img { padding-left:2px; height:145px; overflow:hidden; border-left:2px solid #fff;}
.m_img { padding-left:2px; padding-right:2px; height:145px; overflow:hidden;  border-left:2px solid #fff; border-right:2px solid #fff;}

.lt_img,.rt_img,.l_img,.r_img,.m_img,.t_img { background-size: cover; background-position: center; position: relative; background-repeat: no-repeat;}
.last_img { color:#fff; text-align:center; font-weight:bold; font-size:18px;}
.last_img:hover { color:#fff;}
.last_img span.nug_album { position:absolute; left:0px; top:0px; z-index:100; display:block; width:100%; height:145px; 
</style>
<#if isOwner?? && isOwner>
	<div class="row">
		<button style="margin-left:15px;" class="btn btn-success" data-toggle="modal" data-target="#addPhotoModal">
			上传照片
		</button>	
		<button class="btn btn-success" data-toggle="modal" data-target="#addGalleryModal">增加相册</button>
	</div>
</#if>

<#if gallerys?size == 0>
	<#if isOwner?? && isOwner>
		<p style="margin:15px;">还没有相册，<a href="javascript:void(0)" data-toggle="modal" data-target="#addGalleryModal">点击增加一个相册吧！</a></p>		
	<#else>
		<p style="margin:15px;">该用户还没有相册</p>			
	</#if>		
<#else>
	<#list gallerys as gallery>
		<#if gallery.images?? && gallery.images?size gt 0>
			<div class="lightgallery m_img last_img col-sm-3" style="margin: 10px;background-image:url(/images/8.jpg);">
				<#list gallery.images as image>
					<a href="/photos${image.url}" ${(image_index == 0)?string("style='display:block;width:100%;height:100%;background-image:url(/photos${image.url});'", "")}>
						<img src="/photos${image.url}" class="hide"/>
					</a>
				</#list>
			</div>
		<#else>
			<div class="col-sm-3 m_img last_img" style="margin: 10px;background-image:url(/images/8.jpg);">
				<span class="nug_album">相册“${gallery.name!""}”为空</span>
			</div>							
		</#if>
	</#list>
</#if>		

<!-- Modal -->
<div class="modal fade" id="addPhotoModal" tabindex="-1" role="dialog" aria-labelledby="addPhotoModalLabel">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">上传照片到相册：
					<select class="gallerySelector form-control" style="width:200px;display:inline-block;">
						<#if gallerys?? && (gallerys?size) gt 0>
							<#list gallerys as gallery>
						  <option value="${gallery.id}">${gallery.name}</option>
						  </#list>
					  <#else>
						  <option value="">商品相册</option>
					  </#if>
					</select>				
        </h4>
      </div>
      <div class="modal-body">
				<input id="photos" name="photos" multiple type="file" class="file-loading">
				<p class="text-warning">请上传2M以内JPG,JPEG,PNG,GIF格式的图片。</p>
				<br/>
      </div>
    </div>
  </div>
</div>

<div class="modal fade" id="addGalleryModal" tabindex="-1" role="dialog" aria-labelledby="addGalleryModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				添加相册
      </div>
      <div class="modal-body">
				<form class="addGalleryForm">
					<div class="form-group">
						<label for="name">相册名称</label>
						<input class="form-control" id="name" name="name" placeholder="相册名称必填，且不超过20字" />
					</div>
					<button type="button" class="addGallerybtn btn btn-success">提交</button>
				</form>
      </div>
    </div>
  </div>
</div>
</@c.html>
<script>
$(function () {
	$(".lightgallery").lightGallery();
	
	$('.addGallerybtn').click(function(){
		$.ajax({
			type	: "POST",
			url		: "/gallerys/createGallery",
			data	: $(".addGalleryForm").serialize(),
			dateType: "json",
			success	: function(data){
				location.href =location.href;
			}
		});
	});	
	
	$("#photos").fileinput({
		language : 'zh', //设置语言
		uploadUrl : "/gallerys/createPhoto4Gallery", //上传的地址
		uploadExtraData : function(previewId, index) {
			var obj = {};
			obj['id'] = $(".gallerySelector").val();
			return obj;
		},
		allowedFileExtensions : [ 'jpg', 'jpeg', 'png', 'gif' ],//接收的文件后缀,
		maxFileCount : 5,
		maxFileSize : 2048,
		showPreview : true, //是否显示预览画面
		browseClass : "btn btn-primary", //按钮样式             
		uploadAsync: false,
		msgFilesTooMany : "您选择上传的文件数量是{n}，超过最大允许数值{m}！"
	}).on('filebatchuploadsuccess', function(event, data, previewId, index) {
		if(data.response&&data.response.length>0){
	   	location.href ="/gallerys/" + data.response[0].gallery.id + "/images"
		}
	});
});
</script>