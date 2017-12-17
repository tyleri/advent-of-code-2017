fun main(args : Array<String>) {
    val input = readLine()?.toInt() ?: throw IllegalArgumentException("No input")

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
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

fun part2(input: Int) : Int {
    val mapping = mutableMapOf( Pair(Pair(0,0), 1) )
    val sequence = generateSequence(
        {
            val up = Pair(0, 1)
            val down = Pair(0, -1)
            val left = Pair(-1, 0)
            val right = Pair(1, 0)
            var currDir = right
            var totalLength = 1
            var timesLeft = totalLength

            {
                if (timesLeft == 0) {
                    currDir = when (currDir) {
                        right -> up
                        up    -> left
                        left  -> down
                        down  -> right
                        else  -> throw IllegalArgumentException("invalid direction")
                    }

                    if (currDir == left || currDir == right) {
                        totalLength++
                    }

                    timesLeft = totalLength
                }

                timesLeft--
                currDir
            }
        }()
    )

    var (x, y) = Pair(0, 0)
    for ((xDir, yDir) in sequence) {
        x += xDir
        y += yDir

        val surroundings = listOf(
            x+1 to y,
            x+1 to y-1,
            x to y-1,
            x-1 to y-1,
            x-1 to y,
            x-1 to y+1,
            x to y+1,
            x+1 to y+1
        )
        
        val squareVal = surroundings.map {mapping.getOrDefault(it, 0)}.reduce(Int::plus)

        mapping.put(x to y, squareVal)

        if (squareVal > input) {
            return squareVal
        }
    }

    return 0 // will never get here
}