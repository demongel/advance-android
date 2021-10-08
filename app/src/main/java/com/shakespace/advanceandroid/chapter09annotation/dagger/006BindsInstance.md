
1.
@Component.Builder is missing setters for required modules or components:
 [com.shakespace.advanceandroid.chapter09annotation.dagger.bindinstance.BModule]

 使用bindsinstance ， module不需要再接收参数


 2. BindsInstance中创建过的参数，不需要再次创建，也不需要在provide中提供方法，可以直接使用
      //   DaggerMainComponent 有一个field接收该参数
      private DaggerMainComponent(CatModule catModuleParam, AModule aModuleParam, Mouse mouseParam) {
        this.catModule = catModuleParam;
        this.mouse = mouseParam;
        initialize(catModuleParam, aModuleParam, mouseParam);
      }

     // 注入的时候直接使用该成员变量
     @CanIgnoreReturnValue
     private Human2 injectHuman2(Human2 instance) {
       Human2_MembersInjector.injectMouse(instance, mouse);
       Human2_MembersInjector.injectMouse2(instance, mouse);
       return instance;
     }



 Component可以有一个使用@Component.Builder注解的静态抽象类或接口，如果有必须满足以下条件：
     必须有一个抽象无参方法返回Component，通常为build()方法
     所有抽象方法必须有一个参数，返回值为void、Builder或Builder的父类型
