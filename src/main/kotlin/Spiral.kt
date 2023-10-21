import org.openrndr.application
import org.openrndr.color.ColorHSLa
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.noclear.NoClear
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.math.Vector2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan
import kotlin.random.Random

fun main() = application {
    configure {
        width = 800
        height = 800
    }
    oliveProgram {
        var r = 0.0
        var theta = 0.0
        val scale = 0.1
        val amount = 20
        var thickness = 0.1

//        drawer.clear(ColorRGBa.WHITE)
        extend(NoClear())
        extend {
            drawer.circles {
                drawer.pushTransforms()
                drawer.translate(width / 2.0, height / 2.0)
                for (i in 0..amount) {
                    drawer.rotate(360.0 / amount)
//                val scale = 0.2

                    val x = r * cos(theta * theta)
//                    val x = r * sin(if (i % 2 == 0) -theta else theta * scale)
//                val y = r * sin(theta * scale)
                    val y = r * cos(if (i % 5 == 0) -theta else theta * scale)

                    drawer.fill = ColorHSLa(20.0, cos(r), sin(r) + 0.2).toRGBa()
                    drawer.stroke = null
                    if ((x > -width / 2 || x < width / 2) && (x > -height / 2 || x < height / 2)) {
                        drawer.rectangle(Vector2(x, y), thickness, thickness)
                    }
                    theta += sin(0.5)
                    r += 0.01 //speed
//                r += sin(y)
//                    thickness += sin(0.00003)
                    thickness += 0.001
                }
                drawer.popTransforms()
            }
        }
    }
}