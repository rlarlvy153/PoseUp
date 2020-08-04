package kr.co.youngcha.postup.ui.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.youngcha.postup.R
import kr.co.youngcha.postup.ui.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber


class MyPageFragment : Fragment() {

    lateinit var recyclerView : RecyclerView
    companion object {
        val instance = MyPageFragment()
    }

    private val viewModel : MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.mypage_fragment, container, false)


        recyclerView = root.findViewById(R.id.post_list)
        recyclerView.run{
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(context)
            adapter = MyPagePostListAdapter()
        }

        viewModel.myPostList.observe(activity!!, Observer{
            (recyclerView.adapter as MyPagePostListAdapter).run{
                addItem(it)
                notifyDataSetChanged()
                Timber.d("called")
            }
        })
        viewModel.getUserInfo(1)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}
