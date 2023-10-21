import org.openrndr.application
import org.openrndr.color.ColorHSLa
import org.openrndr.draw.circleBatch
import org.openrndr.draw.rectangleBatch
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.math.Vector2
import org.openrndr.math.map
import kotlin.math.cos
import kotlin.math.sin


fun main() = application {
    configure {
        width = 800
        height = 800
//        fullscreen = Fullscreen.CURRENT_DISPLAY_MODE
    }


    oliveProgram {
        val horizontalNr = 20
        val verticalNr = 20
        val locations = List(
            horizontalNr * verticalNr
        ) { index ->
            Vector2(
                ((index % verticalNr).toDouble() * (height / verticalNr)),
                ((index / horizontalNr).toDouble() * (width / horizontalNr))
            )
        }

        extend {
            drawer.rectangleBatch {
                locations.forEachIndexed { index, location ->
                    val sinValue = sin(index * seconds * 0.005) * cos(index * seconds * 0.001)
                    val cosValue = cos(index * seconds * 0.005)
                    val sinNormalized = map(-1.0, 1.0, 0.0, 0.3, sinValue)
                    val cosNormalized = map(-1.0, 1.0, 0.0, 0.3, cosValue)
                    val hueNormalized = map(-1.0, 1.0, 0.0, 120.0, sinValue)
                    val opacity = map(0.0, 1.0, 0.5, 0.0, sinNormalized)
                    val colorHSVa = ColorHSLa(hueNormalized, cosValue, 0.5)
                    drawer.fill = colorHSVa.toRGBa().opacify(0.1)
                    drawer.strokeWeight = sinNormalized
                    drawer.stroke = colorHSVa.toRGBa().opacify(opacity)
                    drawer.rectangle(
                        location,
                        -cosNormalized + cosValue * 100,
                        sinNormalized - sinValue * 100
                    )
                }
            }
        }
    }
}