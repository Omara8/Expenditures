package com.planatech.expenditures.dashboard.view

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import com.firebase.ui.database.paging.DatabasePagingOptions
import com.planatech.expenditures.MainActivity
import com.planatech.expenditures.R
import com.planatech.expenditures.addtransaction.view.AddTransactionFragment
import com.planatech.expenditures.dashboard.viewmodel.DashboardViewModel
import com.planatech.expenditures.databinding.FragmentDashboardBinding
import com.planatech.expenditures.model.Transaction
import com.planatech.expenditures.utils.DatabaseUtils
import com.planatech.expenditures.utils.SPLASH_DISPLAY_TIME


class DashboardFragment : Fragment() {

    private var binding: FragmentDashboardBinding? = null
    private var dashboardViewModel: DashboardViewModel? = null
    private var dashboardAdapter: DashboardAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        loadViewModel()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        Handler().postDelayed({
            binding?.mainMotionLayout?.transitionToEnd()
            binding?.addButton?.visibility = View.VISIBLE
        }, SPLASH_DISPLAY_TIME)

        binding?.addButton?.setOnClickListener {
            val addTransactionFragment = AddTransactionFragment()
            addTransactionFragment.show(requireActivity().supportFragmentManager, "Add Transaction")
        }
    }

    private fun setUpRecyclerView() {
        val query = DatabaseUtils.getDatabase().getPagedOptions()

        val config: PagedList.Config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPrefetchDistance(1)
            .setPageSize(100)
            .build()

        val options = DatabasePagingOptions.Builder<Transaction>()
            .setLifecycleOwner(this)
            .setQuery(query, config, Transaction::class.java)
            .build()

        dashboardAdapter = DashboardAdapter(options) {
            //redirect to details screen
        }
        binding?.adapter = dashboardAdapter
    }

    private fun loadViewModel() {
        dashboardViewModel =
            ViewModelProvider(requireActivity()).get(DashboardViewModel::class.java)
        dashboardViewModel?.loadUserInfo()
        observeViewModel()
    }

    private fun observeViewModel() {
        dashboardViewModel?.user?.observe(viewLifecycleOwner, Observer {
            binding?.user = it
        })
    }

    private fun signOut() {
        val activity = requireActivity() as MainActivity
        activity.signOut()
    }

}