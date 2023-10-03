import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.color.rgb
import org.openrndr.draw.CircleBatchBuilder
import org.openrndr.extra.noclear.NoClear
import org.openrndr.extra.noise.cubic
import org.openrndr.extra.noise.perlin
import org.openrndr.extra.noise.perlinQuintic
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.math.Vector2
import kotlin.math.*


fun main() = application {

    configure {

        width = 1000
        height = 1000

    }

    oliveProgram {
        backgroundColor = ColorRGBa.BLACK
//        drawer.rotate(360 + seconds)
        val clockRadius = 400.0

        val amount = 250
        val angle = 360.0 / amount

        var lastX = 0.0


        val colorsArrays = listOf(
            arrayOf(
                rgb("#191716"),
                rgb("#e6af2e"),
                rgb("#e0e2db"),
                rgb("#3d348b"),
                rgb("#beb7a4")
            ), arrayOf(
                ColorRGBa.BLACK,
                ColorRGBa.PINK,
                ColorRGBa.WHITE,
                ColorRGBa.RED,
                ColorRGBa(0.6, 0.3, 0.2, 0.4)
            ), arrayOf(
                rgb("#191d32"),
                rgb("#282f44"),
                rgb("#453a49"),
                rgb("#6d3b47"),
                rgb("#ba2c73")
            ), arrayOf(
                rgb("#331832"),
                rgb("#694d75"),
                rgb("#1b5299"),
                rgb("#9fc2cc"),
                rgb("#f1ecce")
            ), arrayOf(
                rgb("#41d3bd"),
                rgb("#fffff2"),
                rgb("#791e94"),
                rgb("#de6449"),
                rgb("#407899")
            ), arrayOf(
                rgb("#fb8b24"),
                rgb("#d90368"),
                rgb("#820263"),
                rgb("#291720"),
                rgb("#04a777")
            )
        )
//        val colorArray = org.openrndr.extra.noise.Random.pick(colorsArrays)
        val colorArray = colorsArrays[4]

        var t = seconds
        extend(NoClear()) {
            backdrop = {
                drawer.fill = colorArray[4].opacify(0.4)
//                drawer.circle(drawer.bounds.center, clockRadius)
            }
        }
        extend {

            @OptIn(ExperimentalStdlibApi::class)
            fun calculateCoordinates(amount: Int): List<Pair<Double, Double>> {
                return buildList {
                    for (i in 0 until amount) {

                    }
                }
            }
        }
        extend {
            val vertical = 0.05
            val horizontal = 0.5

            drawer.translate(drawer.bounds.center)
            for (i in 0 until amount) {
//                drawer.rotate(perlin(i, horizontal) + angle)
//                drawer.rotate(perlin(1, vertical) + angle)

//                val x1 = sin(t * vertical / i + t)
//                val x1 = sin(t * vertical * i + t)
//                val x1 = sin(t * cos(vertical) * i + t)
//                val x1 = t  / (sin(t) * i + t) //  <-----
//                val x1 = t  / (cos(t) * i + t)
//                val x1 = t  / (cos(t) * i / sin(t))
                val x1 = (cos(t) * i + t) / t //<----avocado
//                val x1 = (sin(t) * i + t) / t
//                val x1 = (sin(t) * i + t) / i +t

//                val x1 = t  + (sin(t) * i - t)
//                val x1 = tan(t * vertical  + t)
//                val x1 = perlin(i,-t * sin(vertical)  + t)
//                val x1 = perlin(( seconds).toInt(),-t * sin(vertical)  - i *2)
//                val x1 = cubic(( seconds).toInt(),-t * sin(vertical)  - i *2)
//                val x1 = cubic(( seconds).toInt(),-t * sin(vertical)  - i *2)


//                val x2 = tan(t * vertical + t)
                val x2 = tan(t * horizontal + t)
//                val x2 = cos(t * horizontal + t)
//                val x2 = (sin(t) * i + t) / t
                val xPos1 = (clockRadius * if (i % 2 == 0) cos(x1) else -cos(x1))
                val yPos1 = clockRadius * if (i % 2 == 1) -sin(x1) else sin(x1)

                val xPos2 = (clockRadius * if (i % 2 == 0) cos(x2) else -cos(x2))
                val yPos2 = clockRadius * if (i % 2 == 1) -sin(x2) else sin(x2)


                drawer.points {
                    drawer.rotate(amount * i.toDouble())
                    fill = colorArray[0]
//                    point(sin(seconds) * xPos1, cos(seconds) - yPos1) // globe
//                    point(cos(seconds) * xPos2, sin(seconds) -yPos2) // patterned globe
//                    point(0.0, yPos1 - i)
//                    point( yPos2 - i, yPos2 + i) // windings
//                    strokeWeight = sin(i.toDouble()) * 2
                    fill = colorArray[2]
//                    point(xPos1 + i, 0.0)
//                    point(yPos2 + i, yPos2 - i)
//                    point(0.0, sin(yPos1 / xPos1) / clockRadius - i * 5)
//                    point(0.0, sin(yPos1 / t) / clockRadius - i * 5)
//                    point(0.0, sin(yPos1 * t) * clockRadius ) // sunrays
//                    point(0.0, sin(yPos1 + t) * clockRadius - i) //paddlefolies

                    fill = colorArray[3]/*.opacify(0.2)*/
//                    point(0.0, sin(xPos1) * clockRadius + i)
//                    point(0.0, sin(yPos1 - xPos1) * clockRadius - i)
//                    point(0.0, sin(yPos1 - xPos1) * clockRadius - i)
//                    point(0.0, sin(yPos1 + xPos1) * clockRadius - i)
//                    point(0.0, sin(yPos1 - t) * clockRadius - i) // paddels
//                    point(0.0, sin(yPos1 + t) * clockRadius - i) //paddlefolies
                    point(0.0, tan(xPos1 / t*i) * clockRadius ) //growing cables
                    point(0.0, sin(yPos2 * t*i) * clockRadius ) //solar coins
//                    point(xPos1, tan(yPos1 * t/i) * clockRadius ) //black hole
//                    point(cos(xPos2 - yPos2) * clockRadius, tan(yPos1 * t/i)  ) //engraved coin
//                    point(cos(yPos1 * t/i) * clockRadius, tan(yPos1 * t/i)   ) //perforated disk
//                    point(100.0 , tan(yPos2 *i)   ) //black hole sun
//                    point(sin(xPos1) + xPos2 , atan(yPos2 *i)   ) //disk winding
//                    point(0.0, sin(t * yPos2) * clockRadius - t)
//                    point(0.0, sin(t / yPos2) * clockRadius - t)

//                    point(0.0, cos(lastX) * clockRadius - i) // triangle stripes


                }
//                lastX = xPos1
//                lastY = y
                drawer.stroke = null
//                drawer.fill = colorArray[1]/*.opacify(0.2)*/
                drawer.rotate(sin(t) * horizontal)
//                drawer.rotate(sin(t) * vertical)
                drawer.rotate(sin(t*i) * vertical)
            }

//            t += sin(seconds % 2)
//            t += ceil(sin(horizontal))
//            t += ceil(cos(vertical))
//            t += ceil(cos(horizontal))
            t += ceil(sin(vertical))
//            t += 1.0
//            t += seconds % 100
//            vertical -=1
        }
    }
}

