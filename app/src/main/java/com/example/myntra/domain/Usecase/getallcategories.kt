package com.example.myntra.domain.Usecase

import com.example.myntra.comman.ResultState
import com.example.myntra.comman.model.CatergoryModel
import com.example.myntra.domain.model.Userdata
import com.example.myntra.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class getallcategories @Inject constructor(private val repo: Repo) {
    suspend fun getalllcategories(): Flow<ResultState<List<CatergoryModel>>> {
        return repo.getAllCategories()
    }
}