package com.mg.order.controller

import com.mg.order.eventbus.producer.MessageProducer
import com.mg.order.model.Order
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.collections.HashMap


@RefreshScope
@RestController
class OrderController(val messageProducer: MessageProducer) : AbstractController() {

    @Value("\${app.id}")
    private val instance: String? = null

    @GetMapping(value = ["/"])
    fun instanceId() = "{ \"running_instance_id\": \"$instance\"}"

    @PostMapping(value = ["/created"])
    fun created(@RequestBody order: Order): Order {
        messageProducer.sendOrderCreatedMessage(order)
        return order
    }

    @PostMapping(value = ["/placed"])
    fun placed(@RequestBody order: Order): Order {
        messageProducer.sendOrderPlacedMessage(order)
        return order
    }

    @RequestMapping(value = ["/{id}"])
    fun getOrder(@PathVariable("id") id: Int?): Order {
        return Order(id = id ?: -1)
    }

    fun fallback(): ArrayList<Order> {
        val products = ArrayList<Order>()
        products.add(Order(id = 1, sku = "Urun YOK", description = "DESCRIPTION YOK"))
        return products
    }

}
