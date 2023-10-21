import org.openrndr.application
import org.openrndr.color.rgb
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.math.Polar
import org.openrndr.math.Vector2
import org.openrndr.shape.Circle

fun main() = application {
    configure {
        width = 800
        height = 800
        println(width)

    }
    oliveProgram {

        val circleList = mutableListOf(Circle(Vector2(0.0, 0.0), 60.0))

        extend {
            drawer.stroke = null
            drawer.fill = rgb(0.2)
            drawer.clear(rgb(0.9))
            drawer.translate(drawer.bounds.center)
            drawer.circles(circles = circleList)

            var newPos = Polar(seconds * 5091, width * 1.0).cartesian
            val newRadius = circleList.last().radius * 0.91
            var search = true
            while (search) {
                newPos -= newPos.normalized
                circleList.lastOrNull { other: Circle ->
                    val d = (other.center - newPos).length
                    val minDist = other.radius + newRadius + 5
                    d < minDist
                }?.run {
                    circleList.add(Circle(newPos, newRadius))
                    search = false
                }
            }
        }
    }
}
