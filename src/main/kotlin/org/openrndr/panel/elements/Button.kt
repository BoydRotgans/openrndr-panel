package org.openrndr.panel.elements

import org.openrndr.color.ColorRGBa
import org.openrndr.draw.Drawer
import org.openrndr.math.Vector2
import org.openrndr.panel.style.Color
import org.openrndr.panel.style.background
import org.openrndr.panel.style.color
import org.openrndr.panel.style.position
import org.openrndr.text.Writer
import rx.subjects.PublishSubject

class Button : Element(ElementType("button")) {

    var label: String = "OK"

    class ButtonEvent(val source: Button)
    class Events(val clicked: PublishSubject<ButtonEvent> = PublishSubject.create())

    var data: Any? = null

    val events = Events()

    init {
        mouse.clicked.subscribe {
            events.clicked.onNext(ButtonEvent(this))
            it.cancelPropagation()
        }
    }

    override fun draw(drawer: Drawer) {

        computedStyle.let {

            drawer.pushTransforms()
            drawer.pushStyle()
            drawer.fill = ((it.background as? Color.RGBa)?.color ?: ColorRGBa.PINK)
            drawer.stroke = null
            drawer.strokeWeight = 0.0

            drawer.rectangle(0.0, 0.0, layout.screenWidth, layout.screenHeight)

            (root() as? Body)?.controlManager?.fontManager?.let {
                val font = it.font(computedStyle)

                val writer = Writer(drawer)
                drawer.fontMap = (font)
                val textWidth = writer.textWidth(label)
                val textHeight = font.ascenderLength

                val offset = Math.round((layout.screenWidth - textWidth) / 2.0)
                val yOffset = Math.round((layout.screenHeight / 2) + textHeight / 2.0 - 2.0) * 1.0

                drawer.fill = ((computedStyle.color as? Color.RGBa)?.color ?: ColorRGBa.WHITE)
                drawer.text(label, 0.0 + offset, 0.0 + yOffset)
                drawer.stroke = ((computedStyle.color as? Color.RGBa)?.color ?: ColorRGBa.WHITE)

                drawer.strokeWeight = 1.0
                val dx = (layout.screenWidth - textWidth) / 2.0

                drawer.lineSegment(Vector2(dx, yOffset + 3.5), Vector2(dx + textWidth, yOffset + 3.5))
            }
            //          drawer.smooth(true)
            drawer.popStyle()
            drawer.popTransforms()
        }
    }
}