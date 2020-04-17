package component
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.*
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.*
import kotlin.browser.document

interface AppLessonProps : RProps {
    var newlesson: (String) -> Any }

val addLessons =
    functionalComponent<AppLessonProps> { props ->
    div {
        h3 { +"add name lesson"}
            input(type = InputType.text, name = "Key 1")
            {
                attrs.id = "Lesson"
            }
            input(type = InputType.submit, name = "Key 2") {
                attrs.onClickFunction =
                    {
                     val lesson = document.getElementById("Lesson")
                      as HTMLInputElement
                     val tmp = lesson.value
                      props.newlesson(tmp)
                    }
            }
        }
    }

fun RBuilder.addlesson(
    newlesson: (String) -> Any
) = child(addLessons){
    attrs.newlesson= newlesson
}

