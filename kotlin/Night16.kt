fun main(args: Array<String>) {
    val input = readLine() ?: throw IllegalArgumentException("No input")

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
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

fun goDance(arr: Array<Char>, danceMovesStr: String) {
    val danceMoves = danceMovesStr.split(",").map(::parseDanceMove)

    for (move in danceMoves) {
        when (move) {
            is Spin -> {
                arr.takeLast(move.amount).plus(arr.dropLast(move.amount)).forEachIndexed {
                    index, elt -> arr[index] = elt
                }
            }
            is Exchange -> {
                val tmp = arr[move.pos1]
                arr[move.pos1] = arr[move.pos2]
                arr[move.pos2] = tmp
            }
            is Partner -> {
                val pos1 = arr.indexOf(move.prog1)
                val pos2 = arr.indexOf(move.prog2)
                val tmp = arr[pos1]
                arr[pos1] = arr[pos2]
                arr[pos2] = tmp
            }
        }
    }
}

fun part1(input: String) : String {
    var progOrder = Array(16, {'a' + it})

    goDance(progOrder, input)
    return progOrder.joinToString(separator="")
}

fun part2(input: String) : String {
    var progOrder = Array(16, {'a' + it})
    var mapping = mutableMapOf(progOrder.joinToString(separator="") to 1_000_000_000)

    var i = 1_000_000_000
    while (i > 0) {
        i--

        goDance(progOrder, input)
        val joined = progOrder.joinToString(separator="")
        mapping.get(joined)?.let {
            i %= (it - i)
        }
        mapping.put(joined, i)
    }

    return progOrder.joinToString(separator="")
}