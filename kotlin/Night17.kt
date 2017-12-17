fun main(args: Array<String>) {
    val input = readLine()?.toInt() ?: throw IllegalArgumentException("No input")

    println("Part 1: ${part1(input)}")
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