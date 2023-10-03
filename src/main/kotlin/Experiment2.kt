import org.openrndr.application
import org.openrndr.color.ColorHSLa
import org.openrndr.color.rgb
import org.openrndr.draw.circleBatch
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.math.Vector2
import org.openrndr.math.map
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

//data class BangCircle(val location: Vector2, var radius: Double)

fun main() = application {
    configure {
        width = 800
        height = 800
    }


    oliveProgram {
        val horizontalNr = 20
        val verticalNr = 20
        val xOffset = (width / horizontalNr) / 2
        val yOffset = (height / verticalNr) / 2
        val circles = List(
            horizontalNr * verticalNr
        ) { index ->
            BangCircle(
                Vector2(
                    ((index % verticalNr).toDouble() * (height / verticalNr)) ,
                    ((index / horizontalNr).toDouble() * (width / horizontalNr))
                ),
                0.0
            )
        }

        extend {
            drawer.circleBatch {
                circles.forEachIndexed { index, c ->
//                    drawer.pushTransforms()
                    val sinValue = sin(index * seconds * 0.001)
                    val cosValue = cos(index * seconds * 0.001)
                    val indexNormalized =
                        map(0.0, (horizontalNr * verticalNr).toDouble(), 0.0, 1.0, index.toDouble())
                    c.radius = (sinValue)
                    val sinNormalized = map(-1.0, 1.0, 0.0, 0.3, sinValue)
                    val cosNormalized = map(-1.0, 1.0, 0.0, 0.3, cosValue)
                    val hueNormalized = map(-1.0, 0.2, 0.7, 60.0, sinValue)
                    val opacity = map(0.0, 1.0, 0.5, 0.0, sinNormalized)
                    val colorHSVa = ColorHSLa(hueNormalized, cosValue, 0.5)
                    drawer.fill = colorHSVa.toRGBa().opacify(0.1)
                    drawer.strokeWeight = sinNormalized
                    drawer.stroke = colorHSVa.toRGBa().opacify(opacity)

//                    drawer.circle(c.location, tanValue * 5)
//                    drawer.rotate(sinValue)
//                    drawer.scale(cosValue)
                    drawer.rectangle(
                        c.location,
                        -cosNormalized + cosValue * 50,
                        sinNormalized - sinValue * 50
                    )
//                    drawer.rotate(indexNormalized)
                    drawer.rectangle(
                        c.location,
                        -sinNormalized - sinValue * 200,
                        cosNormalized + cosValue * 200,
                    )

//                    drawer.popTransforms()
                }
            }
        }
    }
}