package com.planatech.expenditures.utils.extensions

fun String.encodeDots(): String{
    return this.replace(".", "%2E")
}

fun String.decodeDots(): String{
    return this.replace("%2E", ".")
}