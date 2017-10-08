<#import "macro.ftl" as m>
<@c.html title="Goodeen" route="route-profile" extJsfiles="core/follow.js,jquery/infinitescroll.js,core/infinitescroll.js"?split(",")>
	<@m.left activeNav="followers"/>
	<@m.main "关注者">
		<@m.accountTempWithWrapper/>				
		<@c.streamFooter url="/${user.screenName}/followers?page=2" emptyText="@${user.screenName}尚无任何关注者。"/>
	</@m.main>
</@c.html>
