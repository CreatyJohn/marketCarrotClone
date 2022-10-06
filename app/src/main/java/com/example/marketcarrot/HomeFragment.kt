package com.example.marketcarrot

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var adapter : DataRVAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<Data>

    // 변수 선언 자료Id : Array<자료형>
    lateinit var imageId : Array<Int>
    lateinit var textTitle : Array<String>
    lateinit var textInfo: Array<String>
    lateinit var textPrice: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInitialize()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = DataRVAdapter(dataList)
        recyclerView.adapter = adapter
    }
    
    private fun dataInitialize(){

        /** 향후 데이터에서 서버를 받았을때 아래와 같은 ArrayList 형태로 받아서 CRUD를 구축하려고 함*/

        dataList = arrayListOf<Data>()

        imageId = arrayOf(
            R.drawable.im_1,
            R.drawable.im_2,
            R.drawable.im_3,
            R.drawable.im_4,
            R.drawable.im_5,
            R.drawable.im_6,
            R.drawable.im_7,
            R.drawable.im_8,
            R.drawable.im_9,
            R.drawable.im_10,
            R.drawable.im_11,
            R.drawable.im_12
        )

        textTitle = arrayOf(
            getString(R.string.title_a),
            getString(R.string.title_b),
            getString(R.string.title_c),
            getString(R.string.title_d),
            getString(R.string.title_e),
            getString(R.string.title_f),
            getString(R.string.title_g),
            getString(R.string.title_h),
            getString(R.string.title_i),
            getString(R.string.title_j),
            getString(R.string.title_k),
            getString(R.string.title_l)
        )

        textInfo = arrayOf(
            getString(R.string.info_a),
            getString(R.string.info_b),
            getString(R.string.info_c),
            getString(R.string.info_d),
            getString(R.string.info_e),
            getString(R.string.info_f),
            getString(R.string.info_g),
            getString(R.string.info_h),
            getString(R.string.info_i),
            getString(R.string.info_j),
            getString(R.string.info_k),
            getString(R.string.info_l)
        )

        textPrice = arrayOf(
            getString(R.string.price_a),
            getString(R.string.price_b),
            getString(R.string.price_c),
            getString(R.string.price_d),
            getString(R.string.price_e),
            getString(R.string.price_f),
            getString(R.string.price_g),
            getString(R.string.price_h),
            getString(R.string.price_i),
            getString(R.string.price_j),
            getString(R.string.price_k),
            getString(R.string.price_l)
        )

        /** 적용 가능한 반복문 */

        for (i in imageId.indices){

            val dataFor = Data(imageId[i],textTitle[i],textInfo[i],textPrice[i])
            dataList.add(dataFor)

        }
    }
}