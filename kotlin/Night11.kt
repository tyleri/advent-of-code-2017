package night11

fun main(args: Array<String>) {
    val input = readLine() ?: throw IllegalArgumentException("No input")

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

fun getDirVals(dir: String) : Pair<Int, Int> {
    return when (dir) {
        "n" -> 0 to 1
        "s" -> 0 to -1
        "nw" -> -1 to 0
        "se" -> 1 to 0
        "ne" -> 1 to 1
        "sw" -> -1 to -1
        else -> throw IllegalArgumentException("Invalid direction")
    }
}

fun countSteps(x: Int, y: Int) : Int {
    var (currX, currY) = (x to y)
    var steps = 0

    while (currX != 0 || currY != 0) {
        if (currX > 0 && currY > 0) {
            currX--
            currY--
        } else if (currX < 0 && currY < 0) {
            currX++
            currY++
        } else if (currX > 0) {
            currX--
        } else if (currY > 0) {
            currY--
        } else if (currX < 0) {
            currX++
        } else if (currY < 0) {
            currY++
        } else {
            throw IllegalArgumentException("X and Y should not both be 0")
        }

        steps++
    }

    return steps
}

fun part1(input: String) : Int {
    var currX = 0
    var currY = 0

    input.split(",").forEach {
        val (dirValX, dirValY) = getDirVals(it)
        currX += dirValX
        currY += dirValY
    }

    return countSteps(currX, currY)
}

fun part2(input: String) : Int {
    var currX = 0
    var currY = 0
    var maxDist = 0

    input.split(",").forEach {
        val (dirValX, dirValY) = getDirVals(it)
        currX += dirValX
        currY += dirValY

        maxDist = Math.max(maxDist, countSteps(currX, currY))
    }

    return maxDist
}