package com.filippoengidashet.circularprogressapp.data.mapper

import com.filippoengidashet.circularprogressapp.data.model.User
import com.filippoengidashet.circularprogressapp.data.model.UserData

/**
 * @author Filippo 13/07/2021
 */
class UserDataTransformer {

    fun map(user: User): UserData {
        val bookings = user.bookings?.firstOrNull()
        val lastEnergyLevel = bookings?.car?.last_energy_level?.toFloat() ?: 0f
        val subscriptionMilesLeft = bookings?.subscription_miles_left?.toFloat() ?: 0f
        return UserData(lastEnergyLevel, subscriptionMilesLeft)
    }
}
