$(function() {
	var plus = function(e) {
		$.ajax({
			type:"POST",
			context: this,
			url: "/trip/plus",
			data:{
				id : $(this).closest(".trip").attr("data-id")
			},
			success:function(data) {
				$(this).closest(".trip").addClass("plused");
				$(this).closest(".trip-action").find(".trip-action-count-for-presentation").html(data==0?'':data);
			}
		});
		e.stopPropagation();
	};
	
	var unPlus = function(e) {
		$.ajax({
			type:"POST",
			context: this,
			url: "/trip/unPlus",
			data:{
				id : $(this).closest(".trip").attr("data-id")
			},
			success:function(data) {
				$(this).closest(".trip").removeClass("plused");
				$(this).closest(".trip-action").find(".trip-action-count-for-presentation").html(data==0?'':data);
			}
		});
		e.stopPropagation();
	};
	
	var favorite = function(e) {
		$.ajax({
			type:"POST",
			context: this,
			url: "/trip/favorite",
			data:{
				id : $(this).closest(".trip").attr("data-id")
			},
			success:function(data) {
				$(this).closest(".trip").addClass("favorited");
				$(this).closest(".trip-action").find(".trip-action-count-for-presentation").html(data==0?'':data);
			}
		});
		e.stopPropagation();
	};
	
	var unFavorite = function(e) {
		$.ajax({
			type:"POST",
			context: this,
			url: "/trip/unFavorite",
			data:{
				id : $(this).closest(".trip").attr("data-id")
			},
			success:function(data) {
				$(this).closest(".trip").removeClass("favorited");
				$(this).closest(".trip-action").find(".trip-action-count-for-presentation").html(data==0?'':data);
			}
		});
		e.stopPropagation();
	};
	
	$('[data-toggle="tooltip"]').tooltip();
	
	$(".trip-action.trip-action-plus .trip-action-button").click(plus);
	
	$(".trip-action.trip-action-plus .trip-action-button-undo").click(unPlus);
	
	$(".trip-action.trip-action-favorite .trip-action-button").click(favorite);
	
	$(".trip-action.trip-action-favorite .trip-action-button-undo").click(unFavorite);
});