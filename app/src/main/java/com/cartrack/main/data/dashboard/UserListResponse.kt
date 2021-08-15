package com.cartrack.main.data.dashboard

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Geo(
	val lng: String? = null,
	val lat: String? = null
) : Parcelable

@Parcelize
data class UserListResponseItem(
	val website: String? = null,
	val address: Address? = null,
	val phone: String? = null,
	val name: String? = null,
	val company: Company? = null,
	val id: Int? = null,
	val email: String? = null,
	val username: String? = null
) : Parcelable

@Parcelize
data class Company(
	val bs: String? = null,
	val catchPhrase: String? = null,
	val name: String? = null
) : Parcelable

@Parcelize
data class Address(
	val zipcode: String? = null,
	val geo: Geo? = null,
	val suite: String? = null,
	val city: String? = null,
	val street: String? = null
) : Parcelable
