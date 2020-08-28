package com.example.todonotes.onBoarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.todonotes.R

class OnBoardingOneFragment : Fragment() {

    lateinit var textViewNext: TextView
    lateinit var onNextClick: OnNextClick

    // whenever we have interface in fragment and we have to initialize it, then we do that in onAttach
    override fun onAttach(context: Context) {
        super.onAttach(context)
        onNextClick = context as OnNextClick
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarding_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
    }

    private fun bindViews(view: View) {
        textViewNext = view.findViewById(R.id.textViewNext)
        clickListener()
    }

    private fun clickListener() {
        textViewNext.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                onNextClick.onClick()
            }

        })
    }

    interface OnNextClick{
        fun onClick()
    }

}
