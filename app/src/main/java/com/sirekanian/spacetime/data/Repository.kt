package com.sirekanian.spacetime.data

import com.sirekanian.spacetime.data.local.PageDao
import com.sirekanian.spacetime.data.local.PageEntity
import com.sirekanian.spacetime.model.ImagePage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface Repository {

    fun observePages(): Flow<List<ImagePage>>
    suspend fun addPage(page: ImagePage)
    suspend fun removePage(page: ImagePage)

}

class RepositoryImpl(private val dao: PageDao) : Repository {

    override fun observePages(): Flow<List<ImagePage>> =
        dao.observe().map { it.map(PageEntity::toModel) }

    override suspend fun addPage(page: ImagePage) {
        dao.insert(page.toEntity())
    }

    override suspend fun removePage(page: ImagePage) {
        dao.delete(page.toEntity())
    }

}