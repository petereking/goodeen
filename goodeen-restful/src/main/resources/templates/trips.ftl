<#import "macro.ftl" as m>
<@c.html title="Goodeen" route="route-profile" extJsfiles="core/follow.js,jquery/infinitescroll.js,core/infinitescroll.js"?split(",")>
	<@m.left activeNav="trips"/>
	<@m.main "我的行程">
		<@m.tripTempWithWrapper/>
		<@c.streamFooter url="/${user.screenName}?page=2" emptyText="@${user.screenName}尚未发布行程。" icon="icon-road" />
	</@m.main>
</@c.html>
<script type="text/javascript">
$(function() {
	$("#content").delegate(".trip", "click", function(){
		location.href="/trip/"+$(this).attr("data-id")
	});
	$("#content").delegate(".icon-reply", "click", function(e){
		e.stopPropagation();
	});
	$("#content").delegate(".icon-user-add", "click", function(e){
		e.stopPropagation();
	});
	$("#content").delegate(".icon-star", "click", function(e){
		e.stopPropagation();
	});
});
</script>
