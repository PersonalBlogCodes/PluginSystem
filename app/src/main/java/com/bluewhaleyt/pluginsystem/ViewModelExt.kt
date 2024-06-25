package com.bluewhaleyt.pluginsystem

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
inline fun <reified VM : ViewModel> viewModel(crossinline f: () -> VM) =
    viewModel<VM>(factory = viewModelFactory(f))

inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>):T = f() as T
    }