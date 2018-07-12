package org.openrndr.panel.elements

import org.openrndr.color.ColorRGBa
import org.openrndr.draw.Drawer
import org.openrndr.draw.FontImageMap
import org.openrndr.math.Vector2
import org.openrndr.panel.style.*
import org.openrndr.shape.Rectangle
import org.openrndr.text.Writer

class TextNode(var text: String) : Element(ElementType("text")) {


    override fun draw(drawer: Drawer) {

        computedStyle.let { style ->

            style.color.let {
                val fill = (it as? Color.RGBa)?.color ?: ColorRGBa.WHITE
                drawer.fill = (fill)
            }
            val fontMap = (root() as Body).controlManager.fontManager.font(computedStyle)
            val writer = Writer(drawer)
            drawer.fontMap = (fontMap)

            writer.box= Rectangle(Vector2(layout.screenX * 0.0, layout.screenY * 0.0), layout.screenWidth, layout.screenHeight)
            writer.newLine()
            writer.text(text)
        }

    }

    fun sizeHint(): Rectangle {
        computedStyle.let { style ->
            val fontUrl = (root() as? Body)?.controlManager?.fontManager?.resolve(style.fontFamily)?:"broken"
            val fontSize = (style.fontSize as? LinearDimension.PX)?.value?: 14.0
            val fontMap = FontImageMap.fromUrl(fontUrl, fontSize)

            val writer = Writer(null)

            writer.box = Rectangle(layout.screenX,
                       layout.screenY,
                       layout.screenWidth,
                       layout.screenHeight)

            writer.drawStyle.fontMap = fontMap
            writer.newLine()
            writer.text(text, visible = false)

            return Rectangle(layout.screenX,
                             layout.screenY,
                             layout.screenWidth,
                    (writer.cursor.y - layout.screenY) - fontMap.descenderLength*2)
        }

    }

    override fun toString(): String {
        return "TextNode(id='$id',text='$text')"
    }


}

class H1 : TextElement(ElementType("h1"))
class H2 : TextElement(ElementType("h2"))
class H3 : TextElement(ElementType("h3"))
class H4 : TextElement(ElementType("h4"))
class H5 : TextElement(ElementType("h5"))

class P : TextElement(ElementType("p"))

abstract class TextElement(et: ElementType) : Element(et) {
    fun text(text: String) {
        append(TextNode(text))
    }
}