package com.takuchan.milkypublisher.graphic

import java.util.concurrent.Executor
import java.util.concurrent.atomic.AtomicBoolean
import javax.annotation.Nonnull

class ScopedExecutor constructor(
    executor: Executor
): Executor  {
    private var executors: Executor? = null
    private val shutdown = AtomicBoolean()

    init {
        executors = executor
    }
    override fun execute(p0: Runnable?) {
        if (shutdown.get()){
            return
        }

        executors!!.execute {

            // Check again in case it has been shut down in the mean time.
            if (shutdown.get()) {
                return@execute
            }
            p0?.run()
        }
    }
    fun shutdown() {
        shutdown.set(true)
    }



}