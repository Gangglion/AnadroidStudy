package com.example.practice_and.ScreenRotate

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import com.example.practice_and.R

/**
 * A simple [Fragment] subclass.
 * Use the [PortraitFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PortraitFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_portrait, container, false)
        view.findViewById<AppCompatButton>(R.id.btn_rotate).apply{
            setOnClickListener {
                startActivity(Intent(context, LandscapeActivity::class.java))
            }
        }

        return view
    }
}