package com.example.practice_and.saveinstance

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.example.practice_and.App
import com.example.practice_and.R


class SaveInstanceFragment : Fragment() {
    private var value = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.v(App.TAG + " : SaveInstanceFragment", "onAttach")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(App.TAG + " : SaveInstanceFragment", "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.v(App.TAG + " : SaveInstanceFragment", "onCreateView")
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_save_instance, container, false)
        if(savedInstanceState != null){
            value = savedInstanceState.getInt("COUNT")
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.v(App.TAG + " : SaveInstanceFragment", "onViewCreated")
        onCountValueShow(view)
        view.findViewById<AppCompatButton>(R.id.btn_count).apply{
            setOnClickListener {
                value += 1
                onCountValueShow(view)
            }
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.v(App.TAG + " : SaveInstanceFragment", "onViewStateRestored")
//        if(savedInstanceState != null){
//            value = savedInstanceState.getInt("COUNT")
//        }
    }

    override fun onStart() {
        super.onStart()
        Log.v(App.TAG + " : SaveInstanceFragment", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.v(App.TAG + " : SaveInstanceFragment", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.v(App.TAG + " : SaveInstanceFragment", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.v(App.TAG + " : SaveInstanceFragment", "onStop")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.v(App.TAG + " : SaveInstanceFragment", "onSaveInstanceState")
        outState.putInt("COUNT", value)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.v(App.TAG + " : SaveInstanceFragment", "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(App.TAG + " : SaveInstanceFragment", "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.v(App.TAG + " : SaveInstanceFragment", "onDetach")
    }

    private fun onCountValueShow(view: View){
        view.findViewById<AppCompatTextView>(R.id.tv_count).text = value.toString()
    }
}