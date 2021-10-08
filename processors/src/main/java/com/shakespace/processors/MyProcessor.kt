package com.shakespace.processors

import com.shakespace.annotations.BindView
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

class MyProcessor : AbstractProcessor() {

    /**
     * • init:被注解处理工具调用，并输入 ProcessingEnviroment 参数。ProcessingEnviroment提供很多有用的
     * 工具类，比如Elements、Types、Filer和Messager等。
     */
    override fun init(processingEnv: ProcessingEnvironment?) {
        super.init(processingEnv)
    }


    /**
     *  process:相当于每个处理器的主函数main()，在这里写你的扫描、评估和处理注解的代码，以及生
     *  成Java文件。输入参数RoundEnviroment，可以让你查询出包含特定注解的被注解元素。
     */
    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment
    ): Boolean {

        for (element in roundEnv.getElementsAnnotatedWith(BindView::class.java)) {
            if (element.kind == ElementKind.FIELD) {
                //这里用到Messager的printMessage方法来打印出注解修饰的成员变量的名称，这个名称会在Android Studio的Gradle Console窗口中打印出来。
                processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, "print message $element")
                println(element.toString())
            }
        }
        return true
    }

    /**
     * getSupportedAnnotationTypes:这是必须指定的方法，指定这个注解处理器是注册给哪个注解的。注
     * 意，它的返回值是一个字符串的集合，包含本处理器想要处理的注解类型的合法全称。
     */
    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return LinkedHashSet<String>().apply {
            add(BindView::class.java.canonicalName)
        }
    }

    /**
     * 用来指定你使用的 Java 版本，通常这里返回
     * SourceVersion.latestSupported()。
     */
    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }
}