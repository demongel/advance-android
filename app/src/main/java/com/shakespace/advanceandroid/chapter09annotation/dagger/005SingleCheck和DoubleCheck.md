
1. SingleCheck 多线程可能产生多个对象

      @Override
      public T get() {
        Object local = instance;
        if (local == UNINITIALIZED) {
          // provider is volatile and might become null after the check, so retrieve the provider first
          Provider<T> providerReference = provider;
          if (providerReference == null) {
            // The provider was null, so the instance must already be set
            local = instance;
          } else {
            local = providerReference.get();
            instance = local;

            // Null out the reference to the provider. We are never going to need it again, so we can
            // make it eligible for GC.
            provider = null;
          }
        }
        return (T) local;
      }

2. DoubleCheck , 加锁，支持多线程

      @Override
      public T get() {
        Object result = instance;
        if (result == UNINITIALIZED) {
          synchronized (this) {
            result = instance;
            if (result == UNINITIALIZED) {
              result = provider.get();
              instance = reentrantCheck(instance, result);
              /* Null out the reference to the provider. We are never going to need it again, so we
               * can make it eligible for GC. */
              provider = null;
            }
          }
        }
        return (T) result;
      }