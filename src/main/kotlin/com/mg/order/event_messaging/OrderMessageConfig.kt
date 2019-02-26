package com.mg.order.event_messaging

import com.mg.eventbus.RabbitMqConfig
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OrderMessageConfig : RabbitMqConfig() {

    companion object {
        const val ROUTING_KEY_ORDER_ALL = "order.#"
        const val QUEUE_ORDER_ALL = "order.all"
        const val QUEUE_ORDER_CREATED = "order.created"
        const val QUEUE_ORDER_PLACED = "order.placed"
        const val EXCHANGE_ORDER = "order"
    }

    @Bean
    fun exchangeOrder(): TopicExchange = TopicExchange(EXCHANGE_ORDER)

    @Bean
    fun queueOrderCreated() = Queue(QUEUE_ORDER_CREATED)

    @Bean
    fun queueOrderPlaced() = Queue(QUEUE_ORDER_PLACED)

    @Bean
    fun queueOrderAll() = Queue(QUEUE_ORDER_ALL)

    @Bean
    fun bindingCreated() = BindingBuilder.bind(queueOrderCreated()).to(exchangeOrder()).with(QUEUE_ORDER_CREATED)

    @Bean
    fun bindingPlaced() = BindingBuilder.bind(queueOrderPlaced()).to(exchangeOrder()).with(QUEUE_ORDER_PLACED)

    @Bean
    fun bindingAll() = BindingBuilder.bind(queueOrderAll()).to(exchangeOrder()).with(ROUTING_KEY_ORDER_ALL)

}