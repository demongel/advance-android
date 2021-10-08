
1. 在 Dagger 2 中 Component 的组织关系分为两种：

    依赖关系，一个 Component 依赖其他 Component 公开的依赖实例，用 Component 中的dependencies声明。

    继承关系，一个 Component 继承（也可以叫扩展）某 Component 提供更多的依赖，SubComponent 就是继承关系的体现。

2. @Component(dependencies = [ZooOneComponent::class])
   interface ZooTwoComponent {
       fun inject(zooTwo: ZooTwo)
   }

   @Component(modules = [TigerModule::class])
   interface ZooOneComponent {
       fun inject(zooOne: ZooOne)

       // for ZooTwoComponent
       fun provideTiger(): Tiger
   }

   生成的ZooTwoComponent子类中，会调用zooOneComponent的方法进行注入

      @CanIgnoreReturnValue
      private ZooTwo injectZooTwo(ZooTwo instance) {
        ZooTwo_MembersInjector.injectTiger(
            instance,
            Preconditions.checkNotNull(
                zooOneComponent.provideTiger(),
                "Cannot return null from a non-@Nullable component method"));
        return instance;
      }

3.
    因为 BComponent和 AComponent是依赖关系，如果AComponent声明了作用域的话，那么BComponent也必须声明（反之可以），
    而且它们的 Scope 不能相同，
    并且两个都有作用域的情况下 @Singleton 修饰的 Component （BComponent）不能依赖其他的 Component。

    被依赖的 Component 需要把暴露的依赖实例用显式的接口声明。
    依赖关系中的 Component 的 Scope 不能相同，因为它们的生命周期不同。


4.继承关系
    ChildComponent 在 ParentComponent之中，ChildComponent子承父业，可以访问 ParentComponent 的依赖，而 ParentComponent只知道 ChildComponent 是它的子类，可以访问 SubComponent.Builder，却无法访问 SubComponent 中的依赖

第一种写法：

    1. 父类正常声明
    2. 子类需要用 @Subcomponent 修饰， 提供返回ChildComponent的方法

                @Subcomponent.Builder
                interface Builder {
                    fun build(): ChildComponent
                }

    3.父类中增加创建子类Builder的接口
            // 用来创建childComponent
            fun childComponent(): ChildComponent.Builder

    4. 父类对应的Module需要添加 subComponents ， 不添加也能用，但是加了一定要有步骤2，否则报错
            @Module(subcomponents = [ChildComponent::class])
            class CarModule {
                @Provides
                fun provideCar() = Car()
            }

    5. 在目标类中注入
         DaggerParentComponent.builder().build().childComponent().build().inject(this)
         目标类中可以注入父类绑定module 中的对象

第二种写法：
    1. 子类只保留inject方法

        @Subcomponent(modules = [BikeModule::class])
       interface ChildComponent {
           fun inject(child: Child)
       }

    2. 父类提供一个返回ChildComponent的方法
        @Component(modules = [CarModule::class])
        interface ParentComponent {
            fun inject(parent: Parent)

            fun childComponent2(): ChildComponent
        }

    3. 使用
         DaggerParentComponent.create().childComponent2().inject(this)

5. 重复的Module

    重复的 Module
    当相同的 Module 注入到 parent Component 和它的 SubComponent 中时，则每个 Component 都将自动使用这个 Module 的同一实例。
    也就是如果在 SubComponent.Builder 中调用相同的 Module 或者在返回 SubComponent 的抽象工厂方法中以重复 Module 作为参数时，会出现错误。（前者在编译时不能检测出，是运行时错误）

    @Component(modules = {RepeatedModule.class, ...})
    interface ComponentOne {
      ComponentTwo componentTwo(RepeatedModule repeatedModule); // 编译时报错
      ComponentThree.Builder componentThreeBuilder();
    }

    @Subcomponent(modules = {RepeatedModule.class, ...})
    interface ComponentTwo { ... }

    @Subcomponent(modules = {RepeatedModule.class, ...})
    interface ComponentThree {
      @Subcomponent.Builder
      interface Builder {
        Builder repeatedModule(RepeatedModule repeatedModule);
        ComponentThree build();
      }
    }

    DaggerComponentOne.create().componentThreeBuilder()
        .repeatedModule(new RepeatedModule()) // 运行时报错 UnsupportedOperationException!
        .build();

-------------------
一些错误
    1。 第二种写法中，子类不需要 @Subcomponent.Builder

        /Users/ez44446/003_DEMO/001MyApp/KotlinMVVM/app/build/tmp/kapt3/stubs/debug/com/shakespace/template/z_demo/a_dagger/subcomponent/ParentComponent.java:16:
        error: Components may not have factory methods for subcomponents that define a builder.
            public abstract com.shakespace.advanceandroid.chapter09annotation.dagger.subcomponent.ChildComponent childComponent2();

    2。不能同时存在以下两种方法：
            fun childComponent(): ChildComponent.Builder
           fun childComponent2(): ChildComponent

           /Users/ez44446/003_DEMO/001MyApp/KotlinMVVM/app/build/tmp/kapt3/stubs/debug/com/shakespace/template/z_demo/a_dagger/subcomponent/ParentComponent.java:10:
           error: Only one method can create a given subcomponent. com.shakespace.advanceandroid.chapter09annotation.dagger.subcomponent.ChildComponent is created by: [childComponent(), childComponent2()]

    3。使用方法1时，给module添加了 @Module(subcomponents = [ChildComponent::class]) ，但是ChildComponent 没有@Subcomponent.Builder

        /Users/ez44446/003_DEMO/001MyApp/KotlinMVVM/app/build/tmp/kapt3/stubs/debug/com/shakespace/template/z_demo/a_dagger/subcomponent/CarModule.java:6:
        error: com.shakespace.advanceandroid.chapter09annotation.dagger.subcomponent.ChildComponent doesn't have a @Subcomponent.Builder, which is required when used with @Module.subcomponents
        @dagger.Module(subcomponents = {com.shakespace.advanceandroid.chapter09annotation.dagger.subcomponent.ChildComponent.class})
        ^/Users/ez44446/003_DEMO/001MyApp/KotlinMVVM/app/build/tmp/kapt3/stubs/debug/com/shakespace/template/z_demo/a_dagger/subcomponent/ParentComponent.java:9:
        error: com.shakespace.advanceandroid.chapter09annotation.dagger.subcomponent.CarModule has errors
        @dagger.Component(modules = {com.shakespace.advanceandroid.chapter09annotation.dagger.subcomponent.CarModule.class})

 com.shakespace.advanceandroid.chapter09annotation.dagger.subcomponent.ChildComponent doesn't have a @Subcomponent.Builder, which is required when used with @Module.subcomponents
@dagger.Module(subcomponents = {com.shakespace.advanceandroid.chapter09annotation.dagger.subcomponent.ChildComponent.class})
^/Users/ez44446/003_DEMO/001MyApp/KotlinMVVM/app/build/tmp/kapt3/stubs/debug/com/shakespace/template/z_demo/a_dagger/subcomponent/ParentComponent.java:9: error: com.shakespace.advanceandroid.chapter09annotation.dagger.subcomponent.CarModule has errors
@dagger.Component(modules = {com.shakespace.advanceandroid.chapter09annotation.dagger.subcomponent.CarModule.class})