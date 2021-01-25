package com.planatech.expenditures.dashboard.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.paging.DatabasePagingOptions
import com.firebase.ui.database.paging.FirebaseRecyclerPagingAdapter
import com.firebase.ui.database.paging.LoadingState
import com.planatech.expenditures.databinding.TransactionItemBinding
import com.planatech.expenditures.model.Transaction

class DashboardAdapter(
    options: DatabasePagingOptions<Transaction>,
    val itemCallback: (transaction: Transaction) -> Unit
) :
    FirebaseRecyclerPagingAdapter<Transaction, DashboardAdapter.DashboardViewHolder>(options) {

    private val TAG = DashboardAdapter::class.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            TransactionItemBinding.inflate(layoutInflater, parent, false)
        return DashboardViewHolder(binding)
    }

    override fun onBindViewHolder(
        viewHolder: DashboardViewHolder,
        position: Int,
        model: Transaction
    ) {
        viewHolder.binding.transaction = model
        viewHolder.itemView.setOnClickListener {
            itemCallback(model)
        }
    }

    class DashboardViewHolder(val binding: TransactionItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onLoadingStateChanged(state: LoadingState) {
        when (state) {
            LoadingState.LOADING_INITIAL -> Log.d(TAG, "onLoadingStateChanged: test")
            LoadingState.LOADING_MORE -> Log.d(TAG, "onLoadingStateChanged: test")
            LoadingState.LOADED -> Log.d(TAG, "onLoadingStateChanged: test")
            LoadingState.FINISHED -> Log.d(TAG, "onLoadingStateChanged: test")
            LoadingState.ERROR -> Log.d(TAG, "onLoadingStateChanged: test")
        }
    }
}
