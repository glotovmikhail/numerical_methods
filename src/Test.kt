import java.io.File
import java.io.PrintWriter

fun main(args: Array<String>) {
    val dir = File("res/double")
    val n = 10
    val d1 = PrintWriter(File(dir, "gilbert.txt"))
    for (i in 0 until n) {
        for (j in 0 until n) {
            val a : Double = 1.0 / (i + j + 1.0)
            d1.print("$a ")
        }
        if (i != n - 1) d1.println()
    }
    d1.close()
}

