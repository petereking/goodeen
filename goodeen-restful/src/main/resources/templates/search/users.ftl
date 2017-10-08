<#import "../macro.ftl" as m>
<@c.html title="Goodeen" route="route-profile" extJsfiles="core/follow.js,jquery/infinitescroll.js,core/infinitescroll.js"?split(",")>
	<@m.left/>
	<@m.main "用户">
		<@m.accountTempWithWrapper/>	
		<@c.streamFooter url="/search?mode=users&q=${q!\"\"}&page=2" emptyText="没有找到相关的用户。" />
	</@m.main>
</@c.html>
