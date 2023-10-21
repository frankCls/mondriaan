import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.noclear.NoClear
import org.openrndr.extra.noise.Random.cubic
import org.openrndr.extra.noise.Random.perlin
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.math.Vector2
import kotlin.math.cos
import kotlin.math.sin

fun main() = application {
    configure {
        width = 800
        height = 800

    }
    oliveProgram {
        backgroundColor = ColorRGBa.WHITE
        var r = 0.0
        var theta = 0.0
        var scale = 1.0
        var amount = 6
        var thickness = 1.0

//        drawer.clear(ColorRGBa.WHITE)
        extend(NoClear())
        extend {
            drawer.points {
                drawer.pushTransforms()
                drawer.translate(width / 2.0, height / 2.0)

                for (i in 0..amount) {
                drawer.rotate(360.0 / amount)
//                val scale = 0.2

//                    val x = if (i % 2 == 0) r else -r * cos(perlin(theta, scale) * seconds % scale)
                val x = -r * sin(theta * scale) * cubic(seconds, scale)
//                    val y = if (i % 4 == 1) - r else r * sin(perlin(theta, scale) *  seconds % scale)
                val y = -r * cos(-theta * scale) * perlin(seconds, scale)

                drawer.fill = ColorRGBa.BLACK
                drawer.stroke = null
                if ((x > -width / 2 || x < width / 2) && (x > -height / 2 || x < height / 2)) {
                    drawer.circle(Vector2(x, y), thickness)
//                        drawer.point(Vector2(x, y))
                }
                theta += 0.005
                r += 0.05   //speed
//                    scale *= 1.00001
//                    thickness += 0.000005
            }
            drawer.popTransforms()

            }
        }
    }
}