package com.example.myntra.domain.Usecase

import com.example.myntra.comman.ResultState
import com.example.myntra.comman.model.CatergoryModel
import com.example.myntra.comman.model.SliderImage
import com.example.myntra.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class getsliderimages @Inject constructor(private val repo: Repo) {
    suspend fun getsliderimages(): Flow<ResultState<List<SliderImage>>> {
        return repo.getSliderpics()
    }
}