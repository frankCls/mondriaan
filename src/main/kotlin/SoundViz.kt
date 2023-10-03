//import ddf.minim.Minim
//import ddf.minim.analysis.FFT
//import org.openrndr.application
//import org.openrndr.extra.olive.oliveProgram
//import java.io.File
//import java.io.FileInputStream
//import java.io.InputStream
//
//fun main() = application {
//    configure {
//        width = 800
//        height = 800
//    }
//
//    val minim = Minim(object : Any() {
//        fun sketchPath(fileName: String): String {
//            return fileName
//        }
//
//        fun createInput(fileName: String): InputStream {
//            return FileInputStream(File(fileName))
//        }
//    })
//    val lineIn = minim.lineIn
//    val fft = FFT(lineIn.bufferSize(), lineIn.sampleRate())
//    oliveProgram {
//
//        extend {
//
//        }
//    }
//}