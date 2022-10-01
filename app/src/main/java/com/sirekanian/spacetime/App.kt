package com.sirekanian.spacetime

import android.app.Application
import com.sirekanian.spacetime.data.Repository
import com.sirekanian.spacetime.data.RepositoryImpl

class App : Application() {

    val repository: Repository by lazy { RepositoryImpl() }

}