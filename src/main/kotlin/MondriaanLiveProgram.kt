import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.Drawer
import org.openrndr.draw.rectangleBatch
import org.openrndr.extra.olive.oliveProgram
import kotlin.random.Random.Default.nextBoolean
import kotlin.collections.Collection

val GOLDEN_RATIO = 1.618033988749894

fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()

abstract class DrawableWithContext(drawer: Drawer) : Drawable

interface Drawable {
    fun draw()
}

class Container(
    private val drawer: Drawer,
    private var x: Double,
    private var y: Double,
    private val width: Double,
    private val height: Double,
    internal var rectangles: Collection<Rect> = ArrayList(),
    private val recursive: Int,
    private val colors: Collection<ColorRGBa>
) : DrawableWithContext(drawer) {
    init {
        val horizontalWidths = divide(arrayListOf(this.width), this.recursive)
        val verticalWidths = divide(arrayListOf(this.height), this.recursive)
        this.rectangles = initializeRects(
            this.drawer,
            this.x,
            this.y,
            horizontalWidths,
            verticalWidths,
            this.colors
        )
    }

    override fun draw() {
        val batch = drawer.rectangleBatch {
            for(rect in rectangles) {
//                drawer.fill = rect.color
//                drawer.stroke = rect.borderColor
//                drawer.strokeWeight = 1.0
//                drawer.rectangle(rect.x, rect.y, rect.width, rect.height)
                rect.draw()
            }

        }
        drawer.rectangles(batch)
    }

    override fun toString(): String {
        return "container: x: $x, y: $y, width: $width, height: $height"
    }
}

enum class MovingOrientation {
    HORIZONTAL,
    VERTICAL
}

class Rect(
    private val drawer: Drawer,
    internal var x: Double,
    internal var y: Double,
    internal val width: Double,
    internal val height: Double,
    val color: ColorRGBa,
    val borderColor: ColorRGBa = ColorRGBa.BLACK,
    val markedForAnimation: Boolean = false,
    var movingOrientation: MovingOrientation,
    var speed: Double = 2.0,
    var xDirection: Int = 1,
    var yDirection: Int = 1,
) : DrawableWithContext(drawer) {
    override fun draw() {
        if (markedForAnimation) {
            if (this.x > drawer.width) {
                xDirection = -1
            }
            if (this.x < 0.0) {
                xDirection = 1
            }
            this.x =
                if (movingOrientation == MovingOrientation.HORIZONTAL) this.x + (speed * xDirection) else this.x
            if (this.y > drawer.height) {
                yDirection = -1
            }
            if (this.y < 0.0) {
                yDirection = 1
            }
            this.y =
                if (movingOrientation == MovingOrientation.VERTICAL) this.y + (speed * yDirection) else this.y
        }
        drawer.fill = color
        drawer.stroke = borderColor
        drawer.strokeWeight = 1.0
        drawer.rectangle(x, y, width, height)
    }

    override fun toString(): String {
        return "rect: x: $x, y: $y, width: $width, height: $height"
    }
}

fun divide(points: Collection<Double>, recursive: Int): Collection<Double> {
    val results: List<Double> = points
        .map {
            val first = it / GOLDEN_RATIO
            if (nextBoolean()) {
                arrayListOf(first, (it - first))
            } else {
                arrayListOf((it - first), first)
            }
        }
        .flatten()
    return if (recursive == 1) results else divide(results, recursive - 1)
}

fun initializeContainers(
    drawer: Drawer,
    horizontalPoints: Collection<Double>,
    verticalPoints: Collection<Double>,
    colors: java.util.ArrayList<ColorRGBa>,
): Collection<Container> {
    val containers = ArrayList<Container>()
    var tempY = 0.0
    for (y in verticalPoints) {
        var tempX = 0.0
        for (x in horizontalPoints) {
            containers.add(
                Container(
                    drawer = drawer,
                    x = tempX,
                    y = tempY,
                    width = x,
                    height = y,
                    recursive = arrayOf(2, 3).random(),
                    colors = colors
                )
            )
            tempX += x
        }
        tempY += y
    }
//    containers.forEach { println(it.toString()) }
    return containers
}

fun initializeRects(
    drawer: Drawer,
    startX: Double,
    startY: Double,
    horizontalPoints: Collection<Double>,
    verticalPoints: Collection<Double>,
    colors: Collection<ColorRGBa>
): Collection<Rect> {
    val rects = ArrayList<Rect>()
    var tempY = startY
    for (y in verticalPoints) {
        var tempX = startX
        for (x in horizontalPoints) {
            rects.add(
                Rect(
                    drawer = drawer,
                    x = tempX,
                    y = tempY,
                    width = x,
                    height = y,
                    colors.random(),
                    markedForAnimation = nextBoolean(),
                    movingOrientation = if (nextBoolean()) MovingOrientation.HORIZONTAL else MovingOrientation.VERTICAL,
                    speed = arrayOf(0.5, 1.0, 2.0, 3.0, 4.0, 5.0).random()
                )
            )
            tempX += x
        }
        tempY += y
    }
//    rects.forEach { println(it.toString()) }
    return rects
}

fun main() = application {
    configure {
        title = "Mondriaan"
        height = 800
        width = height
    }
    oliveProgram {
        val recursive = 3
        val containers: Collection<Container>
        val colors = arrayListOf(
            ColorRGBa(1.0, 0.0, 0.0),
            ColorRGBa(1.0, 1.0, 0.0),
            ColorRGBa(0.0, 0.0, 1.0),
            ColorRGBa(1.0, 1.0, 1.0),
            ColorRGBa(1.0, 1.0, 1.0),
        )
        val horizontalWidths = divide(arrayListOf(width.toDouble()), recursive)
        val verticalWidths = divide(arrayListOf(height.toDouble()), recursive)
        containers =
            initializeContainers(drawer, horizontalWidths, verticalWidths, colors)
        extend {
            drawer.clear(ColorRGBa.BLACK)
            containers.forEach {
                it.draw()
            }
            drawer.rectangleBatch {  }
        }
    }
}




