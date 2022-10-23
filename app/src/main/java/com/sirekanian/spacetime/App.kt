package com.sirekanian.spacetime

import android.app.Application
import androidx.room.Room
import com.sirekanian.spacetime.data.DebugDataCallback
import com.sirekanian.spacetime.data.DefaultDataCallback
import com.sirekanian.spacetime.data.Repository
import com.sirekanian.spacetime.data.RepositoryImpl
import com.sirekanian.spacetime.data.api.ThumbnailApi
import com.sirekanian.spacetime.data.local.Database

class App : Application() {

    val api by lazy { ThumbnailApi() }
    val repository: Repository by lazy {
        RepositoryImpl(
            Room.databaseBuilder(this, Database::class.java, "database")
                .addCallback(DefaultDataCallback(resources))
                .addCallback(DebugDataCallback())
                .build().getPageDao()
        )
    }

}