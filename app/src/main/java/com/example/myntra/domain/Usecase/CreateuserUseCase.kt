package com.example.myntra.domain.Usecase

import com.example.myntra.comman.ResultState
import com.example.myntra.domain.model.Userdata
import com.example.myntra.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateuserUseCase @Inject constructor(private val repo: Repo) {
    fun Createuser(userdata: Userdata): Flow<ResultState<String>> {
        return repo.registeruserwithemailandpassword(userdata)
    }
}