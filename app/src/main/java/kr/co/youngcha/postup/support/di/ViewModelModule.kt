package kr.co.youngcha.postup.support.di

import kr.co.youngcha.postup.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {

    viewModel { MainViewModel() }
}