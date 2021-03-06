package com.sbaiardi.onion.data.roomViewModel

import androidx.lifecycle.*
import com.sbaiardi.onion.data.model.Percentages
import com.sbaiardi.onion.data.roomRepository.PercentagesRepo
import kotlinx.coroutines.launch

public class PercentageViewModel(private val repo: PercentagesRepo): ViewModel() {




    val allPer: LiveData<List<Percentages>> = repo.allPercentages.asLiveData()

    val filterWeek: LiveData<List<Percentages>> = repo.filterWeekPercentages.asLiveData()

    val filterMonth: LiveData<List<Percentages>> = repo.filterMonthPercentages.asLiveData()

    fun insert(per: Percentages) = viewModelScope.launch {
        repo.insert(per)
    }

    fun insertAll(pers: ArrayList<Percentages>) = viewModelScope.launch {
        repo.insertAll(pers)
    }
}

public class PercentageViewModelFactory(private val repository: PercentagesRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PercentageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PercentageViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}