package com.myoptimind.lilo_xpress.shared

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.myoptimind.lilo_xpress.R
import com.myoptimind.lilo_xpress.guestlogin.api.GuestLoginService
import com.myoptimind.lilo_xpress.data.DropDownType
import com.myoptimind.lilo_xpress.data.Option
import com.myoptimind.lilo_xpress.data.Result
import com.myoptimind.lilo_xpress.shared.api.CitiesResponse
import com.myoptimind.lilo_xpress.shared.api.ProvincesCitiesResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

typealias MutableOptionList = MutableLiveData<Result<List<Option>>>

@Singleton
class DropdownDataSource
@Inject
constructor(val guestLoginService: GuestLoginService)
{
    // pre-populated fields guestlogininfo
    val agencies:         MutableOptionList = MutableLiveData()
    val attachedAgencies: MutableOptionList = MutableLiveData()
    val divisions:        MutableOptionList = MutableLiveData()
    val purposes:         MutableOptionList = MutableLiveData()
    val persons:          MutableOptionList = MutableLiveData()
    val regions:          MutableOptionList = MutableLiveData()

    init {
    }

    fun getSelected(index: String?, dropDownType: DropDownType): Option {
        val res = when (dropDownType) {
            DropDownType.AGENCIES -> agencies.value
            DropDownType.ATTACHED_AGENCIES -> attachedAgencies.value
            DropDownType.DIVISIONS -> divisions.value
            DropDownType.PURPOSES -> purposes.value
            DropDownType.PERSONS -> persons.value
            DropDownType.REGION -> regions.value
        }
        return if(index != null){
            when (res) {
                is Result.Success ->
                    res.data[index.toInt()]
                else ->
                    Option("0", "", "")

            }
        }else{
            Option("0","","")
        }
    }

    fun fetchGuestInfoStep1() {
        CoroutineScope(IO).launch{
            agencies.postValue(Result.Loading)
            try {
                val res = guestLoginService.getLoginFirstStep()
                agencies.postValue(Result.Success(res.data.agency))
                attachedAgencies.postValue(Result.Success(res.data.agency))
            }catch (exception: Exception){
                agencies.postValue(Result.Error(exception))
            }
        }
    }

    fun fetchGuestInfoStep2() {
        CoroutineScope(IO).launch{
            agencies.postValue(Result.Loading)
            try {
                val res = guestLoginService.getLoginSecondStep()
                divisions.postValue(Result.Success(res.data.divisions))
                purposes.postValue(Result.Success(res.data.purposes))
                persons.postValue(Result.Success(res.data.personsToVisit))
            }catch (exception: Exception){
                agencies.postValue(Result.Error(exception))
                persons.postValue(Result.Error(exception))
            }
        }
    }

    fun fetchGuestInfoStep3() {
        CoroutineScope(IO).launch{
            agencies.postValue(Result.Loading)
            try {
                val res = guestLoginService.getLoginThirdStep()
                regions.postValue(Result.Success(res.data.placeOfOrigins))
            }catch (exception: Exception){
                regions.postValue(Result.Error(exception))
            }
        }
    }

    suspend fun getCities(region: String): CitiesResponse {
        return guestLoginService.getCities(region)
    }

    suspend fun getProvincesCities(region: String): ProvincesCitiesResponse {
        return guestLoginService.getProvincesCities(region)
    }
}

/**
 *  EXTENSION FUNCTIONS
 */

// Populate Dropdown
fun AutoCompleteTextView.setUpDropDown(context: Context, values: List<String>, onItemClickListener: (index: Int) -> Unit ){
    val adapter: ArrayAdapter<String> = ArrayAdapter(
        context,
        R.layout.support_simple_spinner_dropdown_item,
        values
    )
    this.setAdapter(adapter)
    this.setOnClickListener{
        this.showDropDown()
    }
    this.setOnItemClickListener { _, _, i, _ -> onItemClickListener(i) }
}



// Set Event Listeners to Dropdown
fun Result<List<Any>>.handleData(context: Context,
                                 autoCompleteTextView: AutoCompleteTextView,
                                 list: (r : Result.Success<Any>) -> List<String> = { res ->
                                     (res.data as List<Option>).map{ o -> o.name!! }
                                 },
                                 onFetchSuccess: () -> Unit = { Timber.v("Successfully fetched") },
                                 onSelectItem: (index: Int) -> Unit = { Timber.v("Selected index $it")},
                                 onFetchFail: (errorMessage: String) -> Unit = { Timber.v("Failed fetching") }
){
    when(this){
        is Result.Success -> {
            autoCompleteTextView.setUpDropDown(
                context,
                list(this)
            ) {
                onSelectItem(it)
            }
            onFetchSuccess()
        }
        is Result.Error -> {
            val errorMessage = when (this.error) {
                is UnknownHostException -> "Internet Connection is required. Please try again.."
                else -> "Something went wrong"
            }
            context.displayAlert("",errorMessage)
            onFetchFail(errorMessage)
        }
        Result.Loading -> {
            // nothing
        }
    }
}