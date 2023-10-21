//import org.openrndr.WindowMultisample
//import org.openrndr.application
//import org.openrndr.draw.DepthTestPass
//import org.openrndr.draw.DrawPrimitive
//import org.openrndr.draw.loadImage
//import org.openrndr.extras.meshgenerators.boxMesh
//
//import org.openrndr.draw.shadeStyle
//import org.openrndr.ffmpeg.loadVideo
//import org.openrndr.math.Vector2
//import org.openrndr.math.Vector3
//
//fun main() = application {
//    configure {
//        width = 640
//        height = 360
//        multisample = WindowMultisample.SampleCount(2)
//    }
//    program {
//        val cube = boxMesh()
//        var rot = Vector2(45.0)
////        val tex = loadImage("data/images/pm5544.png")
//        val tex = loadVideo("data/images/pm5544.png")
//
//        extend {
//            drawer.perspective(60.0, width * 1.0 / height, 0.01, 1000.0)
//
//            drawer.depthWrite = true
//            drawer.depthTestPass = DepthTestPass.LESS_OR_EQUAL
//            drawer.shadeStyle = shadeStyle {
//                fragmentTransform = "x_fill = texture(p_tex, va_texCoord0.xy);"
//                parameter("tex", tex)
//            }
//            drawer.translate(0.0, 0.0, -150.0)
//            drawer.rotate(Vector3.UNIT_X, rot.x)
//            drawer.rotate(Vector3.UNIT_Y, rot.y)
//            drawer.scale(90.0)
//            drawer.vertexBuffer(cube, DrawPrimitive.TRIANGLES)
//        }
//
//        mouse.dragged.listen {
//            val rate = 0.5
//            rot += it.dragDisplacement.yx * rate
//        }
//    }
//}