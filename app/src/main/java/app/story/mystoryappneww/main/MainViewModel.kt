package app.story.mystoryappneww.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import app.story.mystoryappneww.api.ApiConfig
import app.story.mystoryappneww.dataclass.ListStoryItem
import app.story.mystoryappneww.dataclass.LoginResult
import app.story.mystoryappneww.dataclass.Stories
import app.story.mystoryappneww.injection.Injection
import app.story.mystoryappneww.repository.StoryRepository
import app.story.mystoryappneww.utils.UserPreference
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(storyRepository: StoryRepository) : ViewModel() {
    //private val pref: UserPreference
    val stories: LiveData<PagingData<ListStoryItem>> =
        storyRepository.getAllStories().cachedIn(viewModelScope)

    /**fun getUser(): LiveData<LoginResult> {
    return pref.getUser().asLiveData()
    }

    fun login() {
    viewModelScope.launch {
    pref.login()
    }
    }

    fun logout() {
    viewModelScope.launch {
    pref.logout()
    }
    }**/

    }


class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(Injection.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}



    /**private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }


    fun getAllStories(token: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getAllStories(bearer = "Bearer $token", 1, 15)
        client.enqueue(object : Callback<Stories> {
            override fun onResponse(
                call: Call<Stories>,
                response: Response<Stories>
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _stories.value = responseBody.listStory
                    Log.d(TAG, responseBody.listStory.toString())
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Stories>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }**/



