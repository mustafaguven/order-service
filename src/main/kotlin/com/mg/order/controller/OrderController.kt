package com.mg.order.controller

import com.mg.order.controller.command.OrderCommandController
import com.mg.order.event_messaging.publish.OrderMessageProducer
import com.mg.order.model.Order
import com.mg.order.model.Product
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
@ResponseStatus(HttpStatus.ACCEPTED)
class OrderController(val messageProducer: OrderMessageProducer,
                      val productService: ProductService) : AbstractController() {

    @Value("\${app.id}")
    private val instance: String? = null

    @GetMapping(value = ["/id"])
    fun instanceId(): ResponseEntity<String> {
        val id = "{ \"running_instance_id\": \"$instance\"}"
        return ResponseEntity.ok(id)
    }

    @RequestMapping(value = ["/{id}"])
    fun getOrder(@PathVariable("id") id: Int?): ResponseEntity<Order> {
        return ResponseEntity.ok(Order(id = id ?: -1))
    }

    @RequestMapping(value = ["/{id}/products"])
    fun getProducts(@PathVariable("id") id: Int?): ResponseEntity<Product>  {
        return ResponseEntity.ok(productService.getProductsByOrderId(id))
    }

    fun fallback(): Product {
        return Product(1, "FAIL", "--", "no desc")
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getLinks(): ResponseEntity<ResourceSupport> {
        val commands = ResourceSupport()
        commands.add(linkTo(methodOn(OrderCommandController::class.java).getCommands()).withRel("command").withTitle("GET"))
        commands.add(linkTo(methodOn(this::class.java).instanceId()).withRel("instanceId").withType("GET"))
        commands.add(linkTo(methodOn(this::class.java).getOrder(null)).withRel("getOrder").withTitle("GET"))
        commands.add(linkTo(methodOn(this::class.java).getProducts(null)).withRel("getProducts").withTitle("GET"))
        return ResponseEntity.ok(commands)
    }

}