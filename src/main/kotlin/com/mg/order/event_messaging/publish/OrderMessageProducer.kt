package com.mg.order.event_messaging.publish

import com.mg.eventbus.producer.MessageProducer
import com.mg.order.event_messaging.OrderMessageConfig
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class OrderMessageProducer(rabbit: RabbitTemplate) : MessageProducer(rabbit) {

    fun <T> sendOrderCreatedMessage(t: T) {
        sendMessage(OrderEventData(OrderMessageConfig.EXCHANGE_ORDER,
                OrderMessageConfig.QUEUE_ORDER_CREATED,
                t)
        )
    }

    fun <T> sendOrderPlacedMessage(t: T) {
        sendMessage(OrderEventData(OrderMessageConfig.EXCHANGE_ORDER, OrderMessageConfig.QUEUE_ORDER_PLACED, t))
    }
}