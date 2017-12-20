fun main(args: Array<String>) {
    val input = mutableListOf<String>()

    while (true) {
        val line = readLine()

        if (line == null) {
            break
        } else if (line == "") {
            continue
        }

        input.add(line)
    }

    println("Part 1: ${part1(input)}")
    // println("Part 2: ${part2(input)}")
}

sealed class Space
object UpDown : Space()
object LeftRight : Space()
object Intersection : Space()
data class Letter(val letter: Char) : Space()
object Empty : Space()

interface Dir {
    val x: Int
    val y: Int
    val left: Dir
    val right: Dir
}
object Up : Dir {
    override val x: Int = 0
    override val y: Int = -1
    override val left: Dir = Left
    override val right: Dir = Right
}
object Down : Dir {
    override val x: Int = 0
    override val y: Int = 1
    override val left: Dir = Right
    override val right: Dir = Left
}
object Left : Dir {
    override val x: Int = -1
    override val y: Int = 0
    override val left: Dir = Down
    override val right: Dir = Up
}
object Right : Dir {
    override val x: Int = 1
    override val y: Int = 0
    override val left: Dir = Up
    override val right: Dir = Down
}

fun parseSpace(char: Char) : Space {
    return when (char) {
        '|' -> UpDown
        '-' -> LeftRight
        '+' -> Intersection
        ' ' -> Empty
        in 'A'..'Z' -> Letter(char)
        else -> throw IllegalArgumentException("Invalid character: $char")
    }
}

fun part1(input: List<String>) : String {
    val pathMap = input.map { it.map(::parseSpace) }

    tailrec fun move(x: Int, y: Int, facing: Dir = Down, acc: String = "") : String {
        val currSpace = pathMap.getOrNull(y)?.getOrNull(x) ?: Empty

        return when (currSpace) {
            is Empty -> acc
            is Letter -> move(x + facing.x, y + facing.y, facing, acc + currSpace.letter)
            is UpDown, is LeftRight -> move(x + facing.x, y + facing.y, facing, acc)
            is Intersection -> {
                val nextSpace = pathMap.getOrNull(y + facing.y)?.getOrNull(x + facing.x) ?: Empty
                val leftSpace = pathMap.getOrNull(y + facing.left.y)?.getOrNull(x + facing.left.x) ?: Empty
                val rightSpace = pathMap.getOrNull(y + facing.right.y)?.getOrNull(x + facing.right.x) ?: Empty

                if (nextSpace !is Empty) {
                    move(x + facing.x, y + facing.y, facing, acc)
                } else if (leftSpace !is Empty) {
                    move(x + facing.left.x, y + facing.left.y, facing.left, acc)
                } else if (rightSpace !is Empty) {
                    move(x + facing.right.x, y + facing.right.y, facing.right, acc)
                } else {
                    acc
                }
            }
        }
    }

    val startX = pathMap[0].indexOf(UpDown)

    return move(startX, 0)
}