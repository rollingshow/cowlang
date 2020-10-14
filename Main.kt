import java.io.File

fun readCOW(path: String): List<String> {
    val file = File(path)
    val lines = file.readLines()
    val result: String = lines.joinToString(" ")
    return result.split(" ")
}

fun getLoopBlocks(source: List<String>): HashMap<Int, Int> {
    val blocks = HashMap<Int, Int>()
    val stack = mutableListOf<Int>()
    for ((i, char) in source.withIndex()) {
        if (char == "MOO") {
            stack.add(i)
        }
        if (char == "moo") {
            blocks[i] = stack[stack.lastIndex]
            blocks[stack.removeAt(stack.lastIndex)] = i
        }
    }
    return blocks
}

fun eval(source: List<String>) {
    val buffer = Array<Char>(200) { _ -> (0).toChar() }
    var ptr = 0
    var i = 0
    var reg: Char? = null
    val blocks = getLoopBlocks(source)
    while (i < source.size) {
        when (source[i]) {
            "MOO" -> {
                if (buffer[ptr] == (0).toChar()) {
                    i = blocks[i]!!
                }
            }
            "mOo" -> ptr -= 1
            "moO" -> ptr += 1
            "mOO" -> {
            }
            "Moo" -> {
                if (buffer[ptr] == 0.toChar()) {
                    buffer[ptr] = readLine()?.toCharArray()?.get(0)!!
                } else {
                    print(buffer[ptr])
                }
            }
            "MOo" -> buffer[ptr] = buffer[ptr] - 1
            "MoO" -> buffer[ptr] = buffer[ptr] + 1
            "moo" -> {
                if (buffer[ptr] != 0.toChar()) {
                    i = blocks[i]!!
                }
            }
            "OOO" -> {
                buffer[ptr] = 0.toChar()
            }
            "MMM" -> {
                if (reg == null) {
                    reg = buffer[ptr]
                } else {
                    buffer[ptr] = reg
                    reg = null
                }
            }
            "OOM" -> print(buffer[ptr].toInt())
            "oom" -> {
                print(" write ")
                buffer[ptr] = readLine()?.toCharArray()?.get(0)!!
            }
        }
        i += 1

    }
}

fun main(args: Array<String>) {
    val source = readCOW("C://COW//fib.cow")
    eval(source)
}