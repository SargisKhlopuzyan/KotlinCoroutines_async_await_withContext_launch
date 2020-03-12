package app.sargis.khlopuzyan.kotlincoroutines_async_await_withcontext_launch

import kotlinx.coroutines.*
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by Sargis Khlopuzyan, on 3/11/2020.
 *
 * @author Sargis Khlopuzyan (sargis.khlopuzyan@fcc.am)
 */

fun main() {
//    exampleBlocking()
//    exampleBlockingDispatcher()
//    exampleLaunchGlobal()
//    exampleLaunchGlobalWaiting()
//    exampleLaunchCoroutineScope()
//    exampleLaunchCoroutineScope2()
//    exampleLaunchCoroutineScope3()
//    exampleAsyncAwait()
//    exampleAsyncAwait1()
    exampleWithContext()
    println("finishing - main")
}

suspend fun printlnDelay(message: String) {
    delay(2000)
    println(message)
}

suspend fun calculateHardThings(startNum: Int): Int {
    delay(3000)
    return startNum * 10
}


fun exampleBlocking() = runBlocking {
    println("one - from thread ${Thread.currentThread().name}")
    printlnDelay("two - from thread ${Thread.currentThread().name}")
    println("three - from thread ${Thread.currentThread().name}")
}

fun exampleBlockingDispatcher() {
    runBlocking(Dispatchers.Default) {
        println("one - from thread ${Thread.currentThread().name}")
        printlnDelay("two - from thread ${Thread.currentThread().name}")
    }

    println("three - from thread ${Thread.currentThread().name}")
    println("finishing * exampleBlockingDispatcher \n")
}

fun exampleLaunchGlobal() = runBlocking {
    println("one - from thread ${Thread.currentThread().name}")

    GlobalScope.launch {
        printlnDelay("two - from thread ${Thread.currentThread().name}")
    }

    println("three - from thread ${Thread.currentThread().name}")

    delay(3000)
    println("finishing * exampleLaunchGlobal \n")
}

fun exampleLaunchGlobalWaiting() = runBlocking {
    println("one - from thread ${Thread.currentThread().name}")

    val job = GlobalScope.launch {
        printlnDelay("two - from thread ${Thread.currentThread().name}")
    }

    println("three - from thread ${Thread.currentThread().name}")

    job.join()
    println("finishing")
}

fun exampleLaunchCoroutineScope() = runBlocking {
    println("one - from thread ${Thread.currentThread().name}")

    launch {
        printlnDelay("two - from thread ${Thread.currentThread().name}")
    }

    println("three - from thread ${Thread.currentThread().name}")
    println("finishing : exampleLaunchCoroutineScope\n")
}

fun exampleLaunchCoroutineScope2() = runBlocking {
    println("one - from thread ${Thread.currentThread().name}")

    launch(Dispatchers.Main) {
        printlnDelay("two - from thread ${Thread.currentThread().name}")
    }

    println("three - from thread ${Thread.currentThread().name}")
    println("finishing : exampleLaunchCoroutineScope2")
}

fun exampleLaunchCoroutineScope3() = runBlocking {
    println("one - from thread ${Thread.currentThread().name}")

    val customDispatcher = Executors.newFixedThreadPool(2).asCoroutineDispatcher()

    launch(customDispatcher) {
        printlnDelay("two - from thread ${Thread.currentThread().name}")
    }

    println("three - from thread ${Thread.currentThread().name}")
    println("finishing : exampleLaunchCoroutineScope3")

    (customDispatcher.executor as ExecutorService).shutdown()
}

fun exampleAsyncAwait() = runBlocking {

    val startTime = System.currentTimeMillis()

    val deferred1 = async { calculateHardThings(10) }.await()
    println("deferred1: $deferred1")
    val deferred2 = async { calculateHardThings(20) }.await()
    println("deferred2: $deferred2")
    val deferred3 = async { calculateHardThings(30) }.await()
    println("deferred3: $deferred3")

    val sum = deferred1 + deferred2 + deferred3
    println("async.await result: $sum")

    val endTime = System.currentTimeMillis()
    println("Time taken: ${endTime - startTime}")

    println("finishing : exampleAsyncAwait")
}

fun exampleAsyncAwait1() = runBlocking {

    val startTime = System.currentTimeMillis()

    val deferred1 = async { calculateHardThings(10) }
    val deferred2 = async { calculateHardThings(20) }
    val deferred3 = async { calculateHardThings(30) }

    val sum = deferred1.await() + deferred2.await() + deferred3.await()
    println("async.await result: $sum")

    val endTime = System.currentTimeMillis()
    println("Time taken: ${endTime - startTime}")

    println("finishing : exampleAsyncAwait1")
}

fun exampleWithContext() = runBlocking {

    val startTime = System.currentTimeMillis()

    val result1 = withContext(Dispatchers.Default) { calculateHardThings(10) }
    val result2 = withContext(Dispatchers.Default) { calculateHardThings(20) }
    val result3 = withContext(Dispatchers.Default) { calculateHardThings(30) }

    val sum = result1 + result2 + result3
    println("async.await result: $sum")

    val endTime = System.currentTimeMillis()
    println("Time taken: ${endTime - startTime}")

    println("finishing : exampleWithContext")
}