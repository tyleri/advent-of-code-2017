package night6

fun main(args : Array<String>) {
    val input = readLine()

    if (input != null) {
        println("Part 1: ${part1(input)}")
        println("Part 2: ${part2(input)}")
    }
}

fun intArrToStr(arr : Array<Int>) : String = arr.fold( "", {acc, elt -> acc + elt} )

fun part1(input : String) : Int? {
    var banks : Array<Int> = input.split("\t").map {it.toInt()}.toTypedArray()
    val set = HashSet<String>()
    var times = 0

    while (!set.contains(intArrToStr(banks))) {
        set.add(intArrToStr(banks))

        // get max
        var max = banks[0]
        var maxIndex = 0

        banks.forEachIndexed { index, value ->
            if (value > max) {
                max = value
                maxIndex = index
            }
        }

        banks[maxIndex] = 0

        // distribute
        var toDistribute = max
        var distrIndex = maxIndex
        while (toDistribute > 0) {
            distrIndex = (distrIndex + 1) % banks.size
            banks[distrIndex]++
            toDistribute--
        }

        times++
    }

    return times
}

fun part2(input : String) : Int? {
    var banks : Array<Int> = input.split("\t").map {it.toInt()}.toTypedArray()
    var step = 0

    val map = HashMap<String, Int>()

    while (!map.containsKey(intArrToStr(banks))) {
        map.put(intArrToStr(banks), step)

        // get max
        var max = banks[0]
        var maxIndex = 0

        banks.forEachIndexed { index, value ->
            if (value > max) {
                max = value
                maxIndex = index
            }
        }

        banks[maxIndex] = 0

        // distribute
        var toDistribute = max
        var distrIndex = maxIndex
        while (toDistribute > 0) {
            distrIndex = (distrIndex + 1) % banks.size
            banks[distrIndex]++
            toDistribute--
        }

        step++
    }

    val mappedVal = map.get(intArrToStr(banks))
    if (mappedVal != null) {
        return step - mappedVal
    } else {
        return null
    }
}