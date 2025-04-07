package com.example.myntra.domain.Usecase

import android.annotation.SuppressLint
import com.example.myntra.comman.ResultState
import com.example.myntra.domain.model.UserDataParent
import com.example.myntra.domain.model.Userdata
import com.example.myntra.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateUserDetails @Inject constructor(private val repo: Repo)  {
    @SuppressLint("NotConstructor")
    fun UpdateUserDetails(userdata: UserDataParent): Flow<ResultState<String>> {
        return repo.updateUserData(userDataParent = userdata)

    }
}