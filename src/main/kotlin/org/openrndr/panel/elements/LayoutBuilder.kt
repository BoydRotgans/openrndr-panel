package org.openrndr.panel.elements

import org.openrndr.draw.Drawer
import org.openrndr.panel.ControlManager

fun layout(controlManager: ControlManager, init: Body.() -> Unit) : Body {
    val body = Body(controlManager)
    body.init()
    return body
}

fun <T : Element> Element.initElement(element: T, init: T.() -> Unit) : Element {
    element.init()
    append(element)
    return element
}

fun Element.button(id:String?=null, label:String="button", init: Button.() -> Unit):Button {
    val button = Button().apply {
        this.id = id
        this.label = label
    }
    initElement(button, init)
    return button

}
fun Element.slider(init: Slider.() -> Unit) = initElement(Slider(), init)
fun Element.toggle(init: Toggle.() -> Unit) = initElement(Toggle(), init)

fun Element.colorpicker(init: Colorpicker.() -> Unit) = initElement(Colorpicker(), init)
fun Element.colorpickerButton(init: ColorpickerButton.() -> Unit) = initElement(ColorpickerButton(), init)

fun Element.canvas(init: Canvas.() -> (Drawer)->Unit) {
    val canvas = Canvas()
    canvas.userDraw = canvas.init()
    append(canvas)
}

fun Element.dropdownButton(id:String?=null, label:String="button", init: DropdownButton.() -> Unit) = initElement(DropdownButton().apply {
    this.id = id
    this.label = label
}, init)

fun Element.envelopeButton(init: EnvelopeButton.()->Unit) = initElement(EnvelopeButton().apply {}, init)
fun Element.envelopeEditor(init: EnvelopeEditor.()->Unit) = initElement(EnvelopeEditor().apply {}, init)


fun DropdownButton.item(init:Item.() -> String) : Item {
    val item = Item()
    item.label = item.init()
    append(item)
    return item
}


fun Element.div(vararg classes:String, init: Div.() -> Unit):Div {
    val div = Div()
    classes.forEach { div.classes.add(ElementClass(it)) }
    initElement(div, init)
    return div
}


inline fun <reified T:TextElement> Element.textElement(id:String?=null, init:T.()->String) {
    val te = T::class.java.newInstance()
    te.id = id
    te.text(te.init())
    append(te)
}


fun Element.p(id:String?=null, init:P.()->String) = textElement(id, init)
fun Element.h1(id:String?=null, init:H1.()->String) = textElement(id, init)
fun Element.h2(id:String?=null, init:H2.()->String) = textElement(id, init)
fun Element.h3(id:String?=null, init:H3.()->String) = textElement(id, init)
