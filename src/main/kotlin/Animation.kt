import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.color.rgb
import org.openrndr.draw.CircleBatchBuilder
import org.openrndr.draw.circleBatch
import org.openrndr.extra.noclear.NoClear
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.math.Vector2
import kotlin.math.sqrt
import kotlin.random.Random


class Particle(pos: Vector2) {
    var position = pos
    var targ = position
    var velocity = Vector2(0.0, 0.0)
    var acceleration = Vector2(0.0, 0.01)
    var lifespan = Random.nextDouble(1.0, 10.0)
    val size = 2.0

    fun update() {
        val dir = targ - position

        acceleration = dir.normalized
        velocity += acceleration
        velocity = velocity.limit(20.0)
        position += velocity
        lifespan -= 0.01
    }

    fun display(drawer: CircleBatchBuilder) {
        drawer.fill = ColorRGBa.RED
        drawer.circle(position.x, position.y, size)
    }

    val isDead: Boolean
        get() = lifespan < 0.0
}

fun main() = application {

    configure {
        width = 800
        height = 800
    }

    oliveProgram {

        // Particle System
        val ps = MutableList(250) {
            Particle(
                Vector2(
                    Random.nextDouble(0.0, this.width.toDouble()),
                    this.height.toDouble()
                )
            )
        }

        extend(NoClear()) {
            backdrop = { drawer.clear(rgb(1.0)) }
        }
        extend {
            drawer.circles { // wrap in

                // CircleBatchBuilder
                for (p in ps.reversed()) {
                    p.targ = mouse.position
                    p.update()
                    p.display(this)

                    if (p.isDead) {
                        ps.remove(p)
                    }
                }
            }


        }
    }
}

fun Vector2.limit(max: Double): Vector2 {
    val mSq = this.squaredLength

    if (mSq > max * max) {
        return this / sqrt(mSq) * max
    }
    return this
}