import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.color.rgb
import org.openrndr.draw.CircleBatchBuilder
import org.openrndr.draw.Drawer
import org.openrndr.extra.noclear.NoClear
import org.openrndr.extra.noise.Random
import org.openrndr.extra.noise.cubic
import org.openrndr.extra.noise.perlin
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.math.Vector2
import kotlin.math.*


class FanParticle(pos: Vector2) {
    var position = pos
    var targ = position
    var velocity = Vector2(0.0, 0.0)
    var acceleration = Vector2(0.0, 0.001)

    //    var lifespan = Random.nextDouble(1.0, 10.0)
    val size = 2.0

    fun update() {
        val dir = targ - position

        acceleration = dir.normalized
        velocity += acceleration
        velocity = velocity.limit(20.0)
        position += velocity
//        lifespan -= 0.01
    }

    fun display(drawer: Drawer) {
        drawer.fill = ColorRGBa.RED
        drawer.circle(position.x, position.y, size)
    }
}

fun main() = application {

    configure {

        width = 1000
        height = 1000

    }

    oliveProgram {
        backgroundColor = ColorRGBa.BLACK
        val clockRadius = 400.0

        val amount = 20
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

        var t = seconds
        val colorArray = colorsArrays[1]
        extend(NoClear()) {
            backdrop = {
                drawer.fill = colorArray[4].opacify(0.4)
//                drawer.circle(drawer.bounds.center, clockRadius)
            }
        }
        extend {
            var vertical = 0.0005
            var horizontal = 1.0

            drawer.translate(drawer.bounds.center)
            for (i in 0 until amount) {
                drawer.rotate(perlin(1, vertical.toDouble()) + angle)

//                val x1 = sin(t * vertical / i + t)
//                val x1 = sin(t * vertical * i + t)
                val x1 = sin(t * cos(vertical) * i + t)
//                val x1 = t  / (cos(t) * i + t)
//                val x1 = t  + (sin(t) * i - t)
//                val x1 = tan(t * vertical  + t)
//                val x1 = perlin(i,-t * sin(vertical)  + t)
//                val x1 = perlin(( seconds).toInt(),-t * sin(vertical)  - i *2)
//                val x1 = cubic(( seconds).toInt(),-t * sin(vertical)  - i *2)
//                val x1 = cubic(( seconds).toInt(),-t * sin(vertical)  - i *2)
                val x2 = tan(t * vertical  + t)

                val xPos1 = (clockRadius * if (i % 2 == 0) cos(x1) else -cos(x1))
                val yPos1 = clockRadius * if (i % 2 == 1) -sin(x1) else sin(x1)

                val xPos2 = (clockRadius * if (i % 2 == 0) cos(x2) else -cos(x2))
                val yPos2 = clockRadius * if (i % 2 == 1) -sin(x2) else sin(x2)

                val fanParticle = FanParticle(Vector2(0.0, yPos2))
                fanParticle.display(drawer)
                fanParticle.update()

//                drawer.points {
//                    drawer.fill = colorArray[0]
//
////                    drawer.point(sin(seconds) * xPos1, cos(seconds) -yPos2)
////                    drawer.point(cos(yPos1) * (t + i), sin(yPos2) * (t - i))
//                    drawer.strokeWeight = 2.0
//                    drawer.fill = colorArray[2]
//                    drawer.point(0.0, tan(xPos1) * clockRadius + i)
//
////                    drawer.point(xPos1 + i, 0.0)
////                    drawer.rotate(5 * i.toDouble())
//                    drawer.strokeWeight = tan(yPos1) *3
//                    drawer.fill = colorArray[3]/*.opacify(0.2)*/
//                    drawer.point(0.0, tan(yPos1) * clockRadius + i)
//
//
//                }
                lastX = xPos1
//                lastY = y
                drawer.stroke = null
                drawer.fill = colorArray[1].opacify(0.2)
                drawer.rotate(sin(t) * horizontal)
            }

            t += sin(10.0) * Random.cubic(horizontal, vertical)
//            t += ceil(sin(horizontal))
//            t += 1.0
//            t +=Random.cubic(horizontal, vertical)
//            vertical +=1
//            horizontal +=2

        }
    }
}

