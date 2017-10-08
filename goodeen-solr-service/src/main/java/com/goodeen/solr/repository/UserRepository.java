package com.goodeen.solr.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.stereotype.Repository;

import com.goodeen.solr.model.UserCore;

@Repository("userRepository")
public interface UserRepository extends
		PagingAndSortingRepository<UserCore, Integer> {
	@Highlight(fragsize = 200, fields = { "name",  "screenName", "description" }, prefix = "<b class=\"highlight\">", postfix = "</b>")
	HighlightPage<UserCore> findBySolrText(String text, Pageable pageable);
}
