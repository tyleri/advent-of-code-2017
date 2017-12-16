package helper

fun reverseSubarr(arr: Array<Int>, start: Int, len: Int) {
    var startIdx = start
    var endIdx = startIdx + len - 1

    while (startIdx < endIdx) {
        val startIdxMod = startIdx % arr.size
        val endIdxMod = endIdx % arr.size

        // swap
        val tmp = arr[startIdxMod]
        arr[startIdxMod] = arr[endIdxMod]
        arr[endIdxMod] = tmp

        startIdx++
        endIdx--
    }
}

fun byteToHexStr(value: Int) : String {
    val char1 = when (value / 16) {
        in 0..9 -> (value / 16).toString()
        10 -> "a"
        11 -> "b"
        12 -> "c"
        13 -> "d"
        14 -> "e"
        15 -> "f"
        else -> throw IllegalArgumentException("Not a byte: $value")
    }
    val char2 = when (value % 16) {
        in 0..9 -> (value % 16).toString()
        10 -> "a"
        11 -> "b"
        12 -> "c"
        13 -> "d"
        14 -> "e"
        15 -> "f"
        else -> throw IllegalArgumentException("Not a byte: $value")
    }

    return char1 + char2
}

fun knotHash(input: String) : String {
    val inputList = input.map(Char::toInt).plus(arrayOf(17, 31, 73, 47, 23))
    val knotsArr = Array(size=256, init={index -> index})
    var currentPos = 0
    var skipSize = 0

    for (i in 1..64) {
        inputList.forEach {
            reverseSubarr(knotsArr, currentPos, it)
            currentPos += it + skipSize
            skipSize++
        }
    }

    val denseHash = knotsArr.asIterable().chunked( 16, {it.reduce(Int::xor)} )

    return denseHash.map(::byteToHexStr).joinToString(separator="")
}