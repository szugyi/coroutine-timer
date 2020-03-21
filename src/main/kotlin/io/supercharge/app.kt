package io.supercharge

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit

fun main() {
    println("Hello World!")

    val start = LocalDateTime.now()
    println("Start at: $start")

    runBlocking {
        counterFlow.collect { elapsedSeconds ->
            println("collect $elapsedSeconds called at: ${LocalDateTime.now()} - on: ${Thread.currentThread().name}")

            delay(1500)

            println("collect delay returned at: ${LocalDateTime.now()}")
        }
    }

    val end = LocalDateTime.now()
    println("Ended at: $end")
    println("Elapsed millis: ${ChronoUnit.MILLIS.between(start, end)}ms")
}

private val counterFlow = flow {
    repeat(MAX_SECONDS_RETRY) {elapsedSeconds ->
        println("emit $elapsedSeconds called at: ${LocalDateTime.now()}  - on: ${Thread.currentThread().name}")

        emit(elapsedSeconds)

        println("emit returned at: ${LocalDateTime.now()}")

        delay(TimeUnit.SECONDS.toMillis(1L))

        println("emit delay returned at: ${LocalDateTime.now()}")
    }

    println("emit $MAX_SECONDS_RETRY called at: ${LocalDateTime.now()}  - on: ${Thread.currentThread().name}")

    emit(MAX_SECONDS_RETRY)

    println("emit returned at: ${LocalDateTime.now()}")
}.flowOn(Dispatchers.Default)

private const val MAX_SECONDS_RETRY = 3