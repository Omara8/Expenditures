package com.planatech.expenditures.edituserinfo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.planatech.expenditures.R
import com.planatech.expenditures.databinding.UserInfoSheetBinding
import com.planatech.expenditures.model.User
import com.planatech.expenditures.utils.DatabaseUtils
import com.planatech.expenditures.utils.extensions.isMoreThanZero
import com.planatech.expenditures.utils.extensions.isNotEmpty
import com.planatech.expenditures.utils.extensions.isValidSalaryDay

class UserInfoFragment(private val user: User?) : BottomSheetDialogFragment() {

    private var binding: UserInfoSheetBinding? = null
    private var database: DatabaseUtils? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.user_info_sheet, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = DatabaseUtils.getDatabase()

        binding?.user = user
        setListeners()
    }

    private fun setListeners() {
        binding?.doneButton?.setOnClickListener {
            handleDoneButton()
        }
        binding?.cancelButton?.setOnClickListener {
            dismiss()
        }
    }

    private fun handleDoneButton() {
        if (isValidInfo()) {
            user?.id?.let {
                val newUserInfo = User(
                    it,
                    user.name,
                    user.email,
                    user.image,
                    binding?.userBalanceText?.text?.toString()?.toFloat(),
                    binding?.userSalaryDateText?.text?.toString(),
                    binding?.userSalaryText?.text?.toString()?.toFloat(),
                    user.totalMonthlyPayments,
                    user.totalMonthlyIncome,
                    user.lastSalaryDate
                )
                database?.updateUserInfo(newUserInfo){
                    dismiss()
                }
            }
        }
    }

    private fun isValidInfo(): Boolean {
        return binding?.userBalanceText?.isNotEmpty(
            R.string.balance_error,
            binding?.userBalanceLayout
        ) == true
                && binding?.userSalaryText?.isMoreThanZero(
            R.string.salary_error,
            binding?.userSalaryLayout
        ) == true
                && binding?.userSalaryDateText?.isValidSalaryDay(
            R.string.salary_day_error,
            binding?.userSalaryDateLayout
        ) == true
    }
}