import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.noclear.NoClear
import org.openrndr.extra.noise.Random
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.math.Polar
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
        extend(NoClear())
        extend {
            val radius = 100.0
            val theta = 0.05
            val zoom = 0.05
            val colors = listOf(
                ColorRGBa.fromHex(0x001219),
                ColorRGBa.fromHex(0x005f73),
                ColorRGBa.fromHex(0x0a9396),
                ColorRGBa.fromHex(0x94d2bd),
                ColorRGBa.fromHex(0xe9d8a6),
                ColorRGBa.fromHex(0xee9b00),
                ColorRGBa.fromHex(0xca6702),
                ColorRGBa.fromHex(0xbb3e03),
                ColorRGBa.fromHex(0xae2012),
                ColorRGBa.fromHex(0x9b2226)
            )

            drawer.points(
                generateSequence(
                    Vector2(width/2.0, height/2.0)
                ) {
//                    drawer.fill = Random.pick(colors).opacify(0.1)
                    drawer.fill = ColorRGBa.BLACK
                    Vector2(x= radius * cos(theta ), y =radius * sin(theta))
                }
                    .take(10000)
                    .toList()
            )
            drawer.translate(width / 2.0, height / 2.0)
            drawer.rotate(90.0)
        }
    }
}
