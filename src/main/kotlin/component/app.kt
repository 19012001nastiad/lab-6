package component

import data.*
import org.w3c.dom.events.Event
import react.*
import react.dom.h1

interface AppProps : RProps {
    var students: Array<Student> }

interface AppState : RState {
    var lessons: Array<Lessons>
    var presents: Array<Array<Boolean>>
    var newlesson: String }

class App : RComponent<AppProps, AppState>() {
    override fun componentWillMount() {
        state.lessons = LessonsList
        state.presents = Array(state.lessons.size) {
            Array(props.students.size) { false }
        }

    }
    fun newLesson (): (String) -> Any = { new_lesson ->
        setState {
            lessons+= Lessons(new_lesson)
            presents+= arrayOf(Array(props.students.size) { false })
        }
    }

    override fun RBuilder.render() {
        h1 { +"App" }
        addlesson(newLesson())
        lessonListFull(
            state.lessons,
            props.students,
            state.presents,
            onClickLessonFull
        )
        studentListFull(
            state.lessons,
            props.students,
            transform(state.presents),
            onClickStudentFull
        )
    }

    fun transform(source: Array<Array<Boolean>>) =
        Array(source[0].size){row->
            Array(source.size){col ->
                source[col][row]
            }
        }

    fun onClick(indexLesson: Int, indexStudent: Int) =
        { _: Event ->
            setState {
                presents[indexLesson][indexStudent] =
                    !presents[indexLesson][indexStudent]
            }
        }

    val onClickLessonFull =
        { indexLesson: Int ->
            { indexStudent: Int ->
                onClick(indexLesson, indexStudent)
            }
        }

    val onClickStudentFull =
        { indexStudent: Int ->
            { indexLesson: Int ->
                onClick(indexLesson, indexStudent)
            }
        }

}

fun RBuilder.app(
    students: Array<Student>
) = child(App::class) {
    attrs.students = students
}