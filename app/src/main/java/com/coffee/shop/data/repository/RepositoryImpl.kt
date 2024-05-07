package flagdps.lats.data.repository

import android.content.Context
import com.coffee.shop.data.DataResource
import com.coffee.shop.data.local.UserDao
import com.coffee.shop.data.mappers.UserMapper
import com.coffee.shop.data.models.db.User
import com.coffee.shop.data.models.response.LoginResponse
import com.coffee.shop.data.remote.NetworkApiService
import com.coffee.shop.domain.models.DomainUser
import com.coffee.shop.domain.repo.IRepository
import com.coffee.shop.preferences.DatastorePreferences
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
    private val preferences: DatastorePreferences,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : IRepository {

    private val userMapper by lazy { UserMapper() }
    override fun shouldShowIntroduction(): Flow<DataResource<Boolean>> = flow {
        val isShown = preferences.isIntroductionPresented.firstOrNull()
        emit(DataResource.Success(isShown ?: false))
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
        val usr = LoginResponse(
            token = "token",
            ID = "ID",
            firstName = "John",
            lastName = "Doe",
            email = "john.doe@example.com"
        )
        userDao.insert(
            User(
                userId = usr.ID ?: getRandomUUID(),
                name = (usr.firstName ?: "firstname").plus(" ").plus(
                    usr.lastName ?: "lastname"
                )
            )
        )
        emit(DataResource.Success(Response.success(usr)))
    }.flowOn(dispatcher)

    override fun register(): Flow<DataResource<Response<LoginResponse>>> = flow {
        emit(DataResource.Loading)
        val usr = LoginResponse(
            token = "token",
            ID = "ID",
            firstName = "John",
            lastName = "Doe",
            email = "john.doe@example.com"
        )
        userDao.insert(
            User(
                userId = usr.ID ?: getRandomUUID(),
                name = (usr.firstName ?: "firstname").plus(" ").plus(
                    usr.lastName ?: "lastname"
                )
            )
        )
        emit(DataResource.Success(Response.success(usr)))
    }.flowOn(dispatcher)

    override fun forgotPassword(): Flow<DataResource<Response<LoginResponse>>> = flow {
        emit(DataResource.Loading)
        val usr = LoginResponse(
            token = "token",
            ID = "ID",
            firstName = "John",
            lastName = "Doe",
            email = "john.doe@example.com"
        )
        userDao.insert(
            User(
                userId = usr.ID ?: getRandomUUID(),
                name = (usr.firstName ?: "firstname").plus(" ").plus(
                    usr.lastName ?: "lastname"
                )
            )
        )
        emit(DataResource.Success(Response.success(usr)))
    }.flowOn(dispatcher)

    override fun verifyOTP(): Flow<DataResource<Response<LoginResponse>>> = flow {
        emit(DataResource.Loading)
        val usr = LoginResponse(
            token = "token",
            ID = "ID",
            firstName = "John",
            lastName = "Doe",
            email = "john.doe@example.com"
        )
        userDao.insert(
            User(
                userId = usr.ID ?: getRandomUUID(),
                name = (usr.firstName ?: "firstname").plus(" ").plus(
                    usr.lastName ?: "lastname"
                )
            )
        )
        emit(DataResource.Success(Response.success(usr)))
    }.flowOn(dispatcher)

    companion object {
        private const val TAG = "Repository"
    }
}
