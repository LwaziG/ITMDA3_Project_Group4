package com.naveenapps.expensemanager.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.naveenapps.expensemanager.R
import androidx.compose.foundation.background

enum class HomeScreenBottomBarItems(
    @StringRes val labelResourceID: Int,
    @DrawableRes val iconResourceID: Int,
   // backgroundColor =  Color(0xFFFAD9E6),
) {
    Home(R.string.title_home, R.drawable.home),
    Analysis(R.string.analysis, R.drawable.ic_chart),
    Transaction(R.string.transaction, R.drawable.ic_transfer),
    Category(R.string.categories, R.drawable.study_w),
}
