import helper.reverseSubarr
import helper.knotHash

fun main(args: Array<String>) {
    val input = readLine() ?: throw IllegalArgumentException("No input given")

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
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

fun part2(input: String) : String {
    return knotHash(input)
}