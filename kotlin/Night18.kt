import java.util.LinkedList

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
    println("Part 2: ${part2(input)}")
}

sealed class Instruction
data class Snd(val amount: Long) : Instruction()
data class Set(val register: Char, val amount: Long) : Instruction()
data class Add(val register: Char, val amount: Long) : Instruction()
data class Mul(val register: Char, val amount: Long) : Instruction()
data class Mod(val register: Char, val amount: Long) : Instruction()
data class Rcv(val register: Char) : Instruction()
data class Jgz(val cmp: Long, val amount: Long) : Instruction()

fun parseInstruction(line: String, registers: Map<Char, Long>) : Instruction {
    val splitLine = line.split(" ")

    return when (splitLine[0]) {
        "snd" -> {
            Snd(
                if (splitLine[1].length == 1 && splitLine[1][0] in 'a'..'z') {
                    registers.getOrDefault(splitLine[1][0], 0L)
                } else {
                    splitLine[1].toLong()
                }
            )
        }
        "set" -> {
            Set(
                splitLine[1][0],
                if (splitLine[2].length == 1 && splitLine[2][0] in 'a'..'z') {
                    registers.getOrDefault(splitLine[2][0], 0L)
                } else {
                    splitLine[2].toLong()
                }
            )
        }
        "add" -> Add(
            splitLine[1][0],
            if (splitLine[2].length == 1 && splitLine[2][0] in 'a'..'z') {
                registers.getOrDefault(splitLine[2][0], 0L)
            } else {
                splitLine[2].toLong()
            }
        )
        "mul" -> Mul(
            splitLine[1][0],
            if (splitLine[2].length == 1 && splitLine[2][0] in 'a'..'z') {
                registers.getOrDefault(splitLine[2][0], 0L)
            } else {
                splitLine[2].toLong()
            }
        )
        "mod" -> Mod(
            splitLine[1][0],
            if (splitLine[2].length == 1 && splitLine[2][0] in 'a'..'z') {
                registers.getOrDefault(splitLine[2][0], 0L)
            } else {
                splitLine[2].toLong()
            }
        )
        "rcv" -> Rcv(splitLine[1][0])
        "jgz" -> Jgz(
            if (splitLine[1].length == 1 && splitLine[1][0] in 'a'..'z') {
                registers.getOrDefault(splitLine[1][0], 0L)
            } else {
                splitLine[1].toLong()
            },
            if (splitLine[2].length == 1 && splitLine[2][0] in 'a'..'z') {
                registers.getOrDefault(splitLine[2][0], 0L)
            } else {
                splitLine[2].toLong()
            }
        )
        else  -> throw IllegalArgumentException("Bad instruction: $line")
    }
}

fun part1(input: List<String>) : Long {
    var registers = mutableMapOf<Char, Long>()
    var lastSound = 0L
    var i = 0
    while (i <= input.lastIndex) {
        val curr = parseInstruction(input[i], registers)
        when (curr) {
            is Snd -> lastSound = curr.amount
            is Set -> registers.put(curr.register, curr.amount)
            is Add -> registers.put(curr.register, registers.getOrDefault(curr.register, 0) + curr.amount)
            is Mul -> registers.put(curr.register, registers.getOrDefault(curr.register, 0) * curr.amount)
            is Mod -> registers.put(curr.register, registers.getOrDefault(curr.register, 0) % curr.amount)
            is Rcv -> if (registers.getOrDefault(curr.register, 0L) != 0L) return lastSound
            is Jgz -> if (curr.cmp > 0) i += curr.amount.toInt() - 1
        }
        i++
    }
    return 0 // should never get here
}

data class ProgState(
    var pc: Int,
    var numSent: Int,
    val sndQueue: LinkedList<Long>,
    val rcvQueue: LinkedList<Long>,
    val registers: MutableMap<Char, Long>
)

fun part2(input: List<String>) : Int {
    val sndQueues = arrayOf(LinkedList<Long>(), LinkedList<Long>())
    val states = arrayOf(
        ProgState(0, 0, sndQueues[0], sndQueues[1], mutableMapOf('p' to 0L) ),
        ProgState(0, 0, sndQueues[1], sndQueues[0], mutableMapOf('p' to 1L) )
    )

    fun runProgram(progNum: Int) : Int {
        val state = states[progNum]
        var instructionsExec = 0
        while (state.pc <= input.lastIndex) {
            val curr = parseInstruction(input[state.pc], state.registers)
            when (curr) {
                is Snd -> {
                    state.sndQueue.offer( curr.amount )
                    state.numSent++
                }
                is Rcv -> {
                    if (state.rcvQueue.isEmpty()) {
                        return instructionsExec
                    }
                    val receivedVal = state.rcvQueue.poll()
                    state.registers.put(curr.register, receivedVal)
                }
                is Set -> state.registers.put(curr.register, curr.amount)
                is Add -> state.registers.put(curr.register, state.registers.getOrDefault(curr.register, 0) + curr.amount)
                is Mul -> state.registers.put(curr.register, state.registers.getOrDefault(curr.register, 0) * curr.amount)
                is Mod -> state.registers.put(curr.register, state.registers.getOrDefault(curr.register, 0) % curr.amount)
                is Jgz -> if (curr.cmp > 0) state.pc += curr.amount.toInt() - 1
            }
            state.pc++
            instructionsExec++
        }
        return instructionsExec
    }

    while (runProgram(0) > 0 && runProgram(1) > 0) {}

    return states[1].numSent
}