package com.my.practical.task.view.main.dashboard

import android.view.View
import androidx.core.widget.NestedScrollView
import com.my.practical.task.R
import com.my.practical.task.base.AppBaseActivity
import com.my.practical.task.databinding.ActivityDashboardBinding
import com.my.practical.task.util.checkBackPress
import com.my.practical.task.util.doubleBackToExitPressedOnce
import com.my.practical.task.util.mSports
import com.my.practical.task.view.main.dashboard.adapter.PopularGymAdapter
import com.my.practical.task.view.main.dashboard.adapter.RecommendedGymAdapter
import com.my.practical.task.view.main.dashboard.adapter.SportsAdapter
import com.my.practical.task.view.main.search.SearchActivity
import com.my.practical.task.view_model.DashboardViewModel
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class DashboardActivity : AppBaseActivity<ActivityDashboardBinding, DashboardViewModel>(), View.OnClickListener {

    lateinit var recommendedGymAdapter: RecommendedGymAdapter
    lateinit var popularGymAdapter: PopularGymAdapter

    override fun setViewBinding() = ActivityDashboardBinding.inflate(layoutInflater)

    override fun setViewModel() = DashboardViewModel.newInstance(this)

    override fun initView() {
        recommendedGymAdapter = RecommendedGymAdapter()
        binding.rvRecommendedGym.apply {
            adapter = recommendedGymAdapter.also {
                it.addAll(arrayListOf())
            }
        }

        binding.rvSports.apply {
            adapter = SportsAdapter().also {
                it.addAll(mSports)
            }
        }

        popularGymAdapter = PopularGymAdapter()
        binding.rvPopularGym.apply {
            adapter = popularGymAdapter.also {
                it.addAll(arrayListOf())
            }
        }

        viewModel.getAllGymList().observe(this, {
            recommendedGymAdapter.addAll(it)
        })

        viewModel.getAllPopularGyms().observe(this, {
            popularGymAdapter.addAll(it)
        })
    }

    override fun initOnClick() {
        binding.fabGithub.setOnClickListener(this)
        binding.include.imgDumbbell.setOnClickListener(this)
        binding.include.imgMap.setOnClickListener(this)
        binding.include.imgSearch.setOnClickListener(this)
        binding.include.imgProfile.setOnClickListener(this)

        recommendedGymAdapter.setItemClickListener { view, position, data ->
            when (view.id) {
                R.id.divCell -> {
                    viewModel.getAllPopularGymList(data.id).observe(this, {
                        popularGymAdapter.addAll(it)
                    })
                }
                R.id.imgFavorite -> {
                    recommendedGymAdapter.list[position].favorite = !data.favorite
                    viewModel.updateGym(data)
                    recommendedGymAdapter.notifyItemChanged(position)
                }
            }
        }

        popularGymAdapter.setItemClickListener { view, position, data ->
            when (view.id) {
                R.id.imgFavorite -> {
                    popularGymAdapter.list[position].favorite = !data.favorite
                    viewModel.updatePopularGym(data)
                    popularGymAdapter.notifyItemChanged(position)
                }
            }
        }

        binding.divNestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY > oldScrollY) {
                binding.fabGithub.hide()
            } else {
                binding.fabGithub.show()
            }
        })
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fabGithub -> {
                startActivity<SearchActivity>()
            }
            R.id.imgDumbbell -> {
                toast("Dumbbell")
            }
            R.id.imgMap -> {
                toast("Map")
            }
            R.id.imgSearch -> {
                toast("Search")
            }
            R.id.imgProfile -> {
                toast("Profile")
            }
        }
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        checkBackPress()
    }

}