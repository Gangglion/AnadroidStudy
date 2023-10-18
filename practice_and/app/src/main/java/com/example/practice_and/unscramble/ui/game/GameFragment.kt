/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.practice_and.unscramble.ui.game

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.practice_and.App
import com.example.practice_and.R
import com.example.practice_and.databinding.FragmentGameBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class GameFragment : Fragment() {

    private val viewModel: GameViewModel by viewModels()

    // Binding object instance with access to the views in the game_fragment.xml layout
    private lateinit var binding: FragmentGameBinding

    // Create a ViewModel the first time the fragment is created.
    // If the fragment is re-created, it receives the same GameViewModel instance created by the
    // first fragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout XML file and return a binding object instance
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)
        Log.d(App.TAG, "GameFragment create")
        Log.d(App.TAG, "Word : ${viewModel.currentScrambledWord} // Score : ${viewModel.score} // WordCount : ${viewModel.currentWordCount}")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 레이아웃변수 gameViewModel 과 maxNoOfWords를 초기화
        binding.gameViewModel = viewModel
        binding.maxNoOfWords = MAX_NO_OF_WORDS

        // LiveData는 수명주기 인식, 관찰이 가능. 레이아웃에서도 LiveData를 사용할 것이므로 레이아웃에 수명주기 소유자를 전달해주어야함
        binding.lifecycleOwner = viewLifecycleOwner

        // Setup a click listener for the Submit and Skip buttons.
        binding.submit.setOnClickListener { onSubmitWord() }
        binding.skip.setOnClickListener { onSkipWord() }
        // Update the UI

        // 사용하지 않음에 따라 제거
//        updateNextWordOnScreen()

        // 점수 및 단어 수 업데이트하는 텍스트뷰 제거
//        binding.score.text = getString(R.string.score, 0)
//        binding.wordCount.text = getString(
//            R.string.word_count, 0, MAX_NO_OF_WORDS)

        // dataBinding으로 레이아웃에서 viewModel의 currentScrambledWord 값을 직접 수신하므로, Fragment에서 관찰자 제거해도 됨
//        // currentScrambledWord LiveData 관찰자 연결
//        viewModel.currentScrambledWord.observe(viewLifecycleOwner) { newWorld -> // newWorld 글자가 뒤섞인 새 단어 값이 포함됨
//            binding.textViewUnscrambledWord.text = newWorld
//        } // viewLifecycleOwner는 이 관찰자(프래그먼트뷰)의 수명 주기를 나타냄

//        // score LiveData 관찰자 연결
//        viewModel.score.observe(viewLifecycleOwner){newScore ->
//            binding.score.text = getString(R.string.score, newScore)
//        }
//
//        // wordCount LiveData 관찰자 연결
//        viewModel.currentWordCount.observe(viewLifecycleOwner){newWordCount ->
//            binding.wordCount.text = getString(R.string.word_count, newWordCount, MAX_NO_OF_WORDS)
//        }
    }

    /*
* Checks the user's word, and updates the score accordingly.
* Displays the next scrambled word.
*/
    private fun onSubmitWord() {
        val playerWord = binding.textInputEditText.text.toString()

        if(viewModel.isUserWordCorrect(playerWord)){
            setErrorTextField(false)
            // updateNextWordOnScreen 제거됨에 따라 로직 변경
//            if(viewModel.nextWord()){
//                updateNextWordOnScreen()
//            } else{
//                showFinalScoreDialog()
//            }
            if(!viewModel.nextWord()){
                showFinalScoreDialog()
            }
        } else{
            setErrorTextField(true)
        }
    }

    /*
     * Skips the current word without changing the score.
     * Increases the word count.
     */
    private fun onSkipWord() {
        if(viewModel.nextWord()){
            setErrorTextField(false)

            // 사용하지 않음에 따라 제거
//            updateNextWordOnScreen()
        } else{
            showFinalScoreDialog()
        }
    }

    /*
     * Gets a random word for the list of words and shuffles the letters in it.
     */
    private fun getNextScrambledWord(): String {
        val tempWord = allWordsList.random().toCharArray() // allWordsList에서 .random() 랜덤하게 아무 원소나 얻어와서 .toCharArray() char형 배열로 반환.
        tempWord.shuffle() // .shuffle() 원소의 순서를 랜덤화 함
        return String(tempWord) // charArray인 tempWord를 string() string 으로 return
    }

    /*
     * Re-initializes the data in the ViewModel and updates the views with the new data, to
     * restart the game.
     */
    private fun restartGame() {
        viewModel.reinitializeData()
        setErrorTextField(false)

        // 사용하지 않음에 따라 제거
//        updateNextWordOnScreen()
    }

    /*
     * Exits the game.
     */
    private fun exitGame() {
        activity?.finish()
    }

    /*
    * Sets and resets the text field error status.
    */
    private fun setErrorTextField(error: Boolean) {
        if (error) {
            binding.textField.isErrorEnabled = true
            binding.textField.error = getString(R.string.try_again)
        } else {
            binding.textField.isErrorEnabled = false
            binding.textInputEditText.text = null
        }
    }

    /*
     * Displays the next scrambled word on screen.
     */
    // LiveData 사용함에 따라 제거. LiveData 로 단어의 변경을 관찰할 수 있기 때문
//    private fun updateNextWordOnScreen() {
//        binding.textViewUnscrambledWord.text = viewModel.currentScrambledWord
//    }


    private fun showFinalScoreDialog(){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.congratulations))
            .setMessage(getString(R.string.you_scored, viewModel.score.value))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.exit)) { _, _ ->
                exitGame()
            }
            .setPositiveButton(getString(R.string.play_again)){ _, _ ->
                restartGame()
            }
            .show()
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(App.TAG, "GameFragment destroy")
    }
}