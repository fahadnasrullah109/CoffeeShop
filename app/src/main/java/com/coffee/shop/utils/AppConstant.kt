package com.coffee.shop.utils

import com.cofee.shop.BuildConfig

object AppConstant {
    const val validPasswordLength = 4
    const val validUsernameLength = 3
    const val validOTPLength = 4
    const val BASE_URL: String = BuildConfig.BASE_URL
    val GET_IMAGES_URL = "${BASE_URL}images/"
    val GET_HOME_DATA_URL = "${BASE_URL}home.json"
    val GET_ORDERS_HISTORY_URL = "${BASE_URL}orders.json"
    val GET_NOTIFICATIONS_URL = "${BASE_URL}notifications.json"
}