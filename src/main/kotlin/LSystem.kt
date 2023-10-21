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
//data class LSystemConfiguration(
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
//val client = OkHttpClient()
//
//fun run(url: String, callback: Callback) {
//    val request = Request.Builder()
//        .url(url)
//        .build()
//
//    client.newCall(request).enqueue(callback)
//}
//
//class StorePoint(val origin: Vector2, val angle: Double)
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
//                name = "Snowflake",
//                start = "F++F++F",
//                rules = mapOf('F' to "F-F++F-F"),
//                ratio = 1.6,
//                a = 60.0,
//                length = 5.0,
//                branchThicknessRatio = 1.0,
//                branchThickness = 0.3,
//                iter = 4,
//                startPosition = Vector2(width / 2.0, height / 2.0)
//            ),
//            LSystemConfiguration(
//                name = "Hilbert Curve",
//                start = "X",
//                rules = mapOf('X' to "+YF-XFX-FY+", 'Y' to "-XF+YFY+FX-"),
//                ratio = 1.6,
//                a = 90.0,
//                length = 5.0,
//                branchThicknessRatio = 1.0,
//                branchThickness = 0.3,
//                iter = 7,
//                startPosition = Vector2(0.0, height.toDouble())
//            ),
//            Json.decodeFromString("{\"start\":\"NN\",\"rules\":{\"F\":\"THHF+[-]-\",\"H\":\"-T\",\"T\":\"+TT--FHT\",\"N\":\"H\"},\"a\":233,\"iter\":16}"),
//            Json.decodeFromString("{\"start\":\"PG\",\"rules\":{\"F\":\"JFF-J\",\"P\":\"PGJPF\",\"G\":\"-PF\",\"J\":\"G+GF-\"},\"a\":182,\"iter\":9}"),
//            Json.decodeFromString("{\"start\":\"FY\",\"rules\":{\"F\":\"P[YF]FF+\",\"H\":\"-Y-PF\",\"N\":\"FN-TY\",\"Y\":\"T+FYP[HH-]\",\"P\":\"[]PFT\"},\"a\":90,\"iter\":16}"),
//            Json.decodeFromString("{\"start\":\"FOM\",\"rules\":{\"F\":\"[M-]FN\",\"M\":\"+[N+TFFF]O\",\"N\":\"-TF\",\"O\":\"+\",\"T\":\"NFF[M[]+T+]\"},\"a\":36,\"iter\":16}"),
//            Json.decodeFromString("{\"start\":\"SR\",\"rules\":{\"F\":\"STSFF-YT\",\"T\":\"FSS-\",\"Y\":\"[F]\",\"R\":\"-F-RSFR\"},\"a\":280,\"iter\":9}"),
//            Json.decodeFromString("{\"start\":\"ZZZZZZZZZZ\",\"rules\":{\"F\":\"[T-]+F\",\"Z\":\"TF\",\"T\":\"-F-TZ\"},\"a\":36,\"iter\":4}"),
//            Json.decodeFromString("{\"start\":\"IDVC\",\"rules\":{\"F\":\"[]-F-FD\",\"D\":\"CF[C]IDFFI\",\"V\":\"[CDFC]\",\"I\":\"V\",\"C\":\"F\"},\"a\":320,\"iter\":7}"),
//            Json.decodeFromString("{\"start\":\"FGYY\",\"rules\":{\"F\":\"W-\",\"W\":\"[U]+GG-W\",\"U\":\"[G]YW-+\",\"G\":\"WFFG\",\"Y\":\"FF+\"},\"a\":90,\"iter\":10}"),
//            Json.decodeFromString("{\"start\":\"TT\",\"rules\":{\"F\":\"+F\",\"T\":\"TFF[FFFF]\"},\"a\":45,\"iter\":7}"),
//            Json.decodeFromString("{\"start\":\"TTF\",\"rules\":{\"F\":\"++FSTTTF+\",\"S\":\"-\",\"T\":\"+F+S-F+\"},\"a\":60,\"iter\":6}"),
//            Json.decodeFromString("{\"start\":\"F\",\"rules\":{\"F\":\"[]F-NFFF\",\"N\":\"[]NN+FN\"},\"a\":90,\"iter\":6}"),
//            Json.decodeFromString("{\"start\":\"KF\",\"rules\":{\"F\":\"FDK[F-[F-]]\",\"K\":\"D[FZ]++-\",\"D\":\"[[]ZFKDKD]+\",\"Z\":\"ZFFF\"},\"a\":90,\"iter\":6}"),
//            Json.decodeFromString("{\"start\":\"JE\",\"rules\":{\"F\":\"E[V]FEXV\",\"X\":\"[XEFFXV-S]\",\"S\":\"[J]FV-V\",\"V\":\"[F]X+J\",\"E\":\"J-VVJ-\"},\"a\":90,\"iter\":6}"),
//            Json.decodeFromString("{\"start\":\"AF\",\"rules\":{\"F\":\"+-AA-A+AF\",\"A\":\"+AFFFA\"},\"a\":90,\"iter\":4}"),
//            Json.decodeFromString("{\"start\":\"GRRG\",\"rules\":{\"F\":\"SFG\",\"G\":\"S[F]FFF-GG\",\"R\":\"SF-+\",\"S\":\"+S+-F+R\"},\"a\":45,\"iter\":7}"),
//            Json.decodeFromString("{\"start\":\"DD\",\"rules\":{\"F\":\"+F\",\"D\":\"DD-FFF[D+]\"},\"a\":45,\"iter\":7}"),
//            Json.decodeFromString("{\"start\":\"LLC\",\"rules\":{\"C\":\"SL[FF]+X+\",\"S\":\"F+FXFCX\",\"L\":\"B+F\",\"B\":\"BFC\",\"X\":\"SCFFLS\"},\"a\":166,\"iter\":6}"),
//            Json.decodeFromString("{\"start\":\"N\",\"rules\":{\"F\":\"BFK-\",\"N\":\"FBFF-BF\",\"K\":\"NF\",\"B\":\"[]D+\",\"D\":\"NFF\"},\"a\":253,\"iter\":7}"),
//            Json.decodeFromString("{\"start\":\"FM\",\"rules\":{\"F\":\"FI[-][-G]\",\"G\":\"GFG-F\",\"M\":\"G\"},\"a\":100,\"iter\":7}"),
//            Json.decodeFromString("{\"start\":\"PPFT\",\"rules\":{\"F\":\"[FP-YTY]-+\",\"P\":\"FTYPFPT\",\"Y\":\"F-\",\"T\":\"[Y]\"},\"a\":60,\"iter\":10}"),
//            Json.decodeFromString("{\"start\":\"KO\",\"rules\":{\"F\":\"+OOFK[OK]O\",\"O\":\"[FFF[F]O-]Z\",\"Z\":\"K+-\",\"K\":\"[]FOF[O]F\"},\"a\":36,\"iter\":10}"),
//            LSystemConfiguration(
//                name = "Koch Curve",
//                start = "F",
//                rules = mapOf('F' to "F+F-F-F+F"),
//                ratio = 1.0,
//                a = 90.0,
//                length = 5.0,
//                branchThicknessRatio = 1.0,
//                branchThickness = 0.5,
//                iter = 10,
//                startPosition = Vector2(width / 3.0, height / 2.0)
//            ),
//            LSystemConfiguration(
//                name = "Sierpinski Triangle",
//                start = "F-G-G",
//                rules = mapOf('F' to "F-G+F+G-F", 'G' to "GG"),
//                ratio = 1.0,
//                a = 120.0,
//                length = 20.0,
//                branchThicknessRatio = 1.0,
//                branchThickness = 0.4,
//                iter = 5,
//                startPosition = Vector2(width / 2.0, height / 2.0)
//            ),
//            LSystemConfiguration(
//                name = "Dragon Curve",
//                start = "F-G-G",
//                rules = mapOf('F' to "F+G", 'G' to "F-G"),
//                ratio = 1.0,
//                a = 90.0,
//                length = 5.0,
//                branchThicknessRatio = 1.0,
//                branchThickness = 0.5,
//                iter = 12,
//                startPosition = Vector2(width / 2.0, height / 2.0)
//            ),
//            LSystemConfiguration(
//                name = "Fractal Plant",
//                start = "X",
//                rules = mapOf('X' to "F+[[X]-X]-F[-FX]+X", 'G' to "FF"),
//                ratio = 0.7,
//                a = 27.34,
//                length = 50.0,
//                branchThicknessRatio = 0.7,
//                branchThickness = 0.5,
//                iter = 7,
//                startPosition = Vector2(width / 2.0, height.toDouble())
//            ),
//            LSystemConfiguration(
//                name = "Custom config",
//                start = "G",
//                rules = mapOf('F' to "F+[F-F-]+F-[F-F-]--", 'G' to "F-[F+F+]-F+[F+F+]++"),
//                ratio = 1.6,
//                a = 45.0,
//                length = 20.0,
//                branchThicknessRatio = 1.0,
//                branchThickness = 0.3,
//                iter = 6,
//                startPosition = Vector2(width / 2.0, height / 2.0)
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
//                    (configuration.length ?: 15.0) * (configuration.iter?.times(0.33333) ?: 2.0)
//                drawer.lineSegment(0.0, 0.0, 0.0, -length * ratio)
//                drawer.translate(0.0, -length * ratio)
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
//        extend(ControlManager()) {
//            layout {
//                button {
//                    label = "generate"
//                    // -- listen to the click event
//                    clicked {
//                        run("http://localhost:3000", object : Callback {
//                            override fun onFailure(call: Call, e: IOException) {}
//                            override fun onResponse(call: Call, response: Response) {
//                                val message = response.body()?.string()
//                                println(message)
//                                configuration = Json.decodeFromString(message!!)
//                                configuration.iter =
//                                    if (configuration.iter!! > 10) 10 else configuration.iter
//                                println(configuration)
//                            }
//                        })
//                    }
//                }
//                button {
//                    label = "iteration up"
//                    // -- listen to the click event
//                    clicked {
//                        configuration.iter = configuration.iter?.plus(1)
//                    }
//                    println(configuration)
//                }
//                button {
//                    label = "iteration down"
//                    // -- listen to the click event
//                    clicked {
//                        configuration.iter = configuration.iter?.minus(1)
//                    }
//                    println(configuration)
//                }
//                button {
//                    label = "next"
//                    // -- listen to the click event
//                    clicked {
//                        println("clicked")
//                        val incrementedConfigurationIndex = configurationIndex++
//                        if (incrementedConfigurationIndex < configs.size) {
//                            configuration = configs[incrementedConfigurationIndex]
//                        }
//                    }
//                    println(configuration)
//                }
//                button {
//                    label = "back"
//                    // -- listen to the click event
//                    clicked {
//                        println("clicked")
//                        val decrementedConfigurationIndex = configurationIndex--
//                        if (decrementedConfigurationIndex > 0) {
//                            configuration = configs[decrementedConfigurationIndex]
//                        }
//                    }
//                    println(configuration)
//                }
//            }
//        }
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
