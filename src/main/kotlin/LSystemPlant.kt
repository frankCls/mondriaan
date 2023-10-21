//import kotlinx.serialization.Contextual
//import kotlinx.serialization.Serializable
//import kotlinx.serialization.decodeFromString
//import kotlinx.serialization.json.Json
//import okhttp3.*
//import org.openrndr.application
//import org.openrndr.color.ColorRGBa
//import org.openrndr.draw.Drawer
//import org.openrndr.extra.olive.oliveProgram
//import org.openrndr.math.Vector2
//import org.openrndr.panel.ControlManager
//import org.openrndr.panel.elements.button
//import org.openrndr.panel.elements.clicked
//import org.openrndr.panel.layout
//import java.io.IOException
//
//@Serializable
//data class LSystemConfigurationPlant(
//    val name: String? = null,
//    val start: String,
//    val rules: Map<Char, String>,
//    val ratio: Double? = null,
//    val a: Double? = 90.0,
//    val length: Double? = null,
//    val branchThicknessRatio: Double? = null,
//    val branchThickness: Double? = null,
//    var iter: Int? = 6,
//    val startPosition: @Contextual Vector2? = null
//)
//
//
//fun main() = application {
//    configure {
//        width = 800
//        height = 800
//    }
//
//    oliveProgram {
//
//        val configs = listOf(
//            LSystemConfiguration(
//                name = "Plant",
//                start = "YYY",
//                rules = mapOf('X' to "X[-FFF][+FFF]FX", 'Y' to "YFX[+Y][-Y]"),
//                ratio = 0.3,
//                a = 25.0,
//                length = 20.0,
//                branchThicknessRatio = 1.0,
//                branchThickness = 0.3,
//                iter = 5,
//                startPosition = Vector2(width / 2.0, height.toDouble())
//            )
//        )
//
//
//        var configurationIndex = 0
//        var configuration: LSystemConfiguration = configs[configurationIndex]
//
//        fun draw(char: Char, drawer: Drawer, configuration: LSystemConfiguration) {
//            val ratio = configuration.ratio ?: 1.0
//            drawer.stroke = ColorRGBa.WHITE
//            val branchThicknessRatio = configuration.branchThicknessRatio ?: 1.0
//            val branchThickness = configuration.branchThickness ?: 0.3
//            drawer.strokeWeight = branchThickness * branchThicknessRatio
//            if (char == 'F' || char == 'G') {
//                val length =
//                    (configuration.length ?: 15.0)
//                /** (configuration.iter?.times(0.33333) ?: 2.0)*/
//                drawer.lineSegment(0.0, 0.0, 0.0, -length * ratio)
//                drawer.translate(0.0, -length * ratio)
//                drawer.scale(1 / (configuration.iter!! * 0.3))
//            }
//            if (char == '+') {
//                drawer.rotate(configuration.a ?: 90.0)
//            }
//            if (char == '-') {
//                drawer.rotate(-(configuration.a ?: 90.0))
//            }
//            if (char == '[') {
//                drawer.pushTransforms()
//            }
//            if (char == ']') {
//                drawer.popTransforms()
//            }
//        }
//
//        fun getRule(char: Char): String {
//            return configuration.rules[char] ?: char.toString()
//        }
//
//        fun iteration(sentence: String, iterations: Int = 1): String {
//            val replaced = sentence.map { getRule(it) }.joinToString("")
//            return if (iterations == 1) {
//                replaced
//            } else {
//                iteration(replaced, iterations - 1)
//            }
//        }
//
//
////        extend(ControlManager()) {
////            layout {
////                button {
////                    label = "generate"
////                    // -- listen to the click event
////                    clicked {
////                        run("http://localhost:3000", object : Callback {
////                            override fun onFailure(call: Call, e: IOException) {}
////                            override fun onResponse(call: Call, response: Response) {
////                                val message = response.body()?.string()
////                                println(message)
////                                configuration = Json.decodeFromString(message!!)
////                                configuration.iter =
////                                    if (configuration.iter!! > 10) 10 else configuration.iter
////                                println(configuration)
////                            }
////                        })
////                    }
////                }
////                button {
////                    label = "next"
////                    // -- listen to the click event
////                    clicked {
////                        println("clicked")
////                        val incrementedConfigurationIndex = configurationIndex++
////                        if (incrementedConfigurationIndex < configs.size) {
////                            configuration = configs[incrementedConfigurationIndex]
////                        }
////                    }
////                    println(configuration)
////                }
////                button {
////                    label = "back"
////                    // -- listen to the click event
////                    clicked {
////                        println("clicked")
////                        val decrementedConfigurationIndex = configurationIndex--
////                        if (decrementedConfigurationIndex > 0) {
////                            configuration = configs[decrementedConfigurationIndex]
////                        }
////                    }
////                    println(configuration)
////                }
////            }
////        }
//
//
//        extend {
//
//            drawer.translate(configuration.startPosition ?: Vector2(width / 2.0, height / 2.0))
//            val sentence = iteration(
//                configuration.start,
//                if (configuration.iter!! > 10) 7 else configuration.iter!!
//            )
//            for (char in sentence) {
//                draw(char, drawer, configuration)
//            }
//        }
//    }
//}
