import org.openrndr.application
import org.openrndr.color.ColorHSLa
import org.openrndr.draw.rectangleBatch
import org.openrndr.extra.noclear.NoClear
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.math.Vector2
import org.openrndr.math.map
import kotlin.math.cos
import kotlin.math.sin


fun main() = application {
    configure {
        width = 1000
        height = 1000
        //        fullscreen = Fullscreen.CURRENT_DISPLAY_MODE
    }


    oliveProgram {
        val horizontalNr = 10
        val verticalNr = 10
        val locations = List(horizontalNr * verticalNr) { index ->
            Vector2(
                ((index % verticalNr).toDouble() * (height / verticalNr)),
                ((index / horizontalNr).toDouble() * (width / horizontalNr))
            )
        }
        val amount = 20
        val angle = 360.0 / amount
//        extend(NoClear())
        extend {
            drawer.scale(0.5)
            drawer.translate(width.toDouble(), height.toDouble())
            for (i in 0..amount - 1) {
                drawer.rectangleBatch {
                    locations.forEachIndexed { index, location ->

                        val sinValue = sin(index * seconds * 0.0005) * cos(index * seconds * 0.001)
                        val cosValue = cos(index * seconds * 0.0005) * -sin(index * seconds * 0.001)
                        val sinNormalized = map(-1.0, 1.0, 0.0, 0.5, sinValue)
                        val cosNormalized = map(-1.0, 1.0, 0.0, 0.5, cosValue)
                        val hueNormalized = map(-1.0, 1.0, 0.0, 60.0, sinValue)
                        val opacity = map(0.0, 1.0, 0.0, 0.5, sinNormalized)
                        val colorHSVa = ColorHSLa(hueNormalized, cosValue, 0.5)

                        drawer.strokeWeight = sinNormalized
                        if (index % 10 == 0) {
                            //                        drawer.isolated {
//                            drawer.rotate(30.0)
                            drawer.strokeWeight = sin(seconds * 0.1)
                            drawer.stroke = colorHSVa.toRGBa().opacify(1.0)
                            drawer.fill = colorHSVa.toRGBa().opacify(0.4)
                            //                        }
                        } else {
                            drawer.fill = colorHSVa.toRGBa().opacify(0.1)
//                            drawer.stroke = colorHSVa.toRGBa().opacify(opacity)
                        }
//                        drawer.rectangle(
//                            location,
//                            cosNormalized / cosValue * 20,
//                            sinNormalized * sinValue * 20
//                        )
                        drawer.translate(width.toDouble(), 0.0)
                        drawer.rotate(90.0)

                        drawer.rectangle(
                            location,
                            sinNormalized * sinValue * 50,
                            -cosNormalized / cosValue * 50
                        )
                        //                    drawer.scale(0.5)
                        //                    drawer.translate(0.0, height.toDouble())
                        //                    drawer.rotate(90.0)
                        //                    drawer.rectangle(
                        //                        location.plus(-cosNormalized / cosValue * 100),
                        //                        sinNormalized * sinValue * 100,
                        //                        -cosNormalized / cosValue * 100
                        //                    )
                        //


                    }
                }
                drawer.rotate(angle)
            }

        }
    }
}