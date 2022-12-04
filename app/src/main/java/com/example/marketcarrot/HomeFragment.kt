package com.example.marketcarrot

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marketcarrot.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    /** view binding for fragment */
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var dataRVAdapter: DataRVAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // fragment에서 ById 대신에 사용하는 ViewBinding을 되게 하는 핵심 코드
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list: ArrayList<Data> = requireActivity().intent!!.extras!!.get("DataList") as ArrayList<Data>
        Log.e("HomeFragment", "Data List: ${list}")

        // Fragment에서 전달받은 list를 넘기면서 ListAdapter 생성
        dataRVAdapter = DataRVAdapter(list)
        rv_recyclerview.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        // RecyclerView.adapter에 지정
        rv_recyclerview.adapter = dataRVAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
