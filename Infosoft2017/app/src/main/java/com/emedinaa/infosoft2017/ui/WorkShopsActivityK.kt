package com.emedinaa.infosoft2017.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.emedinaa.infosoft2017.R
import com.emedinaa.infosoft2017.data.model.EventResponseK
import com.emedinaa.infosoft2017.data.storage.ApliClientK
import com.emedinaa.infosoft2017.model.EntityK
import com.emedinaa.infosoft2017.ui.adapterskt.EventAdapterK
import kotlinx.android.synthetic.main.activity_work_shops.*
import kotlinx.android.synthetic.main.layout_progress.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WorkShopsActivityK : BaseActivityK() {

    private var eventAdapter:EventAdapterK?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_shops)
        ui()
        requestWorkshops()
    }

    private fun requestWorkshops(){
        showLoading()
        val call: Call<EventResponseK> = ApliClientK.getMyApiClient().workshops()
        call!!.enqueue(callback)
    }

    private fun renderWorkshops(events:List<EntityK.EventK>){
        eventAdapter= EventAdapterK(events,this)
        recyclerViewWorkshop.setAdapter(eventAdapter)

    }
    //endpoints
    private val callback: Callback<EventResponseK> = object: Callback<EventResponseK> {
        override fun onResponse(call: Call<EventResponseK>?, response: Response<EventResponseK>?) {
            hideLoading()

            log({"onResponse $response.body()"})
            renderWorkshops(response!!.body().data)
        }

        override fun onFailure(call: Call<EventResponseK>?, t: Throwable?) {
            hideLoading()

            log({"onFailure $t"})
        }
    }

    private fun showLoading(){
        relativeLayoutProgress.visibility= View.VISIBLE
    }

    private fun hideLoading(){
        relativeLayoutProgress.visibility= View.GONE
    }

    private fun ui(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title="Talleres"

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerViewWorkshop.layoutManager= mLayoutManager
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
