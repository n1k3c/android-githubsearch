package n1x0nj4.githubsearch.api

import io.reactivex.Observable
import n1x0nj4.githubsearch.data.search.Sort
import n1x0nj4.githubsearch.model.ReposResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/search/repositories")
    fun getRepos(
            @Query("q") name: String,
            @Query("sort") sort: String = Sort.DEFAULT.value,
            @Query("page") page: Int
    ): Observable<ReposResponse>
}