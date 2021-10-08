对于抽象类和接口的注入，我们还可以使用注解@Binds实现，使用注解@Binds有如下要求：

    必须用在Module里的抽象方法
    必须为抽象方法
    必须有一个参数，参数类型可以分配给方法的返回类型


    kotlin 中使用binds intoSet 会报错 找不到provide方法， 需要添加@JvmSuppressWildcards 在注入的地方
    @JvmSuppressWildcards
    @Inject
    lateinit var animals: Set<People>


    标记在泛型位置
    lateinit var animals: Set<@JvmSuppressWildcards People>

 