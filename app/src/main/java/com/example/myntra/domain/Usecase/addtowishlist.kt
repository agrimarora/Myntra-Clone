package com.example.myntra.domain.Usecase

import android.annotation.SuppressLint
import com.example.myntra.comman.ResultState
import com.example.myntra.comman.model.ProductModel
import com.example.myntra.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Addtowishlist @Inject constructor(private val repo: Repo) {
    @SuppressLint("NotConstructor")
    suspend fun Addtowishlist(product: ProductModel): Flow<ResultState<String>> {
        return repo.addTowhislist(product)
    }
}