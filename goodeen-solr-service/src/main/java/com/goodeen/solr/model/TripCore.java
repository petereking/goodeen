package com.goodeen.solr.model;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@SolrDocument(solrCoreName = "trip")
public class TripCore extends CopyField {
	@Id
	@Field
	private Integer id;
	@Field
	private String tags;
	@Field
	private String summary;
}
