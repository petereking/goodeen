<@c.html title="Goodeen" route="route-home">
	<div>
	<h1>错误代码：405你访问get的地址不存在... </h1>
	<#if exception??>
		<p>错误类型：${exception!""}</p>
	</#if>
	</div>
</@c.html>