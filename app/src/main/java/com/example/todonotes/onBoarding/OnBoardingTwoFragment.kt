package com.example.todonotes.onBoarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.todonotes.R


class OnBoardingTwoFragment : Fragment() {
    lateinit var textViewDone : TextView
    lateinit var textViewBack: TextView
    lateinit var onOptionClick: OnOptionClick

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onOptionClick = context as OnOptionClick
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_on_boarding_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView(view)
    }

    private fun bindView(view: View) {
        textViewBack = view.findViewById(R.id.tectViewBack)
        textViewDone = view.findViewById(R.id.textViewDone)
        clickListener()
    }

    private fun clickListener() {
        textViewDone.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                onOptionClick.onOptionDone()
            }
        })
        textViewBack.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                onOptionClick.onOptionBack()
            }
        })
    }

    interface OnOptionClick {
        fun onOptionBack()
        fun onOptionDone()
    }
}
