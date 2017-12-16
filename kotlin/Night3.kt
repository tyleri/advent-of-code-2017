fun main(args : Array<String>) {
    val input = readLine()?.toInt() ?: throw IllegalArgumentException("No input")

    println("Part 1: ${part1(input)}")
    // println("Part 2: ${part2(input)}")
}

fun part1(input : Int) : Int {
    if (input == 1) {
        return 0
    }

    var i = 1
    while (i*i < input) {
        i += 2
    }

    val layers_deep : Int = i / 2

    val place_on_side : Int = ( input - (i - 2) * (i - 2) - 1 ) % (i - 1)

    return layers_deep + Math.abs(place_on_side - (layers_deep - 1))
}