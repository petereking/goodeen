<#import "../macro.ftl" as m>
<@c.html title="Goodeen" route="route-profile"
	extCssfiles=["core/comment.css",
		"jquery/emojione/emojione.sprites.css",
		"jquery/emojione/area/emojionearea.css"] 
	extJsfiles=["jquery/textcomplete.js",
		"jquery/emojione/emojione.js",
		"jquery/emojione/area/emojionearea.js",
		"core/comment.js","core/triptools.js"]>
<div role="main"
		class="trip stream-item ${trip.plused?string("plused","")} ${trip.favorited?string("favorited","")}"
		data-id="${trip.id!0}"
		data-my-id="${currentUser.id!0}"
		data-my-name="${currentUser.name!""}"
		data-my-screenname="${currentUser.screenName!""}"
		data-my-avatar="${currentUser.headerImage!"/images/head/placeholder.png"}">
	<div class="content" style="padding-left:100px;width:900px;">
		<div class="stream-item-header">
		  <a class="account-group" href="/${trip.user.screenName!""}">
			<img class="avatar" src="${trip.user.headerImage!"/images/head/placeholder.png"}">
			<strong class="fullname">${trip.user.name!""}</strong>
			<span>‏</span>
			<span class="username">@${trip.user.screenName!""}</span>
		  </a>
		</div>			
		<p class="trip-summary">${trip.summary!""}</p>
<!-- 		<p>出行方式：${trip.transport.display}</p> -->
		<p class="trip-tags">
		  <i class="text-info icon-tags"></i>
			<#list (trip.tags!"")?split(",") as tag>
				<#if tag??>
				  	<a href="/search?q=${tag}" style="margin-right:5px;">${tag}</a>
				</#if>  
			</#list>	
		</p>
		<div class="stream-item-footer">
			<div class="trip-actions pull-right">
				<div 
					role="say"
					title="回复"		
					class="trip-action trip-action-reply say"
					data-placement="top"
					data-toggle="tooltip"
					data-id="${trip.id!0}"
					data-user-id="${trip.user.id!0}"
					data-user-name="${trip.user.name!""}"
					data-user-screenname="${trip.user.screenName!""}">
					<button class="trip-action-button">
					  <i class="icon-reply" ></i>
					</button>
				</div>					
				<div class="trip-action trip-action-plus">
					<button class="trip-action-button" data-toggle="tooltip" data-placement="top" title="算我一个">
					  <i class="icon-user-add"></i>
						<span class="trip-action-count">
							<span class="trip-action-count-for-presentation"><#if trip.plusedStat!=0>${trip.plusedStat}</#if></span>
						</span>
					</button>
					<button class="trip-action-button-undo" data-toggle="tooltip" data-placement="top" title="退出行程">
					  <i class="icon-user-add"></i>
						<span class="trip-action-count">
							<span class="trip-action-count-for-presentation"><#if trip.plusedStat!=0>${trip.plusedStat}</#if></span>
						</span>
					</button>
				</div>					
				<div class="trip-action trip-action-favorite">
					<button class="trip-action-button" data-toggle="tooltip" data-placement="top" title="收藏">
					  <i class="icon-star"></i>
						<span class="trip-action-count">
							<span class="trip-action-count-for-presentation"><#if trip.favoritedStat!=0>${trip.favoritedStat}</#if></span>
						</span>
					</button>
					<button class="trip-action-button-undo" data-toggle="tooltip" data-placement="top" title="撤销收藏">
					  <i class="icon-star"></i>
						<span class="trip-action-count">
							<span class="trip-action-count-for-presentation"><#if trip.favoritedStat!=0>${trip.favoritedStat}</#if></span>
						</span>
					</button>
				</div>					
			</div>
		</div>
		
		<ul class="comments list-unstyled" style="margin-top:20px;">
			<#list trip.comments![] as comment>
			<li class="first-comment" data-id="${comment.id!0}">
				<div class="ui-avatar">
					<a href="/${comment.user.screenName!""}"><img
						src="${comment.user.headerImage!"/images/head/placeholder.png"}" width="30"></a>
				</div>
				<div class="comment-content">
					<a href="/${comment.user.screenName!""}" role="first-comment">${comment.user.name!""}</a>
					<span class="content-text">：${comment.content!""}</span>
					<div class="comments-op">
						${comment.createTime?string("yyyy-MM-dd HH:mm:ss")}
						<button
							style="top:0px;"
							class="trip-action-button say"
							role="first-comment"
							data-id="${comment.id!0}"
							data-storeylord-id="${comment.id!0}"
							data-user-id="${comment.user.id!0}"
							data-user-name="${comment.user.name!""}"
							data-user-screenname="${comment.user.screenName!""}">						
						  <i class="icon-reply" ></i>
						</button>
					</div>
				</div>
				<ul class="sub-comments list-unstyled">
					<#list comment.subComments![] as subComment>
					<li class="second-comment" data-id="${comment.id!0}">
						<div class="ui-avatar">
							<a href="/${subComment.user.screenName!""}">
								<img src="${subComment.user.headerImage!"/images/head/placeholder.png"}" width="30">
							</a>
						</div>
						<div class="comment-content">
							<a href="/${subComment.user.screenName!""}">${subComment.user.name!""}</a>
							回复
							<a href="/${subComment.upUser.screenName!""}">${subComment.upUser.name!""}</a>
							<span class="content-text">：${subComment.content!""}</span>
							<div class="comments-op">
								${subComment.createTime?string("yyyy-MM-dd HH:mm:ss")}
								<button
									style="top:0px;"
									class="trip-action-button say"
									role="second-comment"
									data-id="${subComment.id!0}"
									data-storeylord-id="${subComment.storeyLord.id!0}"
									data-user-id="${subComment.user.id!0}"
									data-user-name="${subComment.user.name!""}"
									data-user-screenname="${subComment.user.screenName!""}">
								  <i class="icon-reply" ></i>
								</button>
							</div>
						</div>
					</li>
					</#list>
				</ul>
			</li>
			</#list>
		</ul>
										
	</div>
</div>
</@c.html>
