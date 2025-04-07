package com.example.myntra.domain.Usecase

import com.example.myntra.comman.ResultState
import com.example.myntra.comman.model.ProductModel
import com.example.myntra.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class getallwishlistproducts @Inject constructor(private val repo: Repo) {
    suspend fun getalllwishlistproducts(): Flow<ResultState<List<ProductModel>>> {
        return repo.getallwishlistproduct()
    }
}