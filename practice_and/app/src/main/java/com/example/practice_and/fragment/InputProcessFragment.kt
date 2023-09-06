package com.example.practice_and.fragment

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.practice_and.R


class InputProcessFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_input_process, container, false)
        val root = view.findViewById<LinearLayout>(R.id.ll_root)

        root.viewTreeObserver.addOnGlobalLayoutListener {
            val isSoftInputVisible = isSoftInputVisible(root)

            if(!isSoftInputVisible){
                // 키보드가 숨겨졌을 때 동작함
                Log.d("shhan", "키보드 숨겨짐!")
            }
        }

        return view
    }

    private fun isSoftInputVisible(root: View): Boolean{
        val rect = Rect()
        root.getWindowVisibleDisplayFrame(rect)
        val screenHeight = root.height

        val keypadHeight = screenHeight - rect.bottom

        return keypadHeight > 90
    }
}