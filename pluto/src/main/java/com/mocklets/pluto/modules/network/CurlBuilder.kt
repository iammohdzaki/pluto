package com.mocklets.pluto.modules.network

internal fun RequestData.getCurl(): String {
    val curlCommandBuilder = StringBuilder("")
    curlCommandBuilder.append("cURL")
    curlCommandBuilder.append(" -X")
    curlCommandBuilder.append(" ${method.uppercase()}")
    for (headerName in headers) {
        curlCommandBuilder.append(headerPair(headerName.key, headerName.value))
    }

    body?.let {
        curlCommandBuilder.append(" -d '${it.flatten()}'")
    }
    curlCommandBuilder.append(" \"$url\"")
    curlCommandBuilder.append(" -L")
    return curlCommandBuilder.toString() // beautify(request.url.toString(), curlCommandBuilder.toString())
}

private fun headerPair(headerName: String, headerValue: String?): String {
    return " -H \"$headerName: $headerValue\""
}
