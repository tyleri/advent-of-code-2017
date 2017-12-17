fun main(args: Array<String>) {
    val input = readLine()?.toInt() ?: throw IllegalArgumentException("No input")

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

fun part1(input: Int) : Int {
    val buffer = ArrayList<Int>(2018)
    buffer.add(0)
    var currPos = 0

    for (i in 1..2017) {
        currPos = (currPos + input) % buffer.size + 1
        buffer.add(currPos, i)
    }

    return buffer.get(currPos + 1)
}

fun part2(input: Int) : Int {
    var currPos = 0
    var afterZero = 0

    for (i in 1..50_000_000) {
        currPos = (currPos + input) % i + 1

        if (currPos == 1) {
            afterZero = i
        }
    }

    return afterZero
}