<#import "macro.ftl" as m>
<@c.html
	title="Goodeen"
	route="route-home"
	extJsfiles="core/follow.js,jquery/infinitescroll.js,core/infinitescroll.js"?split(",")>
	<div class="col-xs-8">
		<div class="panel panel-default">
		  <div class="panel-heading">
		    <h3 class="panel-title">
		    	<strong>行程动态</strong>
				</h3>
		  </div>
		  <div class="panel-body stream">
				<@m.tripTempWithWrapper/>
				<@c.streamFooter url="/?page=2" emptyText="没有发现行程。" icon="icon-road" />		
			</div>
		</div>			
	</div>
	<div class="col-xs-4">
		<div class="panel panel-default">
		  <div class="panel-heading">
		    <h3 class="panel-title">
		    	<strong>新加入的谷钉</strong>
				</h3>
		  </div>
		  <div class="panel-body stream">
				<@m.accountTempWithWrapper/>
			</div>
		</div>			
	</div>
</@c.html>