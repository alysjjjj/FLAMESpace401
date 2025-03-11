package com.example.flamespace.buildings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.flamespace.R
import com.example.flamespace.user.Reservation

class Modalpopup : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_modal_popup, container, false)

        val roomCode = arguments?.getString("ROOM_CODE")

        val roomCodeTextView = view.findViewById<TextView>(R.id.roomCodeTextView)
        roomCodeTextView.text = roomCode

        val buttonClick = view.findViewById<Button>(R.id.reserveButton)
        buttonClick.setOnClickListener {
            val intent = Intent(requireActivity(), Reservation::class.java)
            intent.putExtra("ROOM_CODE", roomCode)
            startActivity(intent)
            dismiss()
        }


        return view
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }
}



