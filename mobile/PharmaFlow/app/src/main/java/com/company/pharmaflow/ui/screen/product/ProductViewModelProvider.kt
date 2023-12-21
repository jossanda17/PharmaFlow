package com.company.pharmaflow.ui.screen.product

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.company.pharmaflow.PharmaflowApplication
import com.company.pharmaflow.ui.item.ItemDetailsViewModel
import com.company.pharmaflow.ui.item.ItemEditViewModel
import com.company.pharmaflow.ui.item.ItemEntryViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for ItemEditViewModel
        initializer {
            ItemEditViewModel(
                this.createSavedStateHandle(),
                pharmaflowApplication().container.itemsRepository
            )
        }
        // Initializer for ItemEntryViewModel
        initializer {
            ItemEntryViewModel(pharmaflowApplication().container.itemsRepository)
        }

        // Initializer for ItemDetailsViewModel
        initializer {
            ItemDetailsViewModel(
                this.createSavedStateHandle(),
                pharmaflowApplication().container.itemsRepository
            )
        }

        // Initializer for HomeViewModel
        initializer {
            ProductViewModel(pharmaflowApplication().container.itemsRepository)
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.pharmaflowApplication(): PharmaflowApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PharmaflowApplication)