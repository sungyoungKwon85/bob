package com.skplanet.bob

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BobApplication

fun main(args: Array<String>) {
    runApplication<BobApplication>(*args)
}
