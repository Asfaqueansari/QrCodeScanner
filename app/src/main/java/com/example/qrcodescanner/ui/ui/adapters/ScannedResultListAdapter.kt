package com.example.qrcodescanner.ui.ui.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qrcodescanner.R
import com.example.qrcodescanner.ui.db.DBHelperI
import com.example.qrcodescanner.ui.db.entites.QrResult
import com.example.qrcodescanner.ui.ui.dialogs.QrCodeResultDialog
import com.example.qrcodescanner.ui.ui.utils.gone
import com.example.qrcodescanner.ui.ui.utils.toFormattedDisplay
import com.example.qrcodescanner.ui.ui.utils.visible
import kotlinx.android.synthetic.main.layout_single_item_qr_result.view.*

class ScannedResultListAdapter(var dbHelperI: DBHelperI,
                               var context: Context,
                               var listOfScannedResults: MutableList<QrResult>
    ):RecyclerView.Adapter<ScannedResultListAdapter.ScannedResultListViewHolder>() {

    private var resultDialog = QrCodeResultDialog(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScannedResultListViewHolder {
        return ScannedResultListViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.layout_single_item_qr_result,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listOfScannedResults.size
    }

    override fun onBindViewHolder(holder: ScannedResultListViewHolder, position: Int) {
        holder.bind(listOfScannedResults[position], position)
    }

    inner class ScannedResultListViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        fun bind(qrResult: QrResult, position: Int) {
            view.result.text = qrResult.result
            view.tvTime.text = qrResult.calendar.toFormattedDisplay()
            setFavourite(qrResult.favourite)
            onClicks(qrResult, position)
        }

        private fun onClicks(qrResult: QrResult, position: Int) {
            view.setOnClickListener {
                resultDialog.show(qrResult)
            }
            view.setOnLongClickListener {
                showDeleteDialog(qrResult, position)
                return@setOnLongClickListener true
            }
        }

        private fun showDeleteDialog(qrResult: QrResult, position: Int) {
            AlertDialog.Builder(context, R.style.CustomAlertDialog)
                .setTitle("Delete")
                .setMessage("Are You Really Want To Delete This Record?")
                .setPositiveButton("Delete") { dialog, which ->
                    deleteThisRecord(qrResult, position)
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.cancel()
                }.show()
        }

        private fun deleteThisRecord(qrResult: QrResult, position: Int) {
          dbHelperI.deleteQrResult(qrResult.id!!)
          listOfScannedResults.removeAt(position)
          notifyItemRemoved(position)
        }

        private fun setFavourite(favourite: Boolean) {
            if (favourite) {
                view.favouriteIcon.visible()
            } else {
                view.favouriteIcon.gone()
            }

        }
    }
}



