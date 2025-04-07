package com.example.myntra.domain.Usecase

import com.example.myntra.comman.ResultState
import com.example.myntra.domain.model.Userdata
import com.example.myntra.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LogInUser @Inject constructor(private val repo: Repo)  {
    fun loginuser(userdata: Userdata): Flow<ResultState<String>> {
        return repo.loginuserwithemailandpassword(userdata)

    }
}