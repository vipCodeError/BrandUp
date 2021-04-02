package com.vipcodeerror.brandup.ui.main.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.data.model.PlanDataModel
import com.vipcodeerror.brandup.ui.main.adapter.PlanSelectorAdapter
import recycler.coverflow.RecyclerCoverFlow

class PlanSelectorActivity : AppCompatActivity() {
    private lateinit var planSelectorAdapter : PlanSelectorAdapter
    private lateinit var recyclerView: RecyclerCoverFlow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.plan_selector_activity)

        var planDataModel = mutableListOf<PlanDataModel>()
        planDataModel.add(PlanDataModel("1", "10/per day", "30/per day", "31/per day", "2/per day", "120"))
        planDataModel.add(PlanDataModel("1", "25/per day", "120/per day", "64/per day", "6/per day", "445"))
        planDataModel.add(PlanDataModel("1", "100/per day", "500/per day", "250/per day", "15/per day", "912"))

        recyclerView = findViewById(R.id.plan_recycler)
        planSelectorAdapter = PlanSelectorAdapter(this, planDataModel)
        recyclerView.adapter = planSelectorAdapter
    }
}