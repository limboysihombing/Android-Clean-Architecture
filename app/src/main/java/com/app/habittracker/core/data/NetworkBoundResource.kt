package com.app.habittracker.core.data

import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource <ResultType, RequestType>{
    private var result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())

        //val dbSource = loadFromDb().first();
        emitAll(loadFromDb().map { Resource.Success(it) })
    }

    protected open fun onFetchFailed(){}

    protected abstract fun loadFromDb(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    //protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>
    fun asFlow(): Flow<Resource<ResultType>> = result
}