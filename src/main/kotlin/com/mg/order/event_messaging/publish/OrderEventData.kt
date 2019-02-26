package com.mg.order.event_messaging.publish

import com.mg.eventbus.producer.EventData

data class OrderEventData<T>(override val topic: String,
                             override val routingKey: String,
                             override val data: T) : EventData<T>
