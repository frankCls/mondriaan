import org.openrndr.Program
import org.openrndr.application
import org.openrndr.draw.ColorBuffer
import org.openrndr.draw.colorBuffer
import org.openrndr.draw.loadImage
import org.openrndr.extra.fx.blur.BoxBlur
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.shape.Rectangle


fun main(args: Array<String>) {

    application {
        configure {
            width = 800
            height = 800
        }
        oliveProgram {
            //dimensions 343*727
            val images = arrayOf(
                loadImage("/Users/frank.claes/Documents/spiderman/spiderman-run-1.png"),
                loadImage("/Users/frank.claes/Documents/spiderman/spiderman-run-2.png"),
//                loadImage("/Users/frank.claes/Documents/spiderman/spiderman-hang.png"),
//                loadImage("/Users/frank.claes/Documents/spiderman/spiderman-shoot.png")
            )


            val blurreds = images.map { colorBuffer(it.width, it.height) }
            val blur = BoxBlur()
            val source = Rectangle(0.0, 130.0, 330.0, 587.0)
            extend {


                val imageWidth = 343.0 / 4
                val imageHeight = 587.0 / 4

                val iterations = seconds % 30
                for (z in 0..1) {
//                    if(z == 1) {
//                        drawer.translate()
//                        drawer.scale(seconds/60 % 60)
//                    }
                    drawer.pushTransforms()
//
                    drawCircle(iterations, imageWidth, imageHeight, images, source)
                    drawer.popTransforms()
                }
            }
        }
    }

}

fun Program.drawCircle(
    iterations: Double,
    imageWidth: Double,
    imageHeight: Double,
    images: Array<ColorBuffer>,
    source: Rectangle
) {
    for (y in 0..iterations.toInt()) {
        drawer.translate(width / 2.0, height / 2.0)
        drawer.pushTransforms()
        for (x in 0..1) {

            drawer.translate(imageWidth, 0.0)
            val target = Rectangle(
                (width / 5 - imageWidth + if (x % 2 == 0) 0.0 else (imageWidth)) - imageWidth,
                height / 5.0 - imageHeight,
                if (x % 2 == 0) imageWidth else (-imageWidth),
                imageHeight
            )


            drawOtis(images, source, target, x)

        }
        drawer.popTransforms()
        drawer.rotate(360.0 / iterations)
        drawer.translate(-width / 2.0, -height / 2.0)
    }
}

fun Program.drawOtis(
    images: Array<ColorBuffer>,
    source: Rectangle,
    target: Rectangle,
    i: Int
) {
    drawer.image(images[((seconds * 2 + i) % images.size).toInt()], source, target)
}







