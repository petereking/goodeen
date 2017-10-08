(function($){
  $.fn.limitTextarea = function(opts){
	  var defaults = {
		countClass:"count",
        maxNumber:160
	  };
	  var option = $.extend(defaults,opts);
	  if(option.maxNumber - this.val().length>=0){
		  $("."+option.countClass).html("还可以输入<strong>" + (option.maxNumber - this.val().length) + "</strong>字!");
	  } else {
		  $("."+option.countClass).html("已经超过<strong>" + (this.val().length - option.maxNumber) + "</strong>字!");
	  }
	  this.each(function(){
		  var _this = $(this);
		  var fn = function(){
			var extraNumber = option.maxNumber - _this.val().length;
			var $count = $("."+option.countClass);
			if(extraNumber>=0){
			  $count.html("还可以输入<strong>" + extraNumber + "</strong>字!");	
			}else{
			  $count.html("已经超过<strong>" + (-extraNumber) + "</strong>字!");	
			}  
		  };
		  if(window.addEventListener) {
			_this.get(0).addEventListener("input", fn, false);
		  } else {
			_this.get(0).attachEvent("onpropertychange", fn);
		  }
		  if(window.VBArray && window.addEventListener) {
			_this.get(0).attachEvent("onkeydown", function() {
			  var key = window.event.keyCode;
			  (key == 8 || key == 46) && fn();
			});
			_this.get(0).attachEvent("oncut", fn);
		  }		  
	  });   
  };
})(jQuery);