package hw1.solvers

import hw1.*

class JacobiSolver {
    companion object {
        fun getSolve(a0: DoubleMatrix, b: DoubleMatrix, x0: DoubleMatrix): DoubleMatrix = getSolve(a0, b, x0, 1e-6)

        fun getSolve(a0: DoubleMatrix, b: DoubleMatrix, x0: DoubleMatrix, e: Double): DoubleMatrix {
            val n = a0.size().first
            val r = a0.clone()
            for (i in 0 until n) {
                r.set(i, i, 0.0)
            }
            val d = a0 - r
            val q = ConditionNumber.getNorm(d.invert() * r)
            var result = x0
            var nResult = DoubleMatrix(n, 1)
            if (q > 1) throw NoSolveException("q is greater than 1")
            while (true) {
                for (i in 0 until n) {
                    val s = (0 until n)
                            .filter { i != it }
                            .sumByDouble { a0.get(i, it) * result.get(it, 0) }
                    nResult.set(i, 0, (b.get(i, 0) - s) / a0.get(i, i))
                }
                val cond = ConditionNumber.getNorm(result - nResult)
                if (cond / (1 - q) < e) break
                result = nResult
                nResult = DoubleMatrix(n, 1)
            }
            return nResult
        }
    }
}