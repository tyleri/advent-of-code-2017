fun main(args: Array<String>) {
    val input = readLine() ?: throw IllegalArgumentException("Wrong arguments")

    println("Part 1: ${part1(input)}")
}

sealed class Bunch
data class Group(val score: Int) : Bunch()
object Garbage : Bunch()
object Cancel : Bunch()

enum class Token {
    OPEN_GROUP, CLOSE_GROUP, OPEN_GARBAGE, CLOSE_GARBAGE, CANCEL, OTHER
}

fun part1(input: String) : Int {
    var queue = listOf<Bunch>()
    var totalScore = 0

    fun parseChar(char: Char) : Token = when (char) {
        '{' -> Token.OPEN_GROUP
        '}' -> Token.CLOSE_GROUP
        '<' -> Token.OPEN_GARBAGE
        '>' -> Token.CLOSE_GARBAGE
        '!' -> Token.CANCEL
        else -> Token.OTHER
    }

    fun checkQueue(char: Char) {
        val lastElt = queue.lastOrNull()
        when (lastElt) {
            Cancel -> queue = queue.dropLast(1)
            Garbage -> when (parseChar(char)) {
                Token.CANCEL -> queue = queue.plus( Cancel )
                Token.CLOSE_GARBAGE -> queue = queue.dropLast(1)
                else -> {}
            }
            is Group -> when (parseChar(char)) {
                Token.CANCEL -> queue = queue.plus( Cancel )
                Token.CLOSE_GROUP -> {
                    totalScore += lastElt.score
                    queue = queue.dropLast(1)
                }
                Token.OPEN_GROUP -> queue = queue.plus( Group(lastElt.score + 1) )
                Token.OPEN_GARBAGE -> queue = queue.plus( Garbage )
                else -> {}
            }
            null -> when (parseChar(char)) {
                Token.CANCEL -> queue = queue.plus( Cancel )
                Token.OPEN_GROUP -> queue = queue.plus( Group(1) )
                Token.OPEN_GARBAGE -> queue = queue.plus( Garbage )
                else -> {}
            }
        }
    }

    for (char in input) {
        checkQueue(char)
    }

    // println(queue)
    return totalScore
}