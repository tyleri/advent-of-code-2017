fun main(args : Array<String>) {
    val input = readLine() ?: throw IllegalArgumentException("No input")

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

fun part1(input: String) : Int {
    var count = 0

    for (i in input.indices) {
        if (input[i] == input[(i+1) % input.length]) {
            count += input.substring(i,i+1).toInt()
        }
    }

    return count
}

fun part2(input: String) : Int {
    var count = 0

    for (i in input.indices) {
        if (input[i] == input[(i + input.length / 2) % input.length]) {
            count += input.substring(i,i+1).toInt()
        }
    }

    return count
}