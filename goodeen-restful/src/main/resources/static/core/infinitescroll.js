$(function() {
	$("#content").infinitescroll({     //#content是包含所有图或块的容器  
    navSelector  : "#next",   //导航的容器，成功后会被隐藏  
    nextSelector : "#next a:first",  // 包含下一页链接的容器  
    itemSelector : ".stream-item",  // 你将要取来的内容块 
    maxPage: $("#total").val(),
    loading: {  
    	selector: ".spinner", // 显示loading信息的div
			msgText: "",
      finishedMsg: "",
      speed:"slow",
      img: "/images/loading.gif"  
    }  
	}, function(data, opts) {
     if(opts.maxPage==opts.state.currPage) {
				$(".timeline-end").removeClass("has-more-items");
     }
  });
  
	$(window).scroll(function() {
		if ($(window).scrollTop() > 100 && $(".timeline-end").hasClass("has-items")){ 
			$(".back-to-top").fadeIn( "slow" );
		} else {
			$(".back-to-top").fadeOut( "slow" );
		}
	});
	    
  $(".back-to-top").on("click",function(){
      $("html,body").animate({scrollTop:0},"slow");
  });
});