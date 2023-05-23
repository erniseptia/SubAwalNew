package app.story.mystoryappneww.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import app.story.mystoryappneww.api.ApiService
import app.story.mystoryappneww.dataclass.ListStoryItem
import app.story.mystoryappneww.dataclass.Stories

class StoryPagingSource(private val apiService: ApiService, private val token: String) : PagingSource<Int, ListStoryItem>(){

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val response = apiService.getAllStories(
                bearer = "Bearer $token",
                position,
                DEFAULT_BUFFER_SIZE
            ).execute().body()?.listStory ?: emptyList()

            LoadResult.Page(
                data = response,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (response.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }



    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private fun ListStoryItem.toStory(): Stories {
        val listItem = ListStoryItem(photoUrl, createdAt, name, description, lon, id, lat )
        return Stories(listOf(listItem), false, "Success")
    }


}