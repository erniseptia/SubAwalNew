package app.story.mystoryappneww.injection

import android.content.Context
import app.story.mystoryappneww.api.ApiConfig
import app.story.mystoryappneww.database.StoryDatabase
import app.story.mystoryappneww.repository.StoryRepository

object Injection  {

    fun provideRepository(context: Context): StoryRepository {
        val  token = String
        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return StoryRepository(database, apiService, "Bearer $token")
    }

}