package com.goodeen.solr.model;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false, of="email")
@SolrDocument(solrCoreName = "user")
public class UserCore extends CopyField {
	@Id @Field
	private Integer id;
	@Field
	private String name;
	@Field
	private String screenName;
	@Field
	private String email;
	@Field
	private String url;
	@Field
	private String summary;
}
