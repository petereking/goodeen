<#macro left activeNav="trips"> 
	<div class="col-xs-3">
		<div id="navigation" class="list-group">
			<#list navMap?keys as active>
			<#if q??>
				<a href="/search?${(active!="all")?string("mode="+active+"&","")}q=${q!""}" class="list-group-item${((searchMode!"all")==active)?string(" active","")}">${navMap[active]}</a>
			<#else>
		  	<a href="${(active=="trips" || user.screenName!=currentUser.screenName)?string("/${user.screenName}","")}${(active!="trips")?string("/${active}","")}" class="list-group-item${(activeNav==active)?string(" active","")}">${navMap[active]}</a>
			</#if>  
			</#list> 
		</div>
	</div>
</#macro>

<#macro main title="正在关注">	<div class="col-xs-9">
		<#if !(q??)>
			<@infoPanel/>
		</#if>
		<div class="panel panel-default">
		  <div class="panel-heading">
		    <h3 class="panel-title">
		    	<strong>
						<#if q??>
							有关<i class="highlight">『${q}』</i>的${title}如下
						<#else>
							${title}
						</#if>		    	
					</strong>
				</h3>
		  </div>
		  <div class="panel-body stream">
			  <#nested/>
			</div>
		</div>			
	</div>
</#macro>

<#macro infoPanel> 
<div class="panel panel-default">
	<div class="panel-heading row no-margin">
		<div class="picture">
			<img alt="peter_s_king" class="profile-avatar" src="${user.headerImage!"/images/head/placeholder.png"}">
		</div>
		<div class="info">
			<div>
				<div class="name">${user.name }</div>
				<div class="blog text-info">@${user.screenName}</div>
			</div>
			<div class="introduce" title="${user.description!""}">${user.description!""}</div>
		</div>
	</div>
	<div class="panel-body no-padding">		<ul class="stats">
			<li><a href="/${user.screenName}"><strong>${user.tripStat}</strong>行程</a></li>
			<li><a href="/${user.screenName}/following"><strong>${user.followingStat}</strong>正在关注</a></li>
			<li><a href="/${user.screenName}/followers"><strong>${user.followerStat}</strong>关注者</a></li>
		</ul>
		<@infoButton account=user />
	</div>
</div>
</#macro>

<#macro accountTemp>
	<#list accounts![] as account>
		<li class="stream-item">
		  <div class="account">
				<@infoButton account=account />
				<div class="content">
					<div class="stream-item-header">
			      <a class="account-group" href="/${account.screenName}">
			        <img class="avatar" src="${account.headerImage!"/images/head/placeholder.png"}">
			        <strong class="fullname">${account.name}‏</strong>
			        <span>‏</span>
			        <span class="username">@${account.screenName}</span>
			      </a>
					</div>
		      <p class="bio">
						${account.description!""}
					</p>					
				</div>
		  </div>
		</li>
	</#list>
</#macro>

<#macro accountTempWithWrapper>
	<ul id="content" class="list-unstyled">
		<@accountTemp/>
	</ul>
</#macro>

<#macro tripTemp>
	<#list trips![] as trip>
		<li class="stream-item">
		  <div class="trip" data-id="${trip.id}">
				<div class="content">
					<div class="stream-item-header">
			      <a class="account-group" href="/${trip.user.screenName!user.screenName}">
			        <img class="avatar" src="${trip.user.headerImage!"/images/head/placeholder.png"}">
			        <strong class="fullname">${trip.user.name!user.name}</strong>
			        <span>‏</span>
			        <span class="username">@${trip.user.screenName!user.screenName}</span>
			      </a>
					</div>			
					<p class="trip-summary">${trip.summary!""}</p>
					<div class="trip-tags">
						<p> 出行时间：${trip.departureTime?date} 到${trip.arrivalTime?date}</p>
						<p> 出行方式： ${trip.transport.display} </p>
					  	<i class="text-info icon-tags"></i>
							<#list (trip.tags!"")?split(",") as tag>
								<#if tag??>
								  	<a href="/search?q=${tag}" style="margin-right:5px;">${tag}</a>
								</#if>  
							</#list>		
					</div>
					<div class="stream-item-footer">
<!-- 						<div class="trip-actions pull-right">
							<div class="trip-action trip-action-reply" data-toggle="tooltip" data-placement="top" title="回复">
								<span class="trip-action-button">
								  <i class="icon-reply"></i>
								</span>
							</div>					
							<div class="trip-action trip-action-plus">
								<span class="trip-action-button" data-toggle="tooltip" data-placement="top" title="算我一个">
								  <i class="icon-user-add"></i>
									<span class="trip-action-count">
										<span class="trip-action-count-for-presentation"><#if trip.plusedStat!=0>${trip.plusedStat}</#if></span>
									</span>
								</span>
								<span class="trip-action-button-undo" data-toggle="tooltip" data-placement="top" title="退出行程">
								  <i class="icon-user-add"></i>
									<span class="trip-action-count">
										<span class="trip-action-count-for-presentation"><#if trip.plusedStat!=0>${trip.plusedStat}</#if></span>
									</span>
								</span>
							</div>					
							<div class="trip-action trip-action-favorite">
								<span class="trip-action-button" data-toggle="tooltip" data-placement="top" title="收藏">
								  <i class="icon-star"></i>
									<span class="trip-action-count">
										<span class="trip-action-count-for-presentation"><#if trip.favoritedStat!=0>${trip.favoritedStat}</#if></span>
									</span>
								</span>
								<span class="trip-action-button-undo" data-toggle="tooltip" data-placement="top" title="撤销收藏">
								  <i class="icon-star"></i>
									<span class="trip-action-count">
										<span class="trip-action-count-for-presentation"><#if trip.favoritedStat!=0>${trip.favoritedStat}</#if></span>
									</span>
								</span>
							</div>					
						</div> -->
					</div>								
				</div>
		  </div>				
		</li>
	</#list>	
</#macro>

<#macro tripTempWithWrapper>
	<ul id="content" class="list-unstyled">
		<@tripTemp/>
	</ul>
</#macro>

<#macro infoButton account>
	<#if account.relation=="self">
		<a class="inline-edit-profile-btn btn btn-info btn-sm" href="/settings/profile">编辑个人资料</a>
	<#else>
		<div class="user-actions ${account.relation}" data-user-id="${account.id}">
<!-- 			<div class="dropdown">
				<button class="btn btn-default btn-sm active dropdown-toggle" data-toggle="dropdown">
					<i class="icon-user"></i>
					<i class="caret"></i>
				</button>
				<ul class="dropdown-menu">
					<li><a>发送私信</a></li>
				</ul>
			</div> -->
			<button class="follow-button btn btn-sm">
				<span class="button-text follow-text"><i class="icon-eye"></i>关注</span>
				<span class="button-text following-text"><i class="icon-eye"></i>正在关注</span>
				<span class="button-text unfollow-text"><i class="icon-eye-off"></i>取消关注</span>
			</button>
		</div>
	</#if>
</#macro>