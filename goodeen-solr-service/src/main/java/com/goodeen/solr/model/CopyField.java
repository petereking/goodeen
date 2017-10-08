package com.goodeen.solr.model;

import lombok.Data;

import org.apache.solr.client.solrj.beans.Field;

public @Data class CopyField {
	@Field
	private String solrText;
}
