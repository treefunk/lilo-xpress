package com.myoptimind.lilo_xpress.shared.api

import com.myoptimind.lilo_xpress.data.Meta
import com.myoptimind.lilo_xpress.data.Option

class ProvincesResponse(
    val data: Data,
    val meta: Meta
)

class Data (
    val provinces: List<Option>
)