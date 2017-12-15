package night13

import java.util.LinkedList

fun main(args: Array<String>) {
    var input = LinkedList<String>()

    while (true) {
        val line = readLine()
        if (line == null) {
            break
        }
        if (line != "") {
            input.add(line)
        }
    }

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

fun part1 (input: List<String>) : Int {
    return input
        .map {it.split(": ").map(String::toInt)}
        .fold(
            initial=0,
            operation={ acc, elt ->
                acc + if ( elt[0] % ((elt[1]-1)*2) == 0) elt[0] * elt[1] else 0
            }
        )
}

fun part2 (input: List<String>) : Int {
    val parsedInput = input.map {
        val (first, second) = it.split(": ").map(String::toInt)
        first to second
    }
    var counter = 0
    
    do {
        counter++
        val caught = parsedInput.any {(first, second) -> (first + counter) % ((second-1)*2) == 0}
    } while (caught)

    return counter
}