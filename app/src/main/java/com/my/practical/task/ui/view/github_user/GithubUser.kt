package com.my.practical.task.ui.view.github_user

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.my.practical.task.R
import com.my.practical.task.base.AppBaseActivity
import com.my.practical.task.databinding.ActivityGithubUserBinding
import com.my.practical.task.model.responses.RowData
import com.my.practical.task.model.responses.UserData
import com.my.practical.task.model.responses.UserRepoData
import com.my.practical.task.ui.listeners.GithubUserListener
import com.my.practical.task.ui.view.github_user.adapter.UserRepoAdapter
import com.my.practical.task.ui.view_model.GithubUserViewModel
import com.my.practical.task.util.INTENT_DATA
import com.my.practical.task.util.loadImage
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*

class GithubUser : AppBaseActivity<ActivityGithubUserBinding>(), GithubUserListener, SearchView.OnQueryTextListener, SwipeRefreshLayout.OnRefreshListener {

    var rowData: RowData? = null

    lateinit var viewModel: GithubUserViewModel
    lateinit var userRepoAdapter: UserRepoAdapter

    override fun setViewBinding() = ActivityGithubUserBinding.inflate(layoutInflater)

    override fun initView() {
        rowData = intent.getParcelableExtra(INTENT_DATA)

        if (rowData == null) {
            onBackPressed()
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = (rowData!!.login ?: "")

        binding.imgAvatar.loadImage((rowData!!.avatar_url ?: ""))
        binding.lblUserName.text = (rowData!!.login ?: "")

        // Attach ViewModel
        viewModel = ViewModelProvider(this).get(GithubUserViewModel::class.java)
        viewModel.githubUserListener = this
        viewModel.swipeRefreshLayout = binding.swipeRefreshLayout

        userRepoAdapter = UserRepoAdapter()
        binding.rvUserRepo.apply {
            adapter = userRepoAdapter.also {
                it.addAll(arrayListOf())
            }
        }

        binding.searchView.setOnQueryTextListener(this)
        (binding.searchView.findViewById(androidx.appcompat.R.id.search_plate) as View).setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite))

        binding.swipeRefreshLayout.setOnRefreshListener(this)
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent)
        binding.swipeRefreshLayout.isRefreshing = true

        viewModel.onUserInfo(this, (rowData!!.login ?: ""))
        onRefresh()
    }

    override fun initOnClick() {

    }

    override fun onRefresh() {
        viewModel.onUserRepo(this, (rowData!!.login ?: ""))
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        userRepoAdapter.filter.filter(newText)
        return false
    }

    override fun onSuccess(data: UserData) {
        binding.lblUserName.text = (data.login ?: "")
        binding.lblEmail.text = ("Email : ").plus(data.email ?: "")
        binding.lblLocation.text = ("Location : ").plus(data.location ?: "")
        binding.lblFollowers.text = (data.followers ?: 0).toString().plus(" Followers")
        binding.lblFollowing.text = ("Following ").plus(data.following ?: 0)

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val outputFormat = SimpleDateFormat("dd-MM-yyyy")

        val my_date = data.created_at
        val date: Date = inputFormat.parse(my_date)

        binding.lblJoinDate.text = ("Join Date : ").plus(outputFormat.format(date))
    }

    override fun onSuccess(data: List<UserRepoData>) {
        userRepoAdapter.addAll(data)
    }

    override fun onFailure(message: String) {
        toast(message)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}