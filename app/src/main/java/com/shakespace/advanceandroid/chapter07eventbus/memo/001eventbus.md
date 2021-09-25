
1. 初始化EventBus

2. 注册事件
    通过反射订阅对象中的所有方法，找出其中的订阅方法 ，保存在FindState中
            for (Method method : methods) {
                int modifiers = method.getModifiers();
                if ((modifiers & Modifier.PUBLIC) != 0 && (modifiers & MODIFIERS_IGNORE) == 0) {
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length == 1) {
                        Subscribe subscribeAnnotation = method.getAnnotation(Subscribe.class);
                        if (subscribeAnnotation != null) {
                            Class<?> eventType = parameterTypes[0];
                            if (findState.checkAdd(method, eventType)) {
                                ThreadMode threadMode = subscribeAnnotation.threadMode();
                                findState.subscriberMethods.add(new SubscriberMethod(method, eventType, threadMode,
                                        subscribeAnnotation.priority(), subscribeAnnotation.sticky()));
                            }
                        }
                    }


            Class<?> eventType = subscriberMethod.eventType;// 来自参数类型
            // 根据subscriber（订阅者） 和subscriberMethod（订阅方法） 创建一个Subscription（订阅对象）
            Subscription newSubscription = new Subscription(subscriber, subscriberMethod);
            CopyOnWriteArrayList<Subscription> subscriptions = subscriptionsByEventType.get(eventType);
            if (subscriptions == null) {
                subscriptions = new CopyOnWriteArrayList<>();
                //将Subscriptions根据eventType封装到subscriptionsByEventType 中，
                subscriptionsByEventType.put(eventType, subscriptions);
            } else {
                if (subscriptions.contains(newSubscription)) {
                    throw new EventBusException("Subscriber " + subscriber.getClass() + " already registered to event "
                            + eventType);
                }
            }

            // 将subscribedEvents根据subscriber封装到typesBySubscriber中
            List<Class<?>> subscribedEvents = typesBySubscriber.get(subscriber);
            if (subscribedEvents == null) {
                subscribedEvents = new ArrayList<>();
                // subscribedEvents 存放事件类型的集合
                typesBySubscriber.put(subscriber, subscribedEvents);
            }

3. 发送事件时，默认会查找事件的父类
    所以eventTypes 通常包含自身和Object
                List<Class<?>> eventTypes = lookupAllEventTypes(eventClass);
                int countTypes = eventTypes.size();
                for (int h = 0; h < countTypes; h++) {
                    Class<?> clazz = eventTypes.get(h);
                    subscriptionFound |= postSingleEventForEventType(event, postingState, clazz);
                }

    找到对应的订阅对象，反射调用方法
    subscriptions = subscriptionsByEventType.get(eventClass);
    subscription.subscriberMethod.method.invoke(subscription.subscriber, event);

4. 取消订阅
     public synchronized void unregister(Object subscriber) {
            List<Class<?>> subscribedTypes = typesBySubscriber.get(subscriber);
            if (subscribedTypes != null) {
                for (Class<?> eventType : subscribedTypes) {
                    unsubscribeByEventType(subscriber, eventType);
                }
                typesBySubscriber.remove(subscriber);
            } else {
                Log.w(TAG, "Subscriber to unregister was not registered before: " + subscriber.getClass());
            }
        }


        otto 整体逻辑和eventBus 基本一致