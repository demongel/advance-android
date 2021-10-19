
1. presenter 需要有View层的引用
    为了避免内存泄漏，需要考虑使用弱引用，并注意及时销毁

2. presenter 需要有获取数据的能力

3. 拿到数据后 调用view层进行展示

4. 通常View层，要有一个BaseView, 实现共用方法，通常是显示或隐藏loader
    然后再实现Contract.View  实现当前页面需要实现的方法 ， 通常是展示数据

    Presenter 实现 Contract.Presenter 通常是获取数据

