package com.pbs.expenseApp.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppHorizontalPager(
    pageCount: Int = 1,
    horizontalPagerContent: @Composable PagerScope.(Int) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { pageCount })

    HorizontalPager(state = pagerState) {
        horizontalPagerContent(it)
    }
}
