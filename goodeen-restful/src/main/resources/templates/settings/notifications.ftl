<#import "macro.ftl" as m>
<@c.html title="Goodeen / 设置">
<@m.left activeNav="notifications"/>
<@m.main modelTitle="邮件通知" modelIntroduce="控制 Goodeen 向你发送邮件的时间和频率。 <a href=\"/articles/127860-how-to-change-your-email-preferences\" target=\"_blank\">了解更多。</a>">
<form id="notifications-form" method="post" class="form-horizontal" action="/settings/notifications/update">
	<input type="hidden" name="_method" value="PUT" />
	<span class="label label-info">期待中...</span>
	<hr/>
	<p/>
	<div class="form-group">
		<div class="col-xs-offset-3 col-xs-9">	
			<button id="setting-button" type="submit" class="btn btn-large btn-primary">保存更改</button>
		</div>		
	</div>	
</form>
</@m.main>
</@c.html>