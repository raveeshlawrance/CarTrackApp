package com.cartrack.main.ui.dashboard

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cartrack.main.R
import com.cartrack.main.listener.OnUserItemClickListener
import com.cartrack.main.networkimpl.ApiHelper
import com.cartrack.main.networkimpl.RetrofitBuilder
import com.cartrack.main.ui.details.UserDetailsActivity
import com.cartrack.main.utils.Constants
import com.cartrack.main.data.NetworkCallStatus.SUCCESS
import com.cartrack.main.data.NetworkCallStatus.ERROR
import com.cartrack.main.data.NetworkCallStatus.LOADING
import com.cartrack.main.data.dashboard.UserListResponseItem
import kotlinx.android.synthetic.main.dashboard_fragment.*

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class DashboardFragment : Fragment(), OnUserItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var adapter: UserListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var onUserItemClickListener: DashboardFragment
    var loading : Boolean = true

    private lateinit var viewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view : View = inflater.inflate(R.layout.dashboard_fragment, container, false)
        onUserItemClickListener = this
        viewModel = ViewModelProviders.of(
            this,DashboardViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(DashboardViewModel::class.java)
        init(view)
        return view
    }

    private fun init(view: View) {
        val activity = activity as Context
        recyclerView = view.findViewById(R.id.recylerview_user_list)
        adapter = UserListAdapter(activity.applicationContext, arrayListOf(), onUserItemClickListener)
        recyclerView.adapter = adapter
        var mLayoutManager: RecyclerView.LayoutManager
        mLayoutManager = LinearLayoutManager(getActivity()?.applicationContext);

        recyclerView.layoutManager = mLayoutManager
        val swipe_container : SwipeRefreshLayout = view.findViewById(R.id.swipe_container)
        swipe_container.setOnRefreshListener(this)
        swipe_container.post {
            loadUserList()
        }

    }

    private fun loadUserList() {
        if(!isNetworkAvailable(activity?.applicationContext)) {
            recyclerView.visibility = View.GONE
            progressBar.visibility = View.GONE
            empty_view.text = getString(R.string.network_error_text)
            empty_view.visibility = View.VISIBLE
            swipe_container.isRefreshing = false
            return
        }
        empty_view.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        viewModel.getUsers().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    SUCCESS -> {
                        swipe_container.isRefreshing = false
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        resource.data?.let { users -> retrieveList(users) }
                    }
                    ERROR -> {
                        swipe_container.isRefreshing = false
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        Toast.makeText(getActivity(), it.message, Toast.LENGTH_LONG).show()
                    }
                    LOADING -> {
                        swipe_container.isRefreshing = false
                        progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }

    override fun onRefresh() {
        loadUserList()
    }

    private fun retrieveList(userList: List<UserListResponseItem>) {
        adapter.apply {
            refreshUserList(userList)
            notifyDataSetChanged()
        }
    }

    override fun onItemClick(userListResponseItem: UserListResponseItem) {
        var detailsIntent = Intent(activity, UserDetailsActivity::class.java)
        detailsIntent.putExtra(Constants.USER_DETAILS, userListResponseItem)
        startActivity(detailsIntent)
    }

    private fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }
}