package com.cartrack.main.ui.myprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cartrack.main.databinding.FragmentMyProfileBinding
import com.cartrack.main.ui.dashboard.DashboardActivity

class MyProfileFragment : Fragment() {

    private lateinit var myProfileViewModel: MyProfileViewModel
    private var _binding: FragmentMyProfileBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myProfileViewModel =
            ViewModelProvider(this).get(MyProfileViewModel::class.java)

        _binding = FragmentMyProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val logoutTextView: TextView = binding.textLogout
        logoutTextView.setOnClickListener(View.OnClickListener {
            var dashboardActivity : DashboardActivity = activity as DashboardActivity
            dashboardActivity.logout()
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}