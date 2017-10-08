<#import "macro.ftl" as m>
<@c.html title="Goodeen / 设置">
<@m.left activeNav="security"/>
<@m.main modelTitle="安全与隐私" modelIntroduce="更改你的安全和隐私设置。">
<form id="security-form" method="post" class="form-horizontal" action="/settings/security/update" style="width:400px;">
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