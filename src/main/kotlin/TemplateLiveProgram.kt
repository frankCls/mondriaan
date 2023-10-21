import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.renderTarget
import org.openrndr.extra.fx.blur.BoxBlur
import org.openrndr.extra.olive.OliveProgram
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.ffmpeg.PlayMode
import org.openrndr.ffmpeg.VideoPlayerFFMPEG
import org.openrndr.math.Vector2
import org.openrndr.shape.Rectangle

fun main() = application {
    configure {
        width = 640
        height = 720
    }

    oliveProgram {
        val divided = 12

        val videoTarget = renderTarget(width, height) {
            colorBuffer()
            depthBuffer()
        }
        var videoPlayer:VideoPlayerFFMPEG?=null
        val devices = VideoPlayerFFMPEG.listDeviceNames()
        println(devices)
        for(name in devices) {
            videoPlayer = getVideoPlayer(name)
            if(videoPlayer != null) {
                break
            }
        }
        if (videoPlayer != null) {
            videoPlayer.play()
            println(videoPlayer.width) // 1280
            println(videoPlayer.height) //720
        }

        val aspectRatio = width / height
        val w = width / divided
        val h = height / divided
        val blur = BoxBlur()

        extend {
            val colorBuffer = videoTarget.colorBuffer(0)
//            drawer.translate(-width/3.0, -height/3.0)
            drawer.withTarget(videoTarget) {
                for (x in 0..width step w) {
                    for (y in 0..height step h) {
                        videoPlayer!!.draw(
                            drawer,
                            Rectangle(
                                Vector2(0.0, 0.0),
                                width.toDouble() ,
                                height.toDouble()
                            ),
                            Rectangle(
                                Vector2(x.toDouble(), y.toDouble()),
                                w.toDouble() ,
                                h.toDouble()
                            )
                        )

                    }
                }
            }

//            blur.apply(videoTarget.colorBuffer(0), videoTarget.colorBuffer(0))
//            drawer.translate(-width/3.0, -height/3.0)
            drawer.image(colorBuffer)
        }
    }


}

private fun getVideoPlayer(name: String) : VideoPlayerFFMPEG? {
    return try {
        VideoPlayerFFMPEG.fromDevice(name, PlayMode.BOTH)

    } catch (e: Exception) {
        null
    }
}
