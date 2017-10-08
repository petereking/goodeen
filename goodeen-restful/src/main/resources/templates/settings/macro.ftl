<#macro left activeNav="account"> 
	<div class="dashboard">
		<div id="navigation" class="list-group">
			<#list navMap?keys as active>  
		  <a href="/settings/${active}" class="list-group-item${(activeNav==active)?string(" active","")}">${navMap[active]}</a>
			</#list> 
		</div>
	</div>
</#macro>

<#macro main modelTitle="" modelIntroduce="">
<div class="panel content-main">
	<div class="panel-heading content-header">
	  <h3 class="text-muted"><strong>${modelTitle}</strong></h3>
	  <p class="text-muted">${modelIntroduce}</p>
  	<hr>
	</div>
  <div class="panel-body">
		<#nested/> 
  </div>
</div> 
</#macro>