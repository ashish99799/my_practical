package com.my.practical.task.view.splash

import android.os.Handler
import android.os.Looper
import com.my.practical.task.R
import com.my.practical.task.base.AppBaseActivity
import com.my.practical.task.databinding.ActivitySplashScreenBinding
import com.my.practical.task.model.db.GymData
import com.my.practical.task.model.db.PopularGymData
import com.my.practical.task.model.responses.GymList
import com.my.practical.task.view.main.search.SearchActivity
import com.my.practical.task.view_model.SplashScreenViewModel
import com.my.practical.task.util.getJsonDataFromAsset
import com.my.practical.task.view.main.dashboard.DashboardActivity
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

class SplashScreenActivity : AppBaseActivity<ActivitySplashScreenBinding, SplashScreenViewModel>() {

    override fun setViewBinding() = ActivitySplashScreenBinding.inflate(layoutInflater)

    override fun setViewModel() = SplashScreenViewModel.newInstance(this)

    lateinit var gymList: ArrayList<GymList>

    override fun initView() {
        gymList = getJsonDataFromAsset(("data.json"))
        Handler(Looper.getMainLooper()).postDelayed({
            nextStep()
        }, 2000)
    }

    override fun initOnClick() {

    }

    private fun nextStep() {
        viewModel.getAllGymList().observe(this, {
            if (it.isNullOrEmpty()) {
                loadDataFromJson()
            } else {
                startActivity(intentFor<DashboardActivity>().clearTask().newTask())
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            }
        })
    }

    private fun loadDataFromJson() {
        val mGymList = ArrayList<GymData>()
        val mPopularGymList = ArrayList<PopularGymData>()
        gymList.forEach {
            val gym = GymData(it.id, it.title ?: "", it.date_time ?: "", it.image ?: "", it.favorite ?: false, it.price ?: 0.0, it.rating ?: 0.0)
            mGymList.add(gym)
            (it.popular_gym ?: arrayListOf()).forEach { pop ->
                val pgym = PopularGymData(pop.id, it.id, pop.title ?: "", pop.description ?: "", pop.favorite ?: false, pop.price ?: 0.0, pop.rating ?: 0.0, pop.location ?: "", pop.image ?: "")
                mPopularGymList.add(pgym)
            }
        }
        viewModel.addAllGym(mGymList)
        viewModel.addAllPopularGym(mPopularGymList)
        nextStep()
    }
}