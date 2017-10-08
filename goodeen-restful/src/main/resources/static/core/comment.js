$(function(){
	Date.prototype.format = function(fmt) { 
		var o = { 
			"M+" : this.getMonth()+1,
			"d+" : this.getDate(),
			"h+" : this.getHours(),
			"m+" : this.getMinutes(),
			"s+" : this.getSeconds(),
			"q+" : Math.floor((this.getMonth()+3)/3),
			"S"  : this.getMilliseconds()
		}; 
		if(/(y+)/.test(fmt)) {
			fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
		}
    for(var k in o) {
    	if(new RegExp("("+ k +")").test(fmt)){
    		fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
    	}
    }
    return fmt; 
	}
	
	var showEmojiArea = function(e) {
		var sayType = $(e).attr("role");
		var summary = new Array();
		summary.push('<div id="say-div" class="row" style="margin:15px 0;">');
		summary.push('<input class="say-content" placeholder="我有话说..." role="' + sayType + '"></input>');
		summary.push('<span class="say-num" style="margin:5px;color:green;">0/80</span>');
		
		summary.push('<a id="reply-btn"');
		if(sayType != "say"){
			summary.push(' data-storeylord-id="' + $(e).attr("data-storeylord-id") + '"');
		}
		summary.push(' data-id="' + $(e).attr("data-id") + '"');
		summary.push(' data-user-id="' + $(e).attr("data-user-id") + '"');
		summary.push(' data-user-name="' + $(e).attr("data-user-name") + '"');
		summary.push(' data-user-screenname="' + $(e).attr("data-user-screenname") + '"');
		summary.push(' class="btn btn-info pull-right" style="margin:5px;" value="" role="' + sayType + '">发表</a>');
		summary.push('</div>');
		var summaryHtml = summary.join("");
		if(sayType == "say"){
			$(".comments").prepend(summaryHtml);
		}else if(sayType == "first-comment"){
			$(e).closest(".first-comment").append(summaryHtml);
		}else{
			$(e).closest(".sub-comments").append(summaryHtml);
		}
		
		var el = $(".say-content").emojioneArea({
			pickerPosition : "bottom",
	    inline: false
		});
		
		$(".emojionearea-editor").focus();
		
	  el[0].emojioneArea.on("keyup emojibtn.click", function(button, event) {
	    var sayNum = this.getText().length;
	    $(".say-num").html( sayNum + "/80");
	    if(sayNum > 80){
	    	$(".say-num").css("color", "red");
	    }
	  });
		
	  $("#reply-btn").click(function(e){
	  	var replyText = el[0].emojioneArea.getText();
	  	var replyHtml = $(".emojionearea-editor").html();
			if (replyText.length>80) {
				return alert("超出了" + (replyText.length-80) + "个字，请删减后重新发表");
			} else {
				var content;
				if(sayType == "say"){
					content = $(".comments");
				} else	if(sayType == "first-comment"){
					content = $(this).closest(".first-comment").children(".sub-comments");
				} else {
					content = $(this).closest(".sub-comments");
				}
				var myId = $(this).closest(".trip").attr("data-my-id");
				var myAvatar = $(this).closest(".trip").attr("data-my-avatar");
				var myName = $(this).closest(".trip").attr("data-my-name");
				var myScreenName = $(this).closest(".trip").attr("data-my-screenname");
				var upName = $(this).attr("data-user-name");
				var upScreenName = $(this).attr("data-user-screenname");
				var commentHTML = new Array();
				if(sayType == "say"){
					commentHTML.push('<li class="first-comment" data-id="-1">');
				} else {
					commentHTML.push('<li class="second-comment" data-id="-1">');
				}
				commentHTML.push('<div class="ui-avatar">');
				commentHTML.push('<a href="/' + myScreenName +'">');
				commentHTML.push('<img src="' + myAvatar +'" width="30">');
				commentHTML.push('</a>');
				commentHTML.push('</div>');
				
				commentHTML.push('<div class="comment-content">');
				commentHTML.push('<a href="/' + myScreenName +'">');
				commentHTML.push(myName);
				commentHTML.push('</a>');
				if(sayType != "say"){
					commentHTML.push(' 回复 ');
					commentHTML.push('<a href="/' + upScreenName +'">');
					commentHTML.push(upName);
					commentHTML.push('</a>');
				}
				commentHTML.push('<span class="content-text">');
				commentHTML.push("：" + replyHtml);
				commentHTML.push('</span>');
				commentHTML.push('<div class="comments-op">');
				commentHTML.push(new Date().format("yyyy-MM-dd hh:mm:ss"));
				
				commentHTML.push('<button style="top:0px;" class="trip-action-button say" ');
				if(sayType == "say"){
					commentHTML.push(' data-storeylord-id="-1"');
				} else {
					commentHTML.push(' data-storeylord-id="' + $(this).attr("data-storeylord-id") + '"');
				}	
				commentHTML.push(' data-id="-1"');
				commentHTML.push(' data-user-id="' + myId + '"');
				commentHTML.push(' data-user-name="' + myName + '"');
				commentHTML.push(' data-user-screenname="' + myScreenName + '"');
				if(sayType == "say"){
					commentHTML.push(' role="first-comment">');
				} else {
					commentHTML.push(' role="second-comment">');
				}				
				commentHTML.push('<i class="icon-reply"></i>');
				commentHTML.push('</button>');
				commentHTML.push('</div>');
				commentHTML.push('</div>');
				if(sayType == "say"){
					commentHTML.push('<ul class="sub-comments list-unstyled">');
					commentHTML.push('</ul>');
				}
				commentHTML.push('</li>');
				content.append(commentHTML.join(""));
				$.ajax({
					type:"POST",
					context: this,
					url: "/trip/comment/create",
					data:{
						"commentLevel" : sayType=="say" ? "STOREYLORD" : "ROOMLORD",
						"landLordId" : $(".trip").attr("data-id"),
						"storeyLord.id" : sayType=="say" ? "" : $(this).attr("data-storeylord-id"),
						"upUser.id" : $(this).attr("data-user-id"),
						"content" : replyText
					},
					success:function(data) {
						$("[data-storeylord-id|='-1']").attr("data-storeylord-id", data.sId);
						$("[data-id|='-1']").attr("data-id", data.id);
					}
				});
		  	$("#say-div").remove();
			}
			e.stopPropagation();
	  });		
	};
	
  $(".content-text").html(function(n,oldcontent){
  	return emojione.unicodeToImage(oldcontent);
	});  
  
  $("div").delegate(".say", "click",function(e){
  	if($("#say-div").length>0){
  		if($(".emojionearea-editor").html().length>0) {
  			if(window.confirm("你确定要放弃正在编辑的评论吗?")){
  				$("#say-div").remove();
  				showEmojiArea(this);
  			}
  		} else {
  			$("#say-div").remove();
  			showEmojiArea(this);
  		}
  	} else {
  		showEmojiArea(this);
  	}
  	e.stopPropagation();
  });
  
  $("body").click(function(e){
		var e=e?e:window.event; 
		var tar = e.srcElement||e.target;
  	if($(tar).closest("#say-div").length == 0 && $(".emojionearea-editor").length>0 && $(".emojionearea-editor").html().length==0) {
  		$("#say-div").remove();
  	}
  });  
});