package hw1.test

import hw1.*
import hw1.solvers.ConjugateGradientSolver
import hw1.solvers.GaussianSolver
import hw1.solvers.JacobiSolver
import hw1.solvers.SeidelSolver
import java.io.File

private fun solve(s: String, f: () -> DoubleMatrix) {
    try {
        print("$s: ")
        printResult(f())
    } catch (e: Exception) {
        println(s + e.toString())
    }
}

private fun solveAll(a: DoubleMatrix, b: DoubleMatrix, c: DoubleMatrix) {
    solve("G") { GaussianSolver.getSolve(a, b) }
//    solve("J") { JacobiSolver.getSolve(a, b, c) }
//    solve("S") { SeidelSolver.getSolve(a, b, c) }
    solve("C") { ConjugateGradientSolver.getSolve(a, b, c, 1e-6) }
}

private fun getMatrix(file: String): DoubleMatrix = MatrixReader.getDoubleMatrix(File("res/double/$file.txt"))

private fun printResult(matrix: DoubleMatrix) {
    for (i in 0 until matrix.size().first) {
        print(matrix.get(i, 0).toString() + " ")
    }
    println()
}

fun main(args: Array<String>) {
    val n = 3
    double(n)
}

fun double(n: Int) {
    val b = Generator.generateRandom(n, 1)
    val c = Generator.generateRandom(n, 1)
    println("B matrix of size $b")
    println("C matrix of size $c")
    val sym = Generator.generateSymmetric(n)
    println("Symmetric matrix of size $sym")
    solveAll(sym, b, c)
    (0 until n).forEach { sym.set(it, it, 1e-6) }
    println("Bad symmetric matrix of size $sym")
    solveAll(sym, b, c)
    val diag = Generator.generateDiagonallyDominant(n)
    println("Diagonally Dominant matrix of size $diag")
    solveAll(diag, b, c)
    val random = Generator.generateRandom(n, n)
    println("Random matrix of size $random")
    solveAll(random, b, c)
    val bad = Generator.generateBad(n)
    println("Bad matrix of size $bad")
    solveAll(bad, b, c)
}
