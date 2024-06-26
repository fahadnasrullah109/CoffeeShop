package com.coffee.shop.data.repository

import android.content.Context
import com.cofee.shop.R
import com.coffee.shop.data.DataResource
import com.coffee.shop.data.data
import com.coffee.shop.data.failed
import com.coffee.shop.data.local.CoffeeDao
import com.coffee.shop.data.local.UserDao
import com.coffee.shop.data.mappers.CoffeeMapper
import com.coffee.shop.data.mappers.HomeDataMapper
import com.coffee.shop.data.mappers.NotificationMapper
import com.coffee.shop.data.mappers.OrderMapper
import com.coffee.shop.data.mappers.UserMapper
import com.coffee.shop.data.models.db.User
import com.coffee.shop.data.models.response.LoginResponse
import com.coffee.shop.data.remote.NetworkApiService
import com.coffee.shop.data.safeApiCall
import com.coffee.shop.data.succeeded
import com.coffee.shop.domain.models.DomainCoffee
import com.coffee.shop.domain.models.DomainHomeData
import com.coffee.shop.domain.models.DomainNotification
import com.coffee.shop.domain.models.DomainOrder
import com.coffee.shop.domain.models.DomainUser
import com.coffee.shop.domain.repo.IRepository
import com.coffee.shop.preferences.DatastorePreferences
import com.coffee.shop.utils.AppConstant
import com.coffee.shop.utils.getDummyLoginResponse
import com.coffee.shop.utils.getRandomUUID
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class RepositoryImpl(
    private val context: Context,
    private val apiService: NetworkApiService,
    private val userDao: UserDao,
    private val coffeeDao: CoffeeDao,
    private val preferences: DatastorePreferences,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : IRepository {

    private val userMapper by lazy { UserMapper() }
    private val homeDataMapper by lazy { HomeDataMapper() }
    private val orderMapper by lazy { OrderMapper() }
    private val notificationMapper by lazy { NotificationMapper() }
    private val coffeeMapper by lazy { CoffeeMapper() }
    override fun shouldShowIntroduction(): Flow<DataResource<Boolean>> = flow {
        val isShown = preferences.isIntroductionPresented.firstOrNull()
        isShown?.let {
            emit(DataResource.Success(it.not()))
        } ?: run {
            emit(DataResource.Success(true))
        }

    }.flowOn(dispatcher)

    override fun getLoggedInUser(): Flow<DataResource<DomainUser?>> = flow {
        emit(DataResource.Loading)
        try {
            val user = userDao.getLoggedInUser()
            user?.let {
                emit(DataResource.Success(userMapper.mapToDomain(it)))
            } ?: run {
                emit(
                    DataResource.Error<Any>(
                        exception = RuntimeException(
                            "No Logged In User found"
                        )
                    )
                )
            }
        } catch (e: Exception) {
            emit(
                DataResource.Error<Any>(
                    exception = RuntimeException(
                        e.message ?: "Not able to get Logged In User"
                    )
                )
            )
        }
    }.flowOn(dispatcher)

    override fun logout(): Flow<DataResource<Boolean>> = flow {
        emit(DataResource.Loading)
        userDao.clear()
        emit(DataResource.Success(true))

    }.flowOn(dispatcher)

    override fun login(): Flow<DataResource<Response<LoginResponse>>> = flow {
        emit(DataResource.Loading)
        val usr = getDummyLoginResponse()
        userDao.insert(
            User(
                userId = usr.ID ?: getRandomUUID(),
                name = (usr.firstName ?: "firstname").plus(" ").plus(
                    usr.lastName ?: "lastname"
                ),
                profilePicture = "https://ibb.co/T4YShT1",
                location = "Lahore, Pakistan"
            )
        )
        emit(DataResource.Success(Response.success(usr)))
    }.flowOn(dispatcher)

    override fun register(): Flow<DataResource<Response<LoginResponse>>> = flow {
        emit(DataResource.Loading)
        val usr = getDummyLoginResponse()
        emit(DataResource.Success(Response.success(usr)))
    }.flowOn(dispatcher)

    override fun forgotPassword(): Flow<DataResource<Response<LoginResponse>>> = flow {
        emit(DataResource.Loading)
        val usr = getDummyLoginResponse()
        emit(DataResource.Success(Response.success(usr)))
    }.flowOn(dispatcher)

    override fun verifyOTP(): Flow<DataResource<Response<LoginResponse>>> = flow {
        emit(DataResource.Loading)
        val usr = getDummyLoginResponse()
        emit(DataResource.Success(Response.success(usr)))
    }.flowOn(dispatcher)

    override fun markIntroductionShown(): Flow<Unit> = flow<Unit> {
        preferences.saveIntroductionShown()
    }.flowOn(dispatcher)

    override fun loadHomeScreenData(): Flow<DataResource<DomainHomeData>> = flow {
        try {
            emit(DataResource.Loading)
            val response = safeApiCall {
                apiService.getHomeData(
                    url = AppConstant.GET_HOME_DATA_URL
                )
            }
            if (response.succeeded()) {
                val homeData = response.data?.body()
                homeData?.let { data ->
                    emit(DataResource.Success(homeDataMapper.mapToDomain(data)))
                } ?: run {
                    emit(
                        DataResource.Error<Any>(
                            exception = RuntimeException(
                                context.getString(R.string.generic_error)
                            )
                        )
                    )
                }
            }
            if (response.failed()) {
                emit(
                    DataResource.Error<Any>(
                        exception = RuntimeException(
                            (response as DataResource.Error<*>).exception.message
                        )
                    )
                )
            }
        } catch (e: Exception) {
            emit(
                DataResource.Error<Any>(
                    exception = RuntimeException(
                        e.message ?: context.getString(R.string.generic_error)
                    )
                )
            )
        }
    }.flowOn(dispatcher)

    override fun loadOrdersHistory(): Flow<DataResource<List<DomainOrder>>> = flow {
        try {
            emit(DataResource.Loading)
            val response = safeApiCall {
                apiService.getOrders(
                    url = AppConstant.GET_ORDERS_HISTORY_URL
                )
            }
            if (response.succeeded()) {
                val orders = response.data?.body()
                orders?.let { data ->
                    emit(DataResource.Success(data.map { orderMapper.mapToDomain(it) }))
                } ?: run {
                    emit(
                        DataResource.Error<Any>(
                            exception = RuntimeException(
                                context.getString(R.string.generic_error)
                            )
                        )
                    )
                }
            }
            if (response.failed()) {
                emit(
                    DataResource.Error<Any>(
                        exception = RuntimeException(
                            (response as DataResource.Error<*>).exception.message
                        )
                    )
                )
            }
        } catch (e: Exception) {
            emit(
                DataResource.Error<Any>(
                    exception = RuntimeException(
                        e.message ?: context.getString(R.string.generic_error)
                    )
                )
            )
        }
    }.flowOn(dispatcher)

    override fun loadNotifications(): Flow<DataResource<List<DomainNotification>>> = flow {
        try {
            emit(DataResource.Loading)
            val response = safeApiCall {
                apiService.getNotifications(
                    url = AppConstant.GET_NOTIFICATIONS_URL
                )
            }
            if (response.succeeded()) {
                val notifications = response.data?.body()
                notifications?.let { data ->
                    emit(DataResource.Success(data.map { notificationMapper.mapToDomain(it) }))
                } ?: run {
                    emit(
                        DataResource.Error<Any>(
                            exception = RuntimeException(
                                context.getString(R.string.generic_error)
                            )
                        )
                    )
                }
            }
            if (response.failed()) {
                emit(
                    DataResource.Error<Any>(
                        exception = RuntimeException(
                            (response as DataResource.Error<*>).exception.message
                        )
                    )
                )
            }
        } catch (e: Exception) {
            emit(
                DataResource.Error<Any>(
                    exception = RuntimeException(
                        e.message ?: context.getString(R.string.generic_error)
                    )
                )
            )
        }
    }.flowOn(dispatcher)

    override fun saveFavourite(coffee: DomainCoffee): Flow<DataResource<Boolean>> = flow {
        try {
            val result = coffeeDao.insert(coffeeMapper.mapToDb(coffee))
            emit(DataResource.Success(result > 0))
        } catch (e: Exception) {
            emit(
                DataResource.Error<Any>(
                    exception = RuntimeException(
                        e.message ?: context.getString(R.string.favourite_error)
                    )
                )
            )
        }
    }.flowOn(dispatcher)

    override fun deleteFavourite(coffeeId: String): Flow<DataResource<Boolean>> = flow {
        try {
            coffeeDao.removeCoffeeById(coffeeId)
            emit(DataResource.Success(true))
        } catch (e: Exception) {
            emit(
                DataResource.Error<Any>(
                    exception = RuntimeException(
                        e.message ?: context.getString(R.string.un_favourite_error)
                    )
                )
            )
        }
    }.flowOn(dispatcher)

    override fun isFavourite(coffeeId: String): Flow<DataResource<Boolean>> = flow {
        try {
            coffeeDao.isFavourite(coffeeId)?.let {
                emit(DataResource.Success(true))
            } ?: run {
                emit(DataResource.Success(false))
            }
        } catch (e: Exception) {
            emit(
                DataResource.Error<Any>(
                    exception = RuntimeException(
                        e.message ?: context.getString(R.string.generic_error)
                    )
                )
            )
        }
    }.flowOn(dispatcher)


    override fun loadFavourites(): Flow<DataResource<List<DomainCoffee>>> = flow {
        try {
            emit(DataResource.Loading)
            val response = coffeeDao.getAllFavouriteCoffees()
            emit(DataResource.Success(response.map { coffeeMapper.mapToDomain(it) }))
        } catch (e: Exception) {
            emit(
                DataResource.Error<Any>(
                    exception = RuntimeException(
                        e.message ?: context.getString(R.string.generic_error)
                    )
                )
            )
        }
    }.flowOn(dispatcher)

    companion object {
        private const val TAG = "Repository"
    }
}
