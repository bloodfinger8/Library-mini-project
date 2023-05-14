package com.group.libraryapp.dto.response

class SuccessRes<T>(var data: T? = null) : BaseResponse(20000)
