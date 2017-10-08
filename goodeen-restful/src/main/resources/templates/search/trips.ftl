<#import "../macro.ftl" as m>
<@c.html title="Goodeen" route="route-profile" extJsfiles="core/follow.js,jquery/infinitescroll.js,core/infinitescroll.js"?split(",")>
	<@m.left/>
	<@m.main "行程">
		<@m.tripTempWithWrapper/>	
		<@c.streamFooter url="/search?mode=trips&q=${q!\"\"}&page=2" emptyText="没有找到相关行程。" icon="icon-road" />
	</@m.main>
</@c.html>
<script type="text/javascript">
$(function() {
	$("#content").delegate(".trip", "click", function(){
		location.href="/trip/"+$(this).attr("data-id")
	});
});
</script>