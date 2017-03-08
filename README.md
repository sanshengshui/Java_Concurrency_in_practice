非原子的64位操作
<br/>
当线程在没有同步的情况下读取变量时，可能会得到一个失效值，但至少这个值是由之前某个
线程设置的值，而不是一个随机值，这种安全性保证也被称为最低安全性(out-of-thin-airsafety)。
<br/>
最低安全性适用于绝大多数变量,但是存在一个例外:非volatile类型的64位数值变量(double和long)。
Java内存模型要求,变量的读取操作和写入操作都是必须是原子操作的，但对于非volatile类型的long和
double变量,JVM允许将64位的读操作或写操作分解为俩个32位的操作。当读取一个非volatile类型的
long变量时，如果对该变量的读操作和写操作在不同的线程中执行，那么很可能会读取到某个值的高32位和另
一个值的低32位。因此，即使不考虑失效数据问题，在多线程程序中使用共享且可变的long和double等类型的变量
也是不安全的，除非用关键字volatile来声明它们，或者用锁保护起来。
<br/>
<br/>
Volatile变量
Java语言提供了一种稍弱的同步机制,即volatile变量,用来确保将变量的更新操作通知给其他线程。
当把变量声明为volatile类型后,编译器与运行时都会注意到这个变量是共享的,因此不会将该变量
上的操作与其他内存操作一起重排序。volatile变量不会被缓存在寄存器或者对其他处理器不可见的地方，
因此在读取volatile类型的变量时总会返回最新写入的值。
<br/>
理解volatile变量的一种有效方法是,将它们的行为想象成程序中SynchronizedInteger的类似行为，并将
volatile变量的读操作和写操作分别替换为get方法和set方法。然而，在访问volatile变量时不会执行加锁
操作,因此也就不会使执行线程阻塞，因此volatile变量是一种比synchronized关键字更轻量级的同步机制。
<br/>
volatile变量的一种典型用法:
<br/>
volatile boolean asleep;<br/>
    while(!asleep)<br/>
countSomeSheep();
<br/>
检查某个状态标记以判断是否退出循环。
<br/>
调试小知识：对于服务器应用程序，无论在开发阶段还是在测试阶段，当启动JVM时一定都要指定-server命令行
选项。server模式的JVM将比client模式的JVM进行更多的优化,例如将循环中未被修改的变量提升到循环外部,
因此在开发环境(client模式的JVM）中能正确运行的代码，可能会在部署环境(server模式的JVM)中运行失败。
例如，如果在以上程序"忘记"把asleep变量声明为volatile类型，那么server模式的JVM会将asleep的判断
条件提升到循环体外部(这将导致一个无限循环),但client模式的JVM不会这么做。在解决这个问题的开销运小于解决
在应用环境出现无限循环的开销。
<br/>
当且仅当满足以下条件时，才应该使用volatile变量:
<br/>
对变量的写入操作不依赖变量的当前值,或者你能确保只有单个线程更新变量的值.
<br/>
该变量不会与其他状态变量一起纳入不变性条件中。
<br/>
在访问变量时不需要加锁.
<br/>
<br/>
3.2<h2>发布与逸出</h2>
"发布"一个对象的意思是指,使对象能够在当前作用域之外的代码中使用。例如，将一个指向该对象的保存到其他代码可以访问的方法，
或者在某一个非私有的方法中返回该引用，或者将该引用传递到其他类的方法中。在许多情况中，我们要确保对象及其内部状态不被发布。而在某些情况下，我们又需要发布某个对象，但如果在发布时要确保线程安全性，则可能需要同步。发布内部状态可能会破坏封装性，并使得
程序难以维持不变性条件。例如,如果在对象构造完成之前就发布该对象，就会破坏线程安全性。当某个不应该发布的对象被发布时，
这种情况就被称为逸出。
<br/>
发布对象的最简单方法是将对象的引出保存到一个公有的静态变量中，以便任何类和线程都能看到该对象，
<br/>
第二种：发布对象或其内部状态的机制就是发布一个内部的类实例
<br/>
使用封装的最主要原因:封装能够使得对程序的正确性进行分析变得可能，并使得无意中破幻设计约束条件变得更难。
<br/>
3.3.1
<br/>
Ad-hoc线程封
<br/>
由于Ad-hoc线程封闭技术的脆弱性，因此在程序中尽量少使用它，在可能的情况下，应该使用更强的线程封闭技术(栈封闭或ThreadLocal类)
<br/>
栈封闭<br/>
栈封闭是线程封闭的一种特例，在栈封闭中,只能通过局部变量才能访问对象，正如封闭能使得代码更容易维持不变性条件那样，同步变量也能
使对象更易于封闭在线程中。局部变量的固有属性之一就是封闭在执行线程中。它们位于执行线程的栈中，其他线程无法访问这个栈。栈封闭
(也被称为线程内部使用或者线程局部使用，不要与核心类库中ThreadLocal混淆)比Ad-hoc线程封闭更易于维护，也更加健壮。
<br/>
如果在线程内部(Within-Thread)上下文中使用非线程安全的对象，那么该对象仍然是线程安全的。然而，要小心的是，只有编写代码的开发
人员才知道那些对象需要被封闭到执行线程中，以及被封闭的对象是否是线程安全的。如果没有明确地说明这些需求，那么后续的维护人员很容易
错误地使对象溢出。
<br/>
3.4
<br/>
不变性<br/>
当满足以下条件时，对象才是不可变的:
<br/>
对象创建以后其状态就不能修改。
<br/>
对象的以后域都是final类型。
<br/>
对象是正确创建的(在对象的创建期间，this引用没有溢出)










