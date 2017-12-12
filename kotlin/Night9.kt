import java.util.LinkedList

fun main(args: Array<String>) {
    val input = readLine() ?: throw IllegalArgumentException("No input")

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

sealed class Bunch
data class Group(val score: Int) : Bunch()
object Garbage : Bunch()
object Cancel : Bunch()

enum class Token {
    OPEN_GROUP, CLOSE_GROUP, OPEN_GARBAGE, CLOSE_GARBAGE, CANCEL, OTHER
}

fun part1(input: String) : Int {
    var queue = LinkedList<Bunch>()
    var totalScore = 0

    fun parseChar(char: Char) : Token = when (char) {
        '{' -> Token.OPEN_GROUP
        '}' -> Token.CLOSE_GROUP
        '<' -> Token.OPEN_GARBAGE
        '>' -> Token.CLOSE_GARBAGE
        '!' -> Token.CANCEL
        else -> Token.OTHER
    }

    fun processChar(char: Char) {
        val topElt = queue.peek()
        when (topElt) {
            Cancel  -> queue.pop()
            Garbage -> when (parseChar(char)) {
                Token.CANCEL        -> queue.push( Cancel )
                Token.CLOSE_GARBAGE -> queue.pop()
                else                -> {}
            }
            is Group -> when (parseChar(char)) {
                Token.CANCEL        -> queue.push( Cancel )
                Token.CLOSE_GROUP   -> {
                    totalScore += topElt.score
                    queue.pop()
                }
                Token.OPEN_GROUP    -> queue.push( Group(topElt.score + 1) )
                Token.OPEN_GARBAGE  -> queue.push( Garbage )
                else -> {}
            }
            null -> when (parseChar(char)) {
                Token.CANCEL        -> queue.push( Cancel )
                Token.OPEN_GROUP    -> queue.push( Group(1) )
                Token.OPEN_GARBAGE  -> queue.push( Garbage )
                else -> {}
            }
        }
    }

    for (char in input) {
        processChar(char)
    }

    return totalScore
}

fun part2(input: String) : Int {
    var queue = LinkedList<Bunch>()
    var garbageChars = 0

    fun parseChar(char: Char) : Token = when (char) {
        '{' -> Token.OPEN_GROUP
        '}' -> Token.CLOSE_GROUP
        '<' -> Token.OPEN_GARBAGE
        '>' -> Token.CLOSE_GARBAGE
        '!' -> Token.CANCEL
        else -> Token.OTHER
    }

    fun processChar(char: Char) {
        val topElt = queue.peek()
        when (topElt) {
            Cancel  -> queue.pop()
            Garbage -> when (parseChar(char)) {
                Token.CANCEL        -> queue.push( Cancel )
                Token.CLOSE_GARBAGE -> queue.pop()
                else                -> garbageChars++
            }
            is Group -> when (parseChar(char)) {
                Token.CANCEL        -> queue.push( Cancel )
                Token.CLOSE_GROUP   -> queue.pop()
                Token.OPEN_GROUP    -> queue.push( Group(topElt.score + 1) )
                Token.OPEN_GARBAGE  -> queue.push( Garbage )
                else -> {}
            }
            null -> when (parseChar(char)) {
                Token.CANCEL        -> queue.push( Cancel )
                Token.OPEN_GROUP    -> queue.push( Group(1) )
                Token.OPEN_GARBAGE  -> queue.push( Garbage )
                else -> {}
            }
        }
    }

    for (char in input) {
        processChar(char)
    }

    return garbageChars
}