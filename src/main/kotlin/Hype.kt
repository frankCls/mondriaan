import org.openrndr.application
import org.openrndr.color.rgb
import org.openrndr.draw.circleBatch
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.math.Vector2
import org.openrndr.math.map
import org.openrndr.panel.ControlManager
import org.openrndr.panel.elements.button
import org.openrndr.panel.elements.clicked
import org.openrndr.panel.elements.dropdownButton
import org.openrndr.panel.elements.item
import org.openrndr.panel.layout
import org.openrndr.shape.Circle
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

//data class Circle(val location: Vector2, var radius: Double)

fun main() = application {
    configure {
        width = 800
        height = 800
    }


    oliveProgram {
        var horizontalNr = 20
        var verticalNr = 20
        val xOffset = (width / horizontalNr)
        val yOffset = (height / verticalNr)
        val circles = List(
            horizontalNr * verticalNr
        ) { index ->
            Circle(
                Vector2(
                    ((index % verticalNr).toDouble() * (height / verticalNr)) + yOffset,
                    ((index / horizontalNr).toDouble() * (width / horizontalNr)) + xOffset
                ),
                0.0
            )
        }

        var circleScale = 10

        var redFillLeft = 0.7
        var redFillRight = 0.7
        var square = true
        var yellowFillLeft = 0.2
        val yellowFillRight = 0.9
        extend {

            drawer.circleBatch {
                circles.forEachIndexed { index, c ->
                    val sinValue = sin(index * seconds * 0.01)
                    val cosValue = cos(index * seconds * 0.01)
                    val tanValue = tan(index * (seconds % 1000) * 0.1)
//                    c.radius = sinValue * circleScale
//                    println("value: ${tanValue}")
                    val sinNormalized = map(-1.0, 1.0, yellowFillLeft, yellowFillRight, sinValue)
                    val cosNormalized = map(-1.0, 1.0, yellowFillLeft, redFillRight, cosValue)
                    val tanNormalized = map(-100.0, 100.0, 0.0, 0.8, tanValue)
//                    println(sinNormalized)
//                    drawer.stroke = null
//                    drawer.stroke = rgb(sinNormalized, tanNormalized, cosNormalized)
//                    drawer.fill = ColorRGBa.WHITE/*.opacify(sinNormalized)*/
                    drawer.fill = rgb(cosNormalized, sinNormalized, 0.3).opacify(sinNormalized)
                    drawer.stroke =
                        rgb(sinNormalized, cosNormalized, cosNormalized).opacify(sinNormalized)
                    if (square) drawer.rectangle(c.center, c.radius * 2, c.radius * 2)
                    else {
                        drawer.circle(c.center, c.radius)
                    }
                }
//                println(circles)
            }
        }
        extend(ControlManager()) {
            layout {
                button {
                    label = "nr +"
                    clicked {
                        horizontalNr += 1
                        verticalNr += 1
                    }
                }
                button {
                    label = "nr - : "
                    clicked {
                        horizontalNr -= 1
                        verticalNr -= 1
                    }
                }
                button {
                    label = "scale +"
                    clicked {
                        circleScale += 1
                    }
                }
                button {
                    label = "scale - : "
                    clicked {
                        circleScale -= 1
                    }
                }
                button {
                    label = "Red fill low -"
                    clicked {
                        redFillLeft -= 0.1
                    }
                }
                button {
                    label = "Red fill low +"
                    clicked {
                        redFillLeft += 0.1
                    }
                }
                button {
                    label = "Red fill high -"
                    clicked {
                        redFillLeft -= 0.1
                    }
                }
                button {
                    label = "Red fill high +"
                    clicked {
                        redFillLeft += 0.1
                    }
                }
                dropdownButton {
                    label = "form"

                    item {
                        label = "square"
                        events.picked.listen {
                            square = true
                        }
                    }

                    item {
                        label = "circle"
                        events.picked.listen {
                            square = false
                        }
                    }
                }
                button {
                    label = "yellow fill low -"
                    clicked {
                        yellowFillLeft -= 0.1
                    }
                }
                button {
                    label = "yellow fill low +"
                    clicked {
                        yellowFillLeft += 0.1
                    }
                }
                button {
                    label = "yellow fill high -"
                    clicked {
                        yellowFillLeft -= 0.1
                    }
                }
                button {
                    label = "yellow fill high +"
                    clicked {
                        yellowFillLeft += 0.1
                    }
                }
            }
        }
    }
}
