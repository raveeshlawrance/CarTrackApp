package com.cartrack.main.ui.details

import android.Manifest
import android.R.drawable.arrow_down_float
import android.R.drawable.arrow_up_float
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.cartrack.main.R
import com.cartrack.main.data.dashboard.Address
import com.cartrack.main.data.dashboard.UserListResponseItem
import com.cartrack.main.databinding.ActivityUserDetailsBinding
import com.cartrack.main.utils.Constants

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_user_details.*
import android.location.LocationManager
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.transition.Visibility

class UserDetailsActivity : AppCompatActivity(), OnMapReadyCallback {

    private var locationPermissionGranted: Boolean = false
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityUserDetailsBinding
    private lateinit var userDetailsItem: UserListResponseItem
    private lateinit var formatAddress : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userDetailsItem =
            intent.getParcelableExtra(Constants.USER_DETAILS)!!
        (this as AppCompatActivity).supportActionBar!!.title = "Info"

        txtViewUserName.text = userDetailsItem.name
        txtViewPhoneNumber.text = userDetailsItem.phone
        txtViewEmail.text = userDetailsItem.email
        var address : Address =  userDetailsItem.address!!
        formatAddress = address.suite + System.getProperty ("line.separator") +
                address.street + System.getProperty ("line.separator") +
                address.city + System.getProperty ("line.separator") +
                address.zipcode
        txtViewAddress.text = formatAddress;
        txtViewWebsite.text = userDetailsItem.website
        txtViewCompany.text = userDetailsItem.company?.name
        layout_expand_collapse.setOnClickListener {
            if (layout_more_info.visibility == View.VISIBLE) {
                layout_more_info.visibility = View.GONE
                imgArrowUpDown.setBackgroundResource(R.drawable.ic_action_arrow_down)
            } else {
                layout_more_info.visibility = View.VISIBLE
                imgArrowUpDown.setBackgroundResource(R.drawable.ic_action_arrow_up)
            }
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        checkLocationPermission()
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val userLatLong = LatLng(userDetailsItem.address?.geo?.lat.toString().toDouble(), userDetailsItem.address?.geo?.lng.toString().toDouble())
        mMap.addMarker(MarkerOptions().position(userLatLong).title(formatAddress))
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLong, 4.0f))
        updateLocationUI()
    }

    @SuppressLint("MissingPermission")
    private fun updateLocationUI() {
        if (locationPermissionGranted) {
            mMap?.isMyLocationEnabled = true
            mMap?.uiSettings?.isMyLocationButtonEnabled = true
        } else {
            mMap?.isMyLocationEnabled = false
            mMap?.uiSettings?.isMyLocationButtonEnabled = false
        }
    }
}