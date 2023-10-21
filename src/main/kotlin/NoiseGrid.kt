
import org.openrndr.application
import org.openrndr.color.ColorHSLa
import org.openrndr.extra.noise.perlin
import org.openrndr.extra.noise.simplex
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.math.map
import kotlin.math.sin



fun main() = application {
    configure {
        width = 800
        height = 800
    }




    oliveProgram {
        val divider = 32
        val step = width / divider
        extend {
            val scale = 0.005
            for (y in divider until height step step) {
                for (x in divider until width step step) {
                    val simplex = perlin(100, x * seconds * scale, y * seconds * scale)
                    val radius = simplex *10 + 10.0
                    val color = ColorHSLa(map(0.0, 1.0, 0.0, 70.0, simplex), sin(simplex), 0.5)
                    drawer.fill = color.toRGBa()
                    drawer.stroke = null
                    drawer.circle(x * 1.0, y * 1.0, radius)
                }
            }
        }

    }
}

