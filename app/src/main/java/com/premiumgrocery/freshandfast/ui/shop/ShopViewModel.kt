package com.premiumgrocery.freshandfast.ui.shop

import androidx.lifecycle.*
import com.premiumgrocery.freshandfast.remote.ICategoryRepository
import com.premiumgrocery.freshandfast.remote.model.SearchData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val categoryRepository: ICategoryRepository,
    private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    val categories = categoryRepository.getGroceryCategories().asLiveData(ioDispatcher)

    private val _searchResult = MutableLiveData<List<SearchData>>()
    val searchResult: LiveData<List<SearchData>> = _searchResult

    private val _isProcessing = MutableLiveData(true)
    val isProcessing: LiveData<Boolean> = _isProcessing

    fun finishedLoading() {
        _isProcessing.value = false
    }

    fun searchGroceryProduct(query: String) {
        _isProcessing.value = true
        viewModelScope.launch(ioDispatcher) {
            categoryRepository.searchGroceryProduct(query)
                .collect{
                    _searchResult.postValue(it)
                }
        }.invokeOnCompletion { _isProcessing.postValue(false) }
    }

    fun getSuggestedProduct(query: String) {
        // rmb to take from local before going api.
    }
}