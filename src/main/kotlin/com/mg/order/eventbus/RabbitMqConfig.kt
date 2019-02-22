package com.mg.order.eventbus

import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class RabbitMqConfig {

    companion object {
        const val ROUTING_KEY = "product.key"
        const val EXCHANGE_ORDER_GENERAL = "order_general"
        const val EXCHANGE_ORDER_PLACED = "exchange_order_placed"
    }

    @Bean
    fun queue(): Queue = Queue(ROUTING_KEY, true)

    @Bean
    fun exhangeOrderGeneral(): TopicExchange = TopicExchange(EXCHANGE_ORDER_GENERAL)

    @Bean
    fun exhangeOrderIsPlaced(): TopicExchange = TopicExchange(EXCHANGE_ORDER_PLACED)

    @Bean
    fun binding(queue: Queue, exchange: TopicExchange) = BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY)

}