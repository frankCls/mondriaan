import org.openrndr.*
import org.openrndr.animatable.Animatable
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.Drawer
import org.openrndr.draw.rectangleBatch
import org.openrndr.extra.olive.oliveProgram
import kotlin.random.Random.Default.nextBoolean
import kotlin.collections.Collection

val GOLDEN_RATIO = 1.618033988749894

fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()

abstract class DrawableWithContext() : Drawable

interface Drawable {
    fun draw()
    fun resetPosition()
    fun resetSpeed()
}

class Container(
    private val drawer: Drawer,
    private var x: Double,
    private var y: Double,
    private val width: Double,
    private val height: Double,
    var rectangles: Collection<Rect> = ArrayList(),
    private val recursive: Int,
    private val colors: Collection<ColorRGBa>
) : DrawableWithContext() {
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
            for (rect in rectangles) {
                rect.draw()
            }

        }
        drawer.rectangles(batch)
    }

    override fun resetPosition() {
        rectangles.forEach { it.resetPosition() }
    }

    override fun resetSpeed() {
        rectangles.forEach { it.resetSpeed() }
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
    var color: ColorRGBa,
    val borderColor: ColorRGBa = ColorRGBa.BLACK,
    var markedForAnimation: Boolean = false,
    var movingOrientation: MovingOrientation,
    var speed: Double = 2.0,
    var xDirection: Int = arrayOf(1, -1).random(),
    var yDirection: Int = arrayOf(1, -1).random(),
) : Animatable(), Drawable {

    private val initialPosition: Pair<Double, Double> = Pair(x, y)

    override fun draw() {
        this.apply {
            if (markedForAnimation) {
                if (this.x > drawer.width - this.width) {
                    xDirection = -1
                }
                if (this.x < 0.0) {
                    xDirection = 1
                }
                this.x =
                    if (movingOrientation == MovingOrientation.HORIZONTAL) this.x + (speed * xDirection) else this.x
                if (this.y > drawer.height - this.height) {
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
        }

        drawer.rectangle(x, y, width, height)
    }

    override fun resetPosition() {
        x = initialPosition.first
        y = initialPosition.second
        xDirection = arrayOf(1, -1).random()
        yDirection = arrayOf(1, -1).random()
    }

    override fun resetSpeed() {
        speed = 0.0
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
    return containers
}

fun initializeSketch(
    drawer: Drawer,
    recursive: Int,
    colors: ArrayList<ColorRGBa>
): Collection<Container> {
    val horizontalWidths = divide(arrayListOf(drawer.width.toDouble()), recursive)
    val verticalWidths = divide(arrayListOf(drawer.height.toDouble()), recursive)
    return initializeContainers(drawer, horizontalWidths, verticalWidths, colors)
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
                    markedForAnimation = true,
                    movingOrientation = if (x > y) MovingOrientation.HORIZONTAL else MovingOrientation.VERTICAL,
                    speed = 0.0
                )
            )
            tempX += x
        }
        tempY += y
    }
    return rects.sortedBy { it.speed }
}

fun main() = application {
    configure {
        title = "Mondriaan"
        height = 800
        width = height
    }
    oliveProgram {
        val percentages = arrayOf(20, 40, 60, 80, 100)
        var percentagesIterator = percentages.iterator()
        var animate_all = true
        var animate_percentage = 10
        val recursive = 3
        var containers: Collection<Container>
        val colors = arrayListOf(
            ColorRGBa(1.0, 0.0, 0.0),
            ColorRGBa(1.0, 1.0, 0.0),
            ColorRGBa(0.0, 0.0, 1.0),
            ColorRGBa(1.0, 1.0, 1.0),
            ColorRGBa(1.0, 1.0, 1.0),
        )
        containers = initializeSketch(drawer, recursive, colors)
        extend {
            drawer.clear(ColorRGBa.BLACK)
            containers.forEach {
                it.draw()
            }
            drawer.rectangleBatch { }
        }


        mouse.buttonUp.listen {
            containers.forEach {
                it.rectangles.forEach { rect -> rect.color = colors.random() }
            }
        }

        keyboard.keyDown.listen {
            if (it.key == KEY_F3) {
                containers.forEach {
                    it.resetPosition()
                    it.resetSpeed()
                }
            }
            if (it.key == KEY_F1) {
                animate_all = animate_all.not()
                containers.forEach {
                    it.rectangles.forEach { rect ->
                        rect.markedForAnimation = if (animate_all) true else nextBoolean()
                    }
                }
//                var percentage =
//                    if (percentagesIterator.hasNext()) percentagesIterator.next() else percentagesIterator =
//                        percentages.iterator()

            }
            if (it.key == KEY_F2) {
                containers.forEach {
                    it.rectangles.forEach { rect ->
                        rect.movingOrientation =
                            if (rect.movingOrientation == MovingOrientation.HORIZONTAL)
                                MovingOrientation.VERTICAL
                            else MovingOrientation.HORIZONTAL
                    }
                }
            }
            if (it.key == KEY_ENTER) {
                containers = initializeSketch(drawer, recursive, colors)
            }
            if (it.key == KEY_F4) {
                containers.forEach {
                    it.rectangles.forEach { rect ->
                        rect.speed =
                            arrayOf(0.5, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0).random()
                    }
                }
            }
            if (it.key == KEY_F5) {
                containers.forEach {
                    it.rectangles.forEach { rect ->
                        rect.color = rect.color.opacify(0.7)
                    }
                }
            }
        }
    }
}






