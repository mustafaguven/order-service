package com.mg.order.controller.command

import com.mg.order.controller.AbstractController
import com.mg.order.event_messaging.publish.OrderMessageProducer
import com.mg.order.model.Order
import com.mg.order.services.ProductService
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.hateoas.ResourceSupport
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RefreshScope
@RestController
@RequestMapping("/command")
@ResponseStatus(HttpStatus.ACCEPTED)
class OrderCommandController(val messageProducer: OrderMessageProducer,
                             val productService: ProductService) : AbstractController() {

    @Value("\${app.id}")
    private val instance: String? = null

    @PostMapping(value = ["/create"])
    fun create(@RequestBody order: Order?): ResponseEntity<Order> {
        messageProducer.sendOrderCreatedMessage(order)
        return ResponseEntity.ok(order)
    }

    @PostMapping(value = ["/place"])
    fun place(@RequestBody order: Order?): ResponseEntity<Order> {
        messageProducer.sendOrderPlacedMessage(order)
        return ResponseEntity.ok(order)
    }

    @RequestMapping(value = ["/{id}"])
    fun getOrder(@PathVariable("id") id: Int?): ResponseEntity<Order> {
        return ResponseEntity.ok(Order(id = id ?: -1))
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getCommands(): ResponseEntity<ResourceSupport> {
        val commands = ResourceSupport()
        commands.add(linkTo(methodOn(this::class.java).create(null)).withRel("create").withTitle("POST"))
        commands.add(linkTo(methodOn(this::class.java).place(null)).withRel("place").withTitle("POST"))
        return ResponseEntity.ok(commands)
    }


}
