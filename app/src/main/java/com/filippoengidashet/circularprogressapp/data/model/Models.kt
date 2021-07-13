package com.filippoengidashet.circularprogressapp.data.model

/**
 * @author Filippo 13/07/2021
 */

// Start of response models

data class User(
    var username: String? = null,
    var email: String? = null,
    var profile: Profile? = null,
    var bookings: List<Booking>? = null,
    var userhours: List<Hour>? = null
)

data class Profile(
    var first_name: String? = null,
    var last_name: String? = null,
    var hours_available: String? = null
)

data class Booking(
    var id: String? = null,
    var start_location: Location? = null,
    var start_time: String? = null,
    var car_class: CarClass? = null,
    var car: Car? = null,
    var real_start_time: String? = null,
    var subscription_miles_left: String? = null,
)

data class Location(
    var id: Int? = null,
    var name: String? = null
)

data class CarClass(
    var id: Int? = null,
    var label: String? = null
)

data class Car(
    var id: Int? = null,
    var make: String? = null,
    var model: String? = null,
    var registration_number: String? = null,
    var image: Any? = null,
    var real_world_range: Int? = null,
    var last_energy_level: String? = null,
    var ev_access: List<String>? = null
)

data class Hour(
    var car_class: Int? = null,
    var hours_available: String? = null
)

// End of response models

// Start of transformed data models

data class UserData(val lastEnergyLevel: Float, val subscriptionMilesLeft: Float)

// End of transformed data models

