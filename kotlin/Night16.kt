fun main(args: Array<String>) {
    val input = readLine() ?: throw IllegalArgumentException("No input")

    println("Part 1: ${part1(input)}")
}

sealed class DanceMove
data class Spin(val amount: Int) : DanceMove()
data class Exchange(val pos1: Int, val pos2: Int) : DanceMove()
data class Partner(val prog1: Char, val prog2: Char) : DanceMove()

fun parseDanceMove(input: String) : DanceMove {
    return when (input[0]) {
        's' -> Spin(input.substring(1).toInt())
        'x' -> {
            val params = input.substring(1).split("/").map(String::toInt)
            Exchange(params[0], params[1])
        }
        'p' -> Partner(input[1], input[3])
        else -> throw IllegalArgumentException("Bad input: $input")
    }
}

fun part1(input: String) : String {
    val danceMoves = input.split(",").map(::parseDanceMove)
    var progOrder = Array(16, {'a' + it})

    for (move in danceMoves) {
        when (move) {
            is Spin -> progOrder = ( progOrder.takeLast(move.amount).plus(progOrder.dropLast(move.amount)) ).toTypedArray()
            is Exchange -> {
                val tmp = progOrder[move.pos1]
                progOrder[move.pos1] = progOrder[move.pos2]
                progOrder[move.pos2] = tmp
            }
            is Partner -> {
                val pos1 = progOrder.indexOf(move.prog1)
                val pos2 = progOrder.indexOf(move.prog2)
                val tmp = progOrder[pos1]
                progOrder[pos1] = progOrder[pos2]
                progOrder[pos2] = tmp
            }
        }
    }

    return progOrder.joinToString(separator="")
}