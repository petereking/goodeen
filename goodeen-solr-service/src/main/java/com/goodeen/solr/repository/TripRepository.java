package com.goodeen.solr.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.stereotype.Repository;

import com.goodeen.solr.model.TripCore;

@Repository("tripRepository")
public interface TripRepository extends
		PagingAndSortingRepository<TripCore, Integer> {
	@Highlight(fragsize = 500, fields = {"summary" }, prefix = "<b class=\"highlight\">", postfix = "</b>")
	HighlightPage<TripCore> findBySolrText(String text, Pageable pageable);
}
