/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.glion.reply.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.glion.reply.data.Email
import com.glion.reply.data.MailboxType
import com.glion.reply.ui.util.ReplyNavigationType

@Composable
fun ReplyApp(
    modifier: Modifier = Modifier,
    windowSize: WindowWidthSizeClass
) {
    val viewModel: ReplyViewModel = viewModel()
    val replyUiState = viewModel.uiState.collectAsState().value
    // MainActivity에서 전달받은 windowSize에 따라 다른 레이아웃 구성하기 위함
    val navigationType = when(windowSize){
        WindowWidthSizeClass.Compact ->{ // 일반 핸드폰
            ReplyNavigationType.BOTTOM_NAVIGATION
        }
        WindowWidthSizeClass.Medium ->{ // 폴더블 확장
            ReplyNavigationType.NAVIGATION_RAIL
        }
        WindowWidthSizeClass.Expanded ->{ // 테블릿
            ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER
        }
        else ->{
            ReplyNavigationType.BOTTOM_NAVIGATION
        }
    }

    ReplyHomeScreen(
        replyUiState = replyUiState,
        onTabPressed = { mailboxType: MailboxType ->
            viewModel.updateCurrentMailbox(mailboxType = mailboxType)
            viewModel.resetHomeScreenStates()
        },
        onEmailCardPressed = { email: Email ->
            viewModel.updateDetailsScreenStates(
                email = email
            )
        },
        onDetailScreenBackPressed = {
            viewModel.resetHomeScreenStates()
        },
        navigationType = navigationType,
        modifier = modifier
    )
}
