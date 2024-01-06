package com.pbs.expenseApp.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pbs.expenseApp.ui.AppViewModelProvider
import com.pbs.expenseApp.ui.viewmodels.HorizontalPagerViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppHorizontalPager(
    pageCount: Int = 1,
    horizontalPagerContent: @Composable PagerScope.(Int) -> Unit
) {
    val pagerVM: HorizontalPagerViewModel = viewModel(factory = AppViewModelProvider.Factory)

    val pagerState = rememberPagerState(pageCount = { pageCount })

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            pagerVM.setPagerDate(pageCount = page.toLong())
        }
    }

    HorizontalPager(state = pagerState) {
        horizontalPagerContent(it)
    }
}
