fun main(args : Array<String>) {
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

    println("Part 1: ${part1(input)}");
    println("Part 2: ${part2(input)}");
}

fun part1(input : List<String>) : Int {
    val jumpArr : Array<Int> = input.map(String::toInt).toTypedArray()
    
    var index = 0
    var steps = 0
    while (index >= 0 && index <= jumpArr.lastIndex) {
        val tmp = jumpArr[index]
        jumpArr[index]++

        index += tmp
        steps++
    }

    return steps
}

fun part2(input : List<String>) : Int {
    val jumpArr : Array<Int> = input.map(String::toInt).toTypedArray()

    var index = 0
    var steps = 0
    while (index >= 0 && index <= jumpArr.lastIndex) {
        val tmp = jumpArr[index]

        if (tmp >= 3) {
            jumpArr[index]--
        } else {
            jumpArr[index]++
        }

        index += tmp
        steps++
    }

    return steps
}