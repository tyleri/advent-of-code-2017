fun main(args: Array<String>) {
    val genAStart = readLine()?.split(" ")?.last()?.toLong() ?: throw IllegalArgumentException("Invalid input")
    val genBStart = readLine()?.split(" ")?.last()?.toLong() ?: throw IllegalArgumentException("Invalid input")

    println("Part 1: ${part1(genAStart, genBStart)}")
    println("Part 2: ${part2(genAStart, genBStart)}")
}

fun part1(inputA: Long, inputB: Long) : Int {
    var genA = inputA
    var genB = inputB

    val genAFactor = 16807
    val genBFactor = 48271
    val divisor = 2147483647
    val lower16 = Math.pow(2.0, 16.0).toLong() - 1

    var count = 0

    for (i in 1..40_000_000) {
        genA = (genA * genAFactor) % divisor
        genB = (genB * genBFactor) % divisor

        if (genA and lower16 == genB and lower16) {
            count++
        }
    }

    return count
}

fun part2(inputA: Long, inputB: Long) : Int {
    var genA = inputA
    var genB = inputB

    val genAFactor = 16807
    val genBFactor = 48271
    val divisor = 2147483647
    val lower16 = Math.pow(2.0, 16.0).toLong() - 1

    var count = 0

    for (i in 1..5_000_000) {
        do {
            genA = (genA * genAFactor) % divisor
        } while (genA % 4 != 0L)

        do {
            genB = (genB * genBFactor) % divisor
        } while (genB % 8 != 0L)

        if (genA and lower16 == genB and lower16) {
            count++
        }
    }

    return count
}