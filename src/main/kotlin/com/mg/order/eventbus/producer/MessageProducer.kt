package com.mg.order.eventbus.producer

import com.mg.order.eventbus.RabbitMqConfig
import org.springframework.amqp.rabbit.connection.AbstractConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class MessageProducer(val rabbitTemplate: RabbitTemplate) {

    private fun <T> sendMessage(routingKey: String, t: T) {
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_ORDER, routingKey, t)
    }

    fun <T> sendOrderCreatedMessage(t: T) {
        sendMessage(RabbitMqConfig.QUEUE_ORDER_CREATED, t)
    }

    fun <T> sendOrderPlacedMessage(t: T) {
        sendMessage(RabbitMqConfig.QUEUE_ORDER_PLACED, t)
    }

}