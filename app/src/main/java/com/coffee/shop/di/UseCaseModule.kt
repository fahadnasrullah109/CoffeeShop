package com.coffee.shop.di

import com.coffee.shop.domain.repo.IRepository
import com.coffee.shop.domain.usecases.ConfirmPasswordValidationUseCase
import com.coffee.shop.domain.usecases.DeleteFavouriteUseCase
import com.coffee.shop.domain.usecases.EmailValidationUseCase
import com.coffee.shop.domain.usecases.ForgotPasswordUseCase
import com.coffee.shop.domain.usecases.GetLoggedInUserUseCase
import com.coffee.shop.domain.usecases.HomeDataLoadUseCase
import com.coffee.shop.domain.usecases.IsFavouriteUseCase
import com.coffee.shop.domain.usecases.LoadNotificationsUseCase
import com.coffee.shop.domain.usecases.LoadOrdersUseCase
import com.coffee.shop.domain.usecases.LoginUseCase
import com.coffee.shop.domain.usecases.LogoutUseCase
import com.coffee.shop.domain.usecases.MarkIntroductionShownUseCase
import com.coffee.shop.domain.usecases.OTPValidationUseCase
import com.coffee.shop.domain.usecases.PasswordValidationUseCase
import com.coffee.shop.domain.usecases.RegisterUseCase
import com.coffee.shop.domain.usecases.SaveFavouriteUseCase
import com.coffee.shop.domain.usecases.ShowIntroductionDecisionUseCase
import com.coffee.shop.domain.usecases.UsernameValidationUseCase
import com.coffee.shop.domain.usecases.VerifyOTPUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @ViewModelScoped
    @Provides
    fun providePasswordValidationUseCase() = PasswordValidationUseCase()

    @ViewModelScoped
    @Provides
    fun provideConfirmPasswordValidationUseCase() = ConfirmPasswordValidationUseCase()

    @ViewModelScoped
    @Provides
    fun provideUsernameValidationUseCase() = UsernameValidationUseCase()

    @ViewModelScoped
    @Provides
    fun provideEmailValidationUseCase() = EmailValidationUseCase()

    @ViewModelScoped
    @Provides
    fun provideOTPValidationUseCase() = OTPValidationUseCase()

    @ViewModelScoped
    @Provides
    fun provideShowIntroductionDecisionUseCase(repository: IRepository) =
        ShowIntroductionDecisionUseCase(repository, Dispatchers.IO)

    @ViewModelScoped
    @Provides
    fun provideGetLoggedInUserUseCase(repository: IRepository) =
        GetLoggedInUserUseCase(repository, Dispatchers.IO)

    @ViewModelScoped
    @Provides
    fun provideLoginUseCase(repository: IRepository) = LoginUseCase(repository, Dispatchers.IO)

    @ViewModelScoped
    @Provides
    fun provideForgotPasswordUseCase(repository: IRepository) =
        ForgotPasswordUseCase(repository, Dispatchers.IO)

    @ViewModelScoped
    @Provides
    fun provideVerifyOTPUseCase(repository: IRepository) =
        VerifyOTPUseCase(repository, Dispatchers.IO)

    @ViewModelScoped
    @Provides
    fun provideMarkIntroductionShownUseCase(repository: IRepository) =
        MarkIntroductionShownUseCase(repository, Dispatchers.IO)

    @ViewModelScoped
    @Provides
    fun provideRegisterUseCase(repository: IRepository) =
        RegisterUseCase(repository, Dispatchers.IO)

    @ViewModelScoped
    @Provides
    fun provideHomeDataLoadUseCase(repository: IRepository) =
        HomeDataLoadUseCase(repository, Dispatchers.IO)

    @ViewModelScoped
    @Provides
    fun provideLoadOrdersUseCase(repository: IRepository) =
        LoadOrdersUseCase(repository, Dispatchers.IO)

    @ViewModelScoped
    @Provides
    fun provideLoadNotificationsUseCase(repository: IRepository) =
        LoadNotificationsUseCase(repository, Dispatchers.IO)

    @ViewModelScoped
    @Provides
    fun provideSaveFavouriteUseCase(repository: IRepository) =
        SaveFavouriteUseCase(repository, Dispatchers.IO)

    @ViewModelScoped
    @Provides
    fun provideDeleteFavouriteUseCase(repository: IRepository) =
        DeleteFavouriteUseCase(repository, Dispatchers.IO)

    @ViewModelScoped
    @Provides
    fun provideIsFavouriteUseCase(repository: IRepository) =
        IsFavouriteUseCase(repository, Dispatchers.IO)

    @ViewModelScoped
    @Provides
    fun provideLogoutUseCase(repository: IRepository) = LogoutUseCase(repository, Dispatchers.IO)
}