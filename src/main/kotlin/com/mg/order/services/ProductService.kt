package com.mg.order.services

import com.mg.order.model.Product
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(value = "product-service")
interface ProductService {

    @RequestMapping(method = [RequestMethod.GET], value = ["/{id}"])
    fun getProductsByOrderId(@PathVariable("id") id: Int?): Product
}