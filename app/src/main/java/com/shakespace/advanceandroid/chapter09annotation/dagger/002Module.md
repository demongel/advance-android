
@Inject不能直接使用
    接口没有构造函数
    第三方库的类不能被标注
    构造函数中的参数必须配置


使用module 和 provide

1. 创建module
    @Module
    class CatModule {
        @Provides
        fun provideCat() = Cat()
    }

2. 关联到component
    @Component(modules = [CatModule::class])
    interface MainComponent {
        fun inject(person: Person)
    }

3. 注入对象


4. 生成类中，注入方式有所不同，会通过module里的方法进行注入
     @CanIgnoreReturnValue
      private Person injectPerson(Person instance) {
        Person_MembersInjector.injectDog(instance, new Dog());
        Person_MembersInjector.injectCat(
            instance, CatModule_ProvideCatFactory.proxyProvideCat(catModule));
        return instance;
      }

5. proivde 对应
    AModule_ProvideMonkeyFactory
   直接@Inject 对应
    Horse_Factory

需要注入依赖的目标类，需要注入的实例属性由@Inject标注。
提供依赖对象实例的工厂，用@Inject标注构造函数或定义Module这两种方式都能提供依赖实例，Dagger 2 的注解处理器会在编译时生成相应的工厂类。
    Module的优先级比@Inject标注构造函数的高，意味着 Dagger 2 会先从 Module 寻找依赖实例。
把依赖实例工厂创建的实例注入到目标类中的 Component。


@Inject 一般情况下是标注成员属性和构造函数，标注的成员属性不能是private，Dagger 2 还支持方法注入，@Inject还可以标注方法。
@Provides 只能标注方法，必须在 Module 中。
@Module 用来标注 Module 类
@Component 只能标注接口或抽象类，声明的注入接口的参数类型必须和目标类一致。