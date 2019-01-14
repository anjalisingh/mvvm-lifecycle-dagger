package com.solutions.openweatherapp.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.solutions.openweatherapp.R
import com.solutions.openweatherapp.model.CitiesModel
import com.solutions.openweatherapp.model.Coordinates
import com.solutions.openweatherapp.model.WeatherModel
import com.solutions.openweatherapp.ui.adapter.InfoMapAdapter
import com.solutions.openweatherapp.view.fragment.AppFragment


class MapFragment : AppFragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private var previousMarker: Marker? = null

    override fun onMarkerClick(marker: Marker?): Boolean {
        previousMarker?.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
        val model = marker?.tag as WeatherModel?
        googleMap?.animateCamera(CameraUpdateFactory.newLatLng(LatLng(model?.coords?.latitude!!, model?.coords?.longitude)))
        marker?.showInfoWindow()
        marker?.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))

        previousMarker = marker
        return true
    }

    companion object {
        fun newInstance() = MapFragment()
        val TAG = "MapFragment"
    }

    private var googleMap: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val layout =  inflater.inflate(R.layout.main_fragment, container, false)
        return layout
    }

    interface MapListener {
        fun onMapReady()
    }

    lateinit var  mCallback : MapListener
    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            mCallback = context as MapListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " must implement MapListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        if (activity != null) {
            val mapFragment = this?.childFragmentManager
                ?.findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment?.getMapAsync(this)
        }


    }

    override fun onMapReady(googleMap: GoogleMap) {
        // Add a marker in Sydney, Australia, and move the camera.
        val latlng = LatLng(1.29, 103.85)
        googleMap?.isIndoorEnabled = true
        googleMap?.uiSettings.isZoomControlsEnabled = true
        googleMap?.setOnMarkerClickListener(this)
        googleMap?.setOnInfoWindowCloseListener {
            previousMarker?.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
            previousMarker = null

        }

        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 11.0f))
        this.googleMap = googleMap
        mCallback?.onMapReady()
    }

    fun refreshData(citiesModel: CitiesModel?, centerCoords : Coordinates?) {

        if(googleMap == null || centerCoords == null) return
        googleMap?.clear()
        previousMarker = null
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(centerCoords?.latitude, centerCoords?.longitude), 11.0f))

        citiesModel?.list?.let {

            for (city in it) {
                val latlng = LatLng(city?.coords?.latitude!!, city?.coords?.longitude)
                val coordinate = LatLng(latlng.latitude, latlng.longitude)
                val markerOptions = MarkerOptions()
                markerOptions.position(latlng)
                    .title(city.cityName)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))

                activity?.let {
                    val customInfoWindow = InfoMapAdapter(it)
                    googleMap?.setInfoWindowAdapter(customInfoWindow)
                    val marker = googleMap?.addMarker(markerOptions)
                    marker?.tag = city
                }

            }
        }
    }
}
