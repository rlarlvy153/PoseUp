package kr.co.youngcha.postup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber


class MyPageFragment : Fragment() {

    lateinit var recyclerView : RecyclerView
    companion object {
        val instance = MyPageFragment()
//        private fun newInstance() = MapFragment()
    }

    lateinit var viewModel : ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.mypage_fragment, container, false)
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

        recyclerView = root.findViewById(R.id.post_list)
        recyclerView.run{
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(context)
            adapter = MyPagePostListAdapter(context)
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
