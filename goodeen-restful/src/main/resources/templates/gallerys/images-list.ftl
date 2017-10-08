<@c.html title="Goodeen"
	extCssfiles=["bootstrap/fileinput/css/fileinput.css"]
	extJsfiles=["bootstrap/bootpag/bootpag.js","bootstrap/fileinput/js/fileinput.js","fileinput/js/locale_zh.js"]>
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
.media-photo { height:120px; background-size:cover; background-position:center; position:relative; background-repeat: no-repeat;}
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
<#if gallery??>
	<ol class="breadcrumb">
		<li><a href="/gallerys/user/${gallery.creator}">相册首页</a></li>
		<li class="active">${gallery.name}</li>
	</ol>
  <#if isOwner?? && isOwner> 
		<p class="row">
			<button style="margin-left:15px;" class="btn btn-success" data-toggle="modal" data-target="#addPhotoModal">
				上传照片
			</button>	
		</p>
	</#if>

	<#if images?? && images?size gt 0>
		<div class="row">	 
			<#list images as image>
			  <div class="col-sm-3 add_img" style="margin-top:20px;">
					<a href="/photos${image.url}">
						<div class="media-photo" style="background-image:url(/photos${image.url});"></div>
					</a>
				</div>
			</#list>
		</div>	
		<div class="row">
		  <p class="bootpag"></p>
		</div>
	<#else>
	  <div class="row">
			<#if isOwner?? && isOwner>
				<p style="margin:15px;">相册为空，<a href="javascript:void(0)" data-toggle="modal" data-target="#addPhotoModal">点击添加照片吧！</a>	</p>		
			<#else>
				<p style="margin:15px;">相册为空</p>			
			</#if>
	  </div>
	</#if>
<#else>
	<ol class="breadcrumb">
		<li><a href="/">首页</a></li>
		<li><a href="/gallerys">我的相册</a></li>
	</ol>			
	<div class="row">
		<p style="margin:20px;">相册不存在，或已删除！
		</p>	
	</div>
</#if>


<!-- Modal -->
<div class="modal fade" id="addPhotoModal" tabindex="-1" role="dialog" aria-labelledby="addPhotoModalLabel">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">上传照片到相册：${gallery.name}		
        </h4>
      </div>
      <div class="modal-body">
				<input id="photos" name="photos" multiple type="file" class="file-loading">
				<p class="text-warning">请上传2M以内JPG,JPEG,PNG,GIF格式的图片。</p>
      </div>
    </div>
  </div>
</div>
</@c.html>
<script>
$(function () {
	$(".bootpag").bootpag({
		total: ${total!0},
		maxVisible: 5,
		page: ${page!1},
		leaps: false,
		firstLastUse: true
	}).on("page", function(event, page){
	  console.log("第"+page+"页！");
	  location.href = location.href.split("?")[0] + "?page="+(page)+"&size=${size}"
	});	
	
	$("#photos").fileinput({
		language : 'zh', //设置语言
		uploadUrl : "/gallerys/createPhoto4Gallery", //上传的地址
		uploadExtraData : {id:${gallery.id}},
		allowedFileExtensions : [ 'jpg', 'jpeg', 'png', 'gif' ],//接收的文件后缀,
		maxFileCount : 5,
		maxFileSize : 2048,
		showPreview : true, //是否显示预览画面
		browseClass : "btn btn-primary", //按钮样式             
		previewFileIcon : "<i class='glyphicon glyphicon-king'></i>",
		uploadAsync: false,
		msgFilesTooMany : "您选择上传的文件数量是{n}，超过最大允许数值{m}！"
	}).on('filebatchuploadsuccess', function(event, data, previewId, index) {
		if(data.response&&data.response.length>0){
	  	location.href =location.href;;
		}
	});
});
</script>