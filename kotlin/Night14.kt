package night14

import night10.part2 as knotHash

fun main(args: Array<String>) {
    val input = readLine() ?: throw IllegalArgumentException("No input")

    println("Part 1: ${part1(input)}")
}

fun hexCountOnes(char: Char) : Int {
    var hexNum = when (char) {
        in '0'..'9' -> char - '0'
        'a' -> 10
        'b' -> 11
        'c' -> 12
        'd' -> 13
        'e' -> 14
        'f' -> 15
        else -> throw IllegalArgumentException("Invalid hex character")
    }

    var count = 0
    while (hexNum != 0) {
        if (hexNum % 2 == 1) {
            count++
        }
        hexNum = hexNum ushr 1
    }

    return count
}

fun part1(input: String) : Int {
    var sumUsed = 0

    for (i in 0..127) {
        val hash = knotHash("$input-$i")
        sumUsed += hash.fold(0, {acc, char -> acc + hexCountOnes(char)})
    }
    
    return sumUsed
}