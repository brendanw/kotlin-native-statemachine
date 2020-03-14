package sample

import kotlinx.coroutines.GlobalScope

fun hello(): String = "Hello, Kotlin/Native!"

fun main() {
    println(hello())
}
