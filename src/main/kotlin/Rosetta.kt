import org.openrndr.application
import org.openrndr.color.ColorHSLa
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.rectangleBatch
import org.openrndr.extra.noclear.NoClear
import org.openrndr.extra.noise.Random
import org.openrndr.extra.noise.random
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
                ((index / horizontalNr).toDouble() * (width / horizontalNr)) - horizontalNr,
                ((index % verticalNr).toDouble() * (height / verticalNr)) - verticalNr
            )
        }
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

        extend(NoClear())

        val amount = 17
        val angle = 360.0 / amount
        extend {
//            drawer.pushTransforms()
//            drawer.translate(width/2.0, height/2.0)
//            drawer.rotate(5.0)
//            drawer.popTransforms()
            drawer.pushTransforms()
            val scale = mouse.position.x * 0.01
//            val scale = 8.0
            drawer.scale(scale)
//            val translateFactor = 0.0625
            val translateFactor = (1.0 /scale   ) /2

            drawer.translate(
                width.toDouble() * translateFactor,
                height.toDouble() * translateFactor
            )
            drawer.rectangleBatch {
                for (i in 0..amount - 1) {

//                    drawer.translate(width / 10.0, height / 10.0)
                    drawer.rotate(angle)
                    locations.forEachIndexed { index, location ->

                        val sinValue =
                            sin((index - horizontalNr) * seconds * 0.001)

                        val cosValue =
                            cos((index + verticalNr) * seconds * 0.001)

                        val sinNormalized = map(-1.0, 1.0, 0.0, 1.0, sinValue)
                        val cosNormalized = map(-1.0, 1.0, 0.0, 1.0, cosValue)
                        val hueNormalized = map(-1.0, 1.0, 0.0, 40.0, sinValue)
                        val opacity = map(0.0, 1.0, 0.0, 0.5, cosNormalized)
                        val colorHSVa = ColorHSLa(hueNormalized, cosValue, 0.5)

                        drawer.strokeWeight = sinNormalized
                        if (index % amount == 0) {
                            drawer.fill = colorHSVa.toRGBa().opacify(0.3)
//                            drawer.fill = Random.pick(colors).opacify(0.3)
//                            drawer.rotate(index.toDouble())
//                        }
                        } else {
                            drawer.fill = colorHSVa.toRGBa().opacify(0.15)
//                            drawer.stroke = colorHSVa.toRGBa().opacify(opacity)
                        }
                        drawer.rectangle(
                            location,
                            cosNormalized * 50,
                            sinNormalized * 50
                        )
                        drawer.translate(width.toDouble(), 0.0)
                        drawer.rotate(90.0)

                        drawer.rectangle(
                            location.plus(sinNormalized / sinValue * 100),
                            sinNormalized * 50,
                            cosNormalized * 50
                        )
//                    drawer.scale(0.5)
//                    drawer.translate(0.0, height.toDouble())
//                    drawer.rotate(90.0)
                        drawer.rectangle(
                            location.plus(-cosNormalized / cosValue * 100),
                            sinNormalized * sinValue * 50,
                            -cosNormalized / cosValue * 50
                        )
//

                    }
                }
            }

            drawer.translate(width / 2.0, height / 2.0)
//            drawer.rotate(5.0)
            drawer.popTransforms()
        }
    }
}


// write a function to map these values
// 0 -> 0
// 0.5 -> 1.00
// 0.75 -> 1.33
// 0.875 -> 1.60
// 0.9375 -> 1.78
// 1.0 -> 0,500
// 2.0 -> 0.250
// 3.0 -> 0.165
// 4.0 -> 0.125
// 5.0 -> 0.100
// 6.0 -> 0.083
// 7.0 -> 0.071
// 8.0 -> 0.0625
// 16.0 -> 0.03125
// how to calculate the mapping?
fun calc(x: Double): Double {
    return 1.0 / (x + 1.0)
}


