package com.example.flamespace.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flamespace.R

class History : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val backButton: FrameLayout = findViewById(R.id.backButton)
        backButton.setOnClickListener { onBackPressed() }

        val recyclerView = findViewById<RecyclerView>(R.id.history_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = HistoryAdapter(getHistoryList())
    }

    private fun getHistoryList(): List<HistoryItem> {
        val historyList = mutableListOf<HistoryItem>()
        historyList.add(HistoryItem("PTC 301", "Sched Example", "February 29, 2024"))
        return historyList
    }

}
