$(function() {
	var follow = function() {
		$.ajax({
			type:"POST",
			context: this,
			url: "/user/follow",
			data:{
				id : $(this).closest('.user-actions').attr("data-user-id")
			},
			success:function(data) {
				$(this).off();
				$(this).click(unFollow);
				$(this).closest('.user-actions').removeClass("not-following").addClass("following");
			}
		});
	};
	
	var unFollow = function() {
		$.ajax({
			type:"POST",
			context: this,
			url: "/user/unFollow",
			data:{
				id : $(this).closest('.user-actions').attr("data-user-id")
			},
			success:function(data) {
				$(this).off();
				$(this).click(follow);
				$(this).closest('.user-actions').removeClass("following").addClass("not-following");
			}
		});
	};
	
	$('.not-following .follow-button').click(follow);
	
	$('.following .follow-button').click(unFollow);
});