package com.example.search_data.repository

import com.example.search_data.mapper.toDomainArticle
import com.example.search_data.network.SearchApi
import com.example.search_domain.model.Article
import com.example.search_domain.repository.SearchRepository

class SearchRepositoryImpl(private val searchApi: SearchApi) : SearchRepository {
    override suspend fun getSearchArticle(map: MutableMap<String, String>): List<Article> {
        return searchApi.getSearchArticle(map).articles.map { it.toDomainArticle() }
    }
}