fun main(args: Array<String>) {
    val input = readLine() ?: throw IllegalArgumentException("No input given")

    println("Part 1: ${part1(input)}")
}

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

fun part1(input: String) : Int {
    val inputList = input.split(',').map(String::toInt)
    val knotsArr = Array(size=256, init={index -> index})
    var currentPos = 0
    var skipSize = 0

    inputList.forEach {
        reverseSubarr(knotsArr, currentPos, it)
        currentPos += it + skipSize
        skipSize++
    }

    return knotsArr[0] * knotsArr[1]
}