package com.mg.order.eventbus.producer

import com.mg.order.eventbus.RabbitMqConfig
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class MessageProducer {

    @Autowired
    private lateinit var rabbitTemplate: RabbitTemplate

    fun sendMessage(message: String) {
        rabbitTemplate.convertAndSend(RabbitMqConfig.ROUTING_KEY, message)
    }

}