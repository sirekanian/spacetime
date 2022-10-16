package com.sirekanian.spacetime.data

import com.sirekanian.spacetime.data.local.PageDao
import com.sirekanian.spacetime.data.local.PageEntity
import com.sirekanian.spacetime.model.ImagePage

interface Repository {

    suspend fun getPages(): List<ImagePage>
    suspend fun savePage(page: ImagePage)
    suspend fun removePage(page: ImagePage)

}

class RepositoryImpl(private val dao: PageDao) : Repository {

    override suspend fun getPages(): List<ImagePage> =
        dao.select().map(PageEntity::toModel)

    override suspend fun savePage(page: ImagePage) {
        dao.insert(page.toEntity())
    }

    override suspend fun removePage(page: ImagePage) {
        dao.delete(page.toEntity())
    }

}