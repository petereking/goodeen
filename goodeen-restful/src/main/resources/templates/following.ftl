<#import "macro.ftl" as m>
<@c.html title="Goodeen" route="route-profile" extJsfiles="core/follow.js,jquery/infinitescroll.js,core/infinitescroll.js"?split(",")>
	<@m.left activeNav="following"/>
	<@m.main "正在关注">
		<@m.accountTempWithWrapper/>		
		<@c.streamFooter url="/${user.screenName}/following?page=2" emptyText="@${user.screenName}尚未关注任何人。"/>
	</@m.main>
</@c.html>
