package hw1.solvers

import hw1.*

class SeidelSolver {
    companion object {
        fun getSolve(a0: DoubleMatrix, b: DoubleMatrix, x0: DoubleMatrix): DoubleMatrix = getSolve(a0, b, x0, 1e-6)

        fun getSolve(a0: DoubleMatrix, b: DoubleMatrix, x0: DoubleMatrix, eps: Double): DoubleMatrix {
            val n = a0.size().first
            val l = a0.clone()
            val e = DoubleMatrix(n, n)
            for (i in 0 until n) {
                for (j in i + 1 until n) {
                    l.set(i, j, 0.0)
                }
                e.set(i, i, 1.0)
            }
            val r = a0 - l
            val q = ConditionNumber.getNorm((e - l).invert() * r)
            var result = x0
            var nResult = DoubleMatrix(n, 1)
            if (q > 1) throw NoSolveException("q is greater than 1")
            while (true) {
                for (i in 0 until n) {
                    val s = (0 until i).sumByDouble { a0.get(i, it) * nResult.get(it, 0) } +
                            (i + 1 until n).sumByDouble { a0.get(i, it) * result.get(it, 0) }
                    nResult.set(i, 0, (b.get(i, 0) - s) / a0.get(i, i))
                }
                val cond = ConditionNumber.getNorm(result - nResult)
                if (cond < eps) break
                result = nResult
                nResult = DoubleMatrix(n, 1)
            }
            return nResult
        }
    }

}