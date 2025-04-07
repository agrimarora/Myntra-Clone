package com.example.myntra.domain.Usecase

import com.example.myntra.comman.ResultState
import com.example.myntra.domain.model.UserDataParent
import com.example.myntra.domain.model.Userdata
import com.example.myntra.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class getUserBYIDusecase @Inject constructor(val repo: Repo) {
    fun getUserBYUid(uid: String) :Flow<ResultState<UserDataParent>>
    {
        return repo.getuserbyUID(uid)
    }
}