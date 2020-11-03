package com.biomixers;

import com.biomixers.filter.SearchFilterQuery;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BiomixersApplication {

	private static SearchFilterQuery searchFilterQuery = new SearchFilterQuery();

	public static void main(String[] args) {

		SpringApplication.run(BiomixersApplication.class, args);
	}

	public static SearchFilterQuery getSearchFilterQuery() {
		return searchFilterQuery;
	}

	public static void setSearchFilterQuery(SearchFilterQuery data) {
		BiomixersApplication.searchFilterQuery = data;
	}
}
