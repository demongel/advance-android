
1、@Component 关联的@Module中的任何一个@Provides有@scope，则该整个@Component要加上这个scope。
    ...com/shakespace/template/z_demo/a_dagger/component/MainComponent.java:7: error: [Dagger/IncompatiblyScopedBindings]
     com.shakespace.advanceandroid.chapter09annotation.dagger.component.MainComponent (unscoped) may not reference scoped bindings:


    或者可以在Component上添加 @Singleton, 因为
    @Singleton的组件不能依赖其他scope的组件，但是其他scope的组件可以依赖@Singleton组件

 2. 如果@Module 中有两个不同的 Scope, @Component 也要同时添加多个Scope

  ...com/shakespace/template/z_demo/a_dagger/component/MainComponent.java:8: error: [Dagger/IncompatiblyScopedBindings]
  com.shakespace.advanceandroid.chapter09annotation.dagger.component.MainComponent scoped with @com.shakespace.advanceandroid.chapter09annotation.dagger.annotation.AScope may not reference bindings with different scopes:

  ```
    @Module
    class AModule {
        @AScope
        @Provides
        fun provideSheep() = Sheep()

        @BScope
        @Provides
        fun provideMonkey() = Monkey()
    }
   ```

3. @Reusable 作用域只需要标记目标类或 provide 方法，不用标记 Component。

