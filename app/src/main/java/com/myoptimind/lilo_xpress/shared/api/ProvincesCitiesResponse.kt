package com.myoptimind.lilo_xpress.shared.api

import com.myoptimind.lilo_xpress.data.Meta
import com.myoptimind.lilo_xpress.data.Option

class ProvincesCitiesResponse(
    val meta: Meta,
    val data: Data
){
    class Data(
        val cities: List<Option>,
        val provinces: List<Option>
    )
}