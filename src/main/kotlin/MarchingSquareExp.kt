import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.noise.gaussian
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.math.Vector2
import org.openrndr.math.map


fun main() = application {
    configure {
        width = 800
        height = 800
    }




    oliveProgram {
        val scale = 0.0001
        val dim = 40
        extend {
            val weights = mutableListOf<MutableList<Int>>()
            for (y in 0 until height step dim) {
                val list = mutableListOf<Int>()
                weights.add(list)
                for (x in 0 until width step dim) {
                    val simplex = gaussian(0.3, x * seconds * scale)
                    val pointWeight = map(-10.0, 10.0, 0.0, 1.0, simplex)
                    var w: Int = if (pointWeight > 0.5) 1 else 0
                    list.add(w)
                }
            }
            println(weights)

//            drawer.clear(ColorRGBa.WHITE)
            drawer.pushTransforms()
            weights.forEachIndexed { y, list ->
                val y0 = y.toDouble() * dim

//                drawer.translate(0.0, y0)
                list.forEachIndexed { x, weight ->
                    val x0 = x.toDouble() * dim
                    println("y = $y0 and x = $x0")
//                    drawer.translate(x0, 0.0)
                    val halfDim = dim / 2.0
//                    when (weight) {
//                        1 -> drawer.stroke = ColorRGBa.RED
//                        0 -> drawer.stroke = ColorRGBa.GREEN
//                    }
                    drawer.strokeWeight = 1.0
//                    drawer.circle(x0, y0, 10.0)
                    val zero = if (y + 1 < list.size - 1) weights[y][x] else 0
                    val one =
                        if (y + 1 < list.size - 1) weights[y][if (x + 1 < list.size - 1) x else 0] else 0
                    val two = if (x + 1 < list.size - 1) weights[y][x] else 0
                    val three = weights[y][x]
                    val binary = "$zero$one$two$three"

                    val config = Integer.parseInt(binary, 2)
                    drawer.stroke = ColorRGBa.WHITE
//                    https://en.wikipedia.org/wiki/Marching_squares
                    when (config) {
//                        0, 15 -> println("do nothing")
                        1, 14 -> drawer.lineSegment(
                            Vector2(x0, y0 + halfDim),
                            Vector2(x0 + halfDim, y0 + dim)
                        )
                        2 -> drawer.lineSegment(
                            Vector2(x0 + halfDim, y0 + dim),
                            Vector2(x0 + dim, y0 + halfDim)
                        )
                        3 -> drawer.lineSegment(
                            Vector2(x0, y0 + halfDim),
                            Vector2(x0 + dim, y0 + halfDim)
                        )
                        4, 11 -> drawer.lineSegment(
                            Vector2(x0 + halfDim, y0),
                            Vector2(x0 + dim, y0 + halfDim)
                        )
                        5, 13 -> {
                            drawer.lineSegment(
                                Vector2(x0, y0 + halfDim),
                                Vector2(x0 + halfDim, y0)
                            )
                            drawer.lineSegment(
                                Vector2(x0 + halfDim, y0 + dim),
                                Vector2(x0 + dim, y0 + halfDim)
                            )
                        }
                        6, 9 -> drawer.lineSegment(
                            Vector2(x0 + halfDim, y0),
                            Vector2(x0 + halfDim, y0 + dim)
                        )
                        7, 8 -> drawer.lineSegment(
                            Vector2(x0, y0 + halfDim),
                            Vector2(x0 + halfDim, y0)
                        )
                        10 -> {
                            drawer.lineSegment(
                                Vector2(x0, y0 + halfDim),
                                Vector2(x0 + halfDim, y0 + dim)
                            )
                            drawer.lineSegment(
                                Vector2(x0 + halfDim, y0),
                                Vector2(x0 + dim, y0 + halfDim)
                            )
                        }
                        12 -> drawer.lineSegment(
                            Vector2(x0, y0 + halfDim),
                            Vector2(x0 + dim, y0 + halfDim)
                        )
                    }
                }
            }
            drawer.popTransforms()


//            val scale = 0.0005
//            for (y in 16 until height step 32) {
//                for (x in 16 until width step 32) {
//
//                    val simplex = simplex(100, x * seconds * scale, y * seconds * scale)
//                    val radius = simplex * 10.0 + 10.0
//                    val color = ColorHSLa(map(0.0, 1.0, 0.0, 70.0, simplex), sin(simplex), 0.5)
//                    drawer.fill = color.toRGBa()
//                    drawer.stroke = null
//                    drawer.circle(x * 1.0, y * 1.0, radius)
//                }
//            }
        }

    }
}

