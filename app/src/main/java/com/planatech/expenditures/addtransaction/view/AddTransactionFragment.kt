package com.planatech.expenditures.addtransaction.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.planatech.expenditures.R
import com.planatech.expenditures.databinding.AddBottomSheetBinding
import com.planatech.expenditures.model.Transaction
import com.planatech.expenditures.utils.DatabaseUtils
import com.planatech.expenditures.utils.extensions.formatForFireBase
import com.planatech.expenditures.utils.extensions.isNotEmpty
import com.planatech.expenditures.utils.generateUUID
import java.util.*

class AddTransactionFragment : BottomSheetDialogFragment() {

    private var binding: AddBottomSheetBinding? = null
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
        binding = DataBindingUtil.inflate(inflater, R.layout.add_bottom_sheet, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = DatabaseUtils.getDatabase()

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.type_spinner_item,
            R.id.typeSpinnerItem,
            requireContext().resources.getStringArray(R.array.payment_types)
        )
        binding?.transactionTypeText?.setAdapter(adapter)
        setListeners()
    }

    private fun setListeners() {
        binding?.transactionTypeText?.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                1, 3 -> binding?.transactionEndDateLayout?.visibility = View.VISIBLE
                else -> binding?.transactionEndDateLayout?.visibility = View.GONE
            }
        }
        binding?.transactionEndDateText?.setOnClickListener {
            createDatePicker()
        }
        binding?.doneButton?.setOnClickListener {
            handleDoneButton()
        }
    }

    private fun createDatePicker() {
        var cal = Calendar.getInstance()
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                binding?.endDate = cal.time.formatForFireBase()
            }
        DatePickerDialog(
            requireContext(),
            dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun handleDoneButton() {
        when (binding?.transactionEndDateLayout?.visibility) {
            View.VISIBLE -> if (isValidTransactionWithDate()) {
                createAndAddTransaction(binding?.transactionEndDateText?.text.toString())
            }
            View.GONE -> if (isValidTransaction()) {
                createAndAddTransaction(null)
            }
        }
    }

    private fun createAndAddTransaction(endDate: String?) {
        val transaction = Transaction(
            generateUUID(),
            binding?.transactionNameText?.text.toString(),
            Date().formatForFireBase(),
            binding?.transactionAmountText?.text.toString().toFloat(),
            endDate ?: "", binding?.transactionTypeText?.text.toString()
        )

        database?.addTransaction(transaction) {
            dismiss()
        }
    }

    private fun isValidTransaction(): Boolean {
        return binding?.transactionNameText?.isNotEmpty(
            R.string.transaction_name_error,
            binding?.transactionNameLayout
        ) == true
                && binding?.transactionAmountText?.isNotEmpty(
            R.string.transaction_amount_error,
            binding?.transactionAmountLayout
        ) == true
                && binding?.transactionTypeText?.isNotEmpty(
            R.string.transaction_type_error,
            binding?.transactionTypeLayout
        ) == true
    }

    private fun isValidTransactionWithDate(): Boolean {
        return isValidTransaction() && binding?.transactionEndDateText?.isNotEmpty(
            R.string.transaction_date_error,
            binding?.transactionEndDateLayout
        ) == true
    }

}