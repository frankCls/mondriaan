
import org.openrndr.application
import org.openrndr.color.ColorHSLa
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
        extend {
            val scale = 0.0005
            for (y in 16 until height step 32) {
                for (x in 16 until width step 32) {
                    val simplex = simplex(100, x * seconds * scale, y * seconds * scale)
                    val radius = simplex * 10.0 + 10.0
                    val color = ColorHSLa(map(0.0, 1.0, 0.0, 70.0, simplex), sin(simplex), 0.5)
                    drawer.fill = color.toRGBa()
                    drawer.stroke = null
                    drawer.circle(x * 1.0, y * 1.0, radius)
                }
            }
        }

    }
}

