package com.mg.order.controller

import com.mg.order.inlines.logger
import lombok.extern.slf4j.Slf4j


@Slf4j
abstract class AbstractController {

    companion object {
        val log = logger(this)
    }

}