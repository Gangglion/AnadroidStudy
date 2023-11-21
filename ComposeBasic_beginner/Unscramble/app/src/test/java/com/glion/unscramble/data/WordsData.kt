/*
 * Copyright (c)2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glion.unscramble.data

const val MAX_NO_OF_WORDS = 10
const val SCORE_INCREASE = 20

// List with all the words for the Game
val allWords: Set<String> =
    setOf(
        "at",
        "sea",
        "home",
        "arise",
        "banana",
        "android",
        "birthday",
        "briefcase",
        "motorcycle",
        "cauliflower"
    )

// MEMO : Set 인 allWords를 (길이:단어) 형태의 Map으로 변환하여 wordLengthMap 에 저장.
private val wordLengthMap: Map<Int, String> = allWords.associateBy({ it.length }, { it })

// MEMO : 섞기 전 원래 단어를 길이로 찾아온다. ?: 를 사용하여 길이(키)에 해당하는 값이 없으면 "" 을 리턴한다.
internal fun getUnscrambledWord(scrambledWord: String) = wordLengthMap[scrambledWord.length] ?: ""
