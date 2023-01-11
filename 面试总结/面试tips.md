### 面试

#### 自我介绍

---

``` text

```

## Java

##### == 与 equals

**==** : 它的作用是判断两个对象的地址是不是相等。即，判断两个对象是不是同一个对象(基本数据类型==比较的是值，引用数据类型==比较的是内存地址)。

**equals()** : 它的作用也是判断两个对象是否相等。但它一般有两种使用情况：

- 情况 1：类没有覆盖 equals() 方法。则通过 equals() 比较该类的两个对象时，等价于通过“==”比较这两个对象。
- 情况 2：类覆盖了 equals() 方法。一般，我们都覆盖 equals() 方法来比较两个对象的内容是否相等；若它们的内容相等，则返回 true (即，认为这两个对象相等)。


- String 中的 equals 方法是被重写过的，因为 object 的 equals 方法是比较的对象的内存地址，而 String 的 equals 方法比较的是对象的值。
- 当创建 String 类型的对象时，虚拟机会在常量池中查找有没有已经存在的值和要创建的值相同的对象，如果有就把它赋给当前引用。如果没有就在常量池中重新创建一个 String 对象。

##### String StringBuffer 和 StringBuilder 的区别是什么? String 为什么是不可变的?

**可变性**

简单的来说：String 类中使用 final 关键字修饰字符数组来保存字符串，`private final char value[]`，所以 String 对象是不可变的。

> 在 Java 9 之后，String 类的实现改用 byte 数组存储字符串 `private final byte[] value`;

而 StringBuilder 与 StringBuffer 都继承自 AbstractStringBuilder 类，在 AbstractStringBuilder 中也是使用字符数组保存字符串`char[]value` 但是没有用
final 关键字修饰，所以这两种对象都是可变的

**线程安全性**

String 中的对象是不可变的，也就可以理解为常量，线程安全。AbstractStringBuilder 是 StringBuilder 与 StringBuffer 的公共父类，定义了一些字符串的基本操作，如
expandCapacity、append、insert、indexOf 等公共方法。StringBuffer 对方法加了同步锁或者对调用的方法加了同步锁，所以是线程安全的。StringBuilder
并没有对方法进行加同步锁，所以是非线程安全的。

**性能**

每次对 String 类型进行改变的时候，都会生成一个新的 String 对象，然后将指针指向新的 String 对象。StringBuffer 每次都会对 StringBuffer
对象本身进行操作，而不是生成新的对象并改变对象引用。相同情况下使用 StringBuilder 相比使用 StringBuffer 仅能获得 10%~15% 左右的性能提升，但却要冒多线程不安全的风险。

**对于三者使用的总结：**

1. 操作少量的数据: 适用 String
2. 单线程操作字符串缓冲区下操作大量数据: 适用 StringBuilder
3. 多线程操作字符串缓冲区下操作大量数据: 适用 StringBuffer

##### 接口和抽象类的区别是什么？

1. 接口的方法默认是 public，所有方法在接口中不能有实现(Java 8 开始接口方法可以有默认实现），而抽象类可以有非抽象的方法。
2. 接口中除了 static、final 变量，不能有其他变量，而抽象类中则不一定。
3. 一个类可以实现多个接口，但只能实现一个抽象类。接口自己本身可以通过 extends 关键字扩展多个接口。
4. 接口方法默认修饰符是 public，抽象方法可以有 public、protected 和 default 这些修饰符（抽象方法就是为了被重写所以不能使用 private 关键字修饰！）。
5. 从设计层面来说，抽象是对类的抽象，是一种模板设计，而接口是对行为的抽象，是一种行为的规范。

> 备注：
>
> 1. 在 JDK8 中，接口也可以定义静态方法，可以直接用接口名调用。实现类和实现是不可以调用的。如果同时实现两个接口，接口中定义了一样的默认方法，则必须重写，不然会报错。(详见 issue:https://github.com/Snailclimb/JavaGuide/issues/146。
> 2. jdk9 的接口被允许定义私有方法 。

##### 重载和重写的区别

重载就是同样的一个方法能够根据输入数据的不同，做出不同的处理

重写就是当子类继承自父类的相同方法，输入数据一样，但要做出有别于父类的响应时，你就要覆盖父类方法

**重载就是同一个类中多个同名方法根据不同的传参来执行不同的逻辑处理。**

重写发生在运行期，是子类对父类的允许访问的方法的实现过程进行重新编写。

1. 返回值类型、方法名、参数列表必须相同，抛出的异常范围小于等于父类，访问修饰符范围大于等于父类。
2. 如果父类方法访问修饰符为 private/final/static 则子类就不能重写该方法，但是被 static 修饰的方法能够被再次声明。
3. 构造方法无法被重写

##### 说说List,Set,Map三者的区别？

+ **List(对付顺序的好帮手)：** List接口存储一组不唯一（可以有多个元素引用相同的对象），有序的对象
+ **Set(注重独一无二的性质):** 不允许重复的集合。不会有多个元素引用相同的对象。
+ **Map(用Key来搜索的专家):** 使用键值对存储。Map会维护与Key有关联的值。两个Key可以引用相同的对象，但Key不能重复，典型的Key是String类型，但也可以是任何对象。

##### Arraylist 与 LinkedList 区别?

- **1. 是否保证线程安全：** `ArrayList` 和 `LinkedList` 都是不同步的，也就是不保证线程安全；
- **2. 底层数据结构：** `Arraylist` 底层使用的是 **`Object` 数组**；`LinkedList` 底层使用的是 **双向链表**
  数据结构（JDK1.6之前为循环链表，JDK1.7取消了循环。注意双向链表和双向循环链表的区别，下面有介绍到！）
- **3. 插入和删除是否受元素位置的影响：** ① **`ArrayList` 采用数组存储，所以插入和删除元素的时间复杂度受元素位置的影响。** 比如：执行`add(E e) `方法的时候， `ArrayList`
  会默认在将指定的元素追加到此列表的末尾，这种情况时间复杂度就是O(1)。但是如果要在指定位置 i 插入和删除元素的话（`add(int index, E element) `）时间复杂度就为 O(n-i)
  。因为在进行上述操作的时候集合中第 i 和第 i 个元素之后的(n-i)个元素都要执行向后位/向前移一位的操作。 ② **`LinkedList` 采用链表存储，所以对于`add(E e)`
  方法的插入，删除元素时间复杂度不受元素位置的影响，近似 O(1)，如果是要在指定位置`i`插入和删除元素的话（`(add(int index, E element)`） 时间复杂度近似为`o(n))`因为需要先移动到指定位置再插入。**
- **4. 是否支持快速随机访问：** `LinkedList` 不支持高效的随机元素访问，而 `ArrayList` 支持。快速随机访问就是通过元素的序号快速获取元素对象(对应于`get(int index) `方法)。
- **5. 内存空间占用：** ArrayList的空
  间浪费主要体现在在list列表的结尾会预留一定的容量空间，而LinkedList的空间花费则体现在它的每一个元素都需要消耗比ArrayList更多的空间（因为要存放直接后继和直接前驱以及数据）。

##### ArrayList 与 Vector 区别呢?为什么要用Arraylist取代Vector呢？

`Vector`类的所有方法都是同步的。可以由两个线程安全地访问一个Vector对象、但是一个线程访问Vector的话代码要在同步操作上耗费大量的时间。

`Arraylist`不是同步的，所以在不需要保证线程安全时建议使用Arraylist。

##### HashMap 和 Hashtable 的区别

1. **线程是否安全：** HashMap 是非线程安全的，HashTable 是线程安全的；HashTable 内部的方法基本都经过`synchronized` 修饰。（如果你要保证线程安全的话就使用 ConcurrentHashMap
   吧！）；
2. **效率：** 因为线程安全的问题，HashMap 要比 HashTable 效率高一点。另外，HashTable 基本被淘汰，不要在代码中使用它；
3. **对Null key 和Null value的支持：** HashMap 中，null 可以作为键，这样的键只有一个，可以有一个或多个键所对应的值为 null。。但是在 HashTable 中 put 进的键值只要有一个
   null，直接抛出 NullPointerException。
4. **初始容量大小和每次扩充容量大小的不同 ：** ①创建时如果不指定容量初始值，Hashtable 默认的初始大小为11，之后每次扩充，容量变为原来的2n+1。HashMap
   默认的初始化大小为16。之后每次扩充，容量变为原来的2倍。②创建时如果给定了容量初始值，那么 Hashtable 会直接使用你给定的大小，而 HashMap 会将其扩充为2的幂次方大小（HashMap
   中的`tableSizeFor()`方法保证，下面给出了源代码）。也就是说 HashMap 总是使用2的幂作为哈希表的大小,后面会介绍到为什么是2的幂次方。
5. **底层数据结构：** JDK1.8 以后的 HashMap 在解决哈希冲突时有了较大的变化，当链表长度大于阈值（默认为8）（将链表转换成红黑树前会判断，如果当前数组的长度小于
   64，那么会选择先进行数组扩容，而不是转换为红黑树）时，将链表转化为红黑树，以减少搜索时间。Hashtable 没有这样的机制。

##### HashMap的底层实现

+ JDK1.8 之前 `HashMap` 底层是 **数组和链表** 结合在一起使用也就是 **链表散列**。**HashMap 通过 key 的 hashCode 经过扰动函数处理过后得到 hash 值，然后通过 (n - 1) &
  hash 判断当前元素存放的位置（这里的 n 指的是数组的长度），如果当前位置存在元素的话，就判断该元素与要存入的元素的 hash 值以及 key 是否相同，如果相同的话，直接覆盖，不相同就通过拉链法解决冲突。**
+ 相比于之前的版本， JDK1.8之后在解决哈希冲突时有了较大的变化，当链表长度大于阈值（默认为8）（将链表转换成红黑树前会判断，如果当前数组的长度小于
  64，那么会选择先进行数组扩容，而不是转换为红黑树）时，将链表转化为红黑树，以减少搜索时间。

##### ConcurrentHashMap 和 Hashtable 的区别

ConcurrentHashMap 和 Hashtable 的区别主要体现在实现线程安全的方式上不同。

- **底层数据结构：** JDK1.7的 ConcurrentHashMap 底层采用 **分段的数组+链表** 实现，JDK1.8 采用的数据结构跟HashMap1.8的结构一样，数组+链表/红黑二叉树。Hashtable 和
  JDK1.8 之前的 HashMap 的底层数据结构类似都是采用 **数组+链表** 的形式，数组是 HashMap 的主体，链表则是主要为了解决哈希冲突而存在的；
- **实现线程安全的方式（重要）：** ① **在JDK1.7的时候，ConcurrentHashMap（分段锁）** 对整个桶数组进行了分割分段(Segment)
  ，每一把锁只锁容器其中一部分数据，多线程访问容器里不同数据段的数据，就不会存在锁竞争，提高并发访问率。 **到了 JDK1.8 的时候已经摒弃了Segment的概念，而是直接用 Node 数组+链表+红黑树的数据结构来实现，并发控制使用
  synchronized 和 CAS 来操作。（JDK1.6以后 对 synchronized锁做了很多优化）** 整个看起来就像是优化过且线程安全的 HashMap，虽然在JDK1.8中还能看到 Segment
  的数据结构，但是已经简化了属性，只是为了兼容旧版本；② **Hashtable(同一把锁)** :使用 synchronized
  来保证线程安全，效率非常低下。当一个线程访问同步方法时，其他线程也访问同步方法，可能会进入阻塞或轮询状态，如使用 put 添加元素，另一个线程不能使用 put 添加元素，也不能使用 get，竞争会越来越激烈效率越低。

## Spring

##### Spring IOC & AOP

+ #### IoC

    - IoC（Inverse of Control:控制反转）是一种**设计思想**，就是 **将原本在程序中手动创建对象的控制权，交由Spring框架来管理。**  **IoC 容器是 Spring 用来实现 IoC 的载体，
      IoC 容器实际上就是个Map（key，value）,Map 中存放的是各种对象。**
    - 将对象之间的相互依赖关系交给 IoC 容器来管理，并由 IoC 容器完成对象的注入。这样可以很大程度上简化应用的开发，把应用从复杂的依赖关系中解放出来。 **IoC
      容器在当我们需要创建一个对象的时候，只需要配置好配置文件/注解即可。**

+ #### AOP

    - AOP(Aspect-Oriented Programming:面向切面编程)能够将那些与业务无关，**却为业务模块所共同调用的逻辑或责任（例如事务处理、日志管理、权限控制等）封装起来**，便于**减少系统的重复代码**，**
      降低模块间的耦合度**，并**有利于未来的可拓展性和可维护性**。

    - **Spring AOP就是基于动态代理的**，如果要代理的对象，实现了某个接口，那么Spring AOP会使用**JDK Proxy**，去创建代理对象，而对于没有实现接口的对象，就无法使用 JDK Proxy
      去进行代理了，这时候Spring AOP会使用**Cglib** ，这时候Spring AOP会使用 **Cglib** 生成一个被代理对象的子类来作为代理。使用 AOP
      之后我们可以把一些通用功能抽象出来，在需要用到的地方直接使用即可，这样大大简化了代码量。我们需要增加新功能时也方便，这样也提高了系统扩展性。日志功能、事务管理等等场景都用到了 AOP 。

##### Spring AOP 和 AspectJ AOP 有什么区别？

+ **Spring AOP 属于运行时增强，而 AspectJ 是编译时增强。** Spring AOP 基于代理(Proxying)，而 AspectJ 基于字节码操作(Bytecode Manipulation)。Spring AOP
  已经集成了 AspectJ ，AspectJ 应该算的上是 Java 生态系统中最完整的 AOP 框架了。AspectJ 相比于 Spring AOP 功能更加强大，但是 Spring AOP
  相对来说更简单，如果我们的切面比较少，那么两者性能差异不大。但是，当切面太多的话，最好选择 AspectJ ，它比Spring AOP 快很多。

##### Spring 中的 bean 的作用域有哪些?

+ singleton : 唯一 bean 实例，Spring 中的 bean 默认都是单例的。
+ prototype : 每次请求都会创建一个新的 bean 实例。
+ request : 每一次HTTP请求都会产生一个新的bean，该bean仅在当前HTTP request内有效。
+ session : 每一次HTTP请求都会产生一个新的 bean，该bean仅在当前 HTTP session 内有效。
+ global-session： 全局session作用域，仅仅在基于portlet的web应用中才有意义，Spring5已经没有了。Portlet是能够生成语义代码(例如：HTML)片段的小型Java
  Web插件。它们基于portlet容器，可以像servlet一样处理HTTP请求。但是，与 servlet 不同，每个 portlet 都有不同的会话

##### Spring 中的单例 bean 的线程安全问题了解吗？

大部分时候我们并没有在系统中使用多线程，所以很少有人会关注这个问题。单例 bean 存在线程问题，主要是因为当多个线程操作同一个对象的时候，对这个对象的非静态成员变量的写操作会存在线程安全问题。

常见的有两种解决办法：

1. 在Bean对象中尽量避免定义可变的成员变量（不太现实）。
2. 在类中定义一个ThreadLocal成员变量，将需要的可变成员变量保存在 ThreadLocal 中（推荐的一种方式）。

##### @Component 和 @Bean 的区别是什么？

1. 作用对象不同: `@Component` 注解作用于类，而`@Bean`注解作用于方法。
2. `@Component`通常是通过类路径扫描来自动侦测以及自动装配到Spring容器中（我们可以使用 `@ComponentScan` 注解定义要扫描的路径从中找出标识了需要装配的类自动装配到 Spring 的 bean
   容器中）。`@Bean` 注解通常是我们在标有该注解的方法中定义产生这个 bean,`@Bean`告诉了Spring这是某个类的示例，当我需要用它的时候还给我。
3. `@Bean` 注解比 `@Component` 注解的自定义性更强，而且很多地方我们只能通过 `@Bean` 注解来注册bean。比如当我们引用第三方库中的类需要装配到 `Spring`容器时，则只能通过 `@Bean`来实现。

##### Spring 中的 bean 生命周期?

- Bean 容器找到配置文件中 Spring Bean 的定义。
- Bean 容器利用 Java Reflection API 创建一个Bean的实例。
- 如果涉及到一些属性值 利用 `set()`方法设置一些属性值。
- 如果 Bean 实现了 `BeanNameAware` 接口，调用 `setBeanName()`方法，传入Bean的名字。
- 如果 Bean 实现了 `BeanClassLoaderAware` 接口，调用 `setBeanClassLoader()`方法，传入 `ClassLoader`对象的实例。
- 与上面的类似，如果实现了其他 `*.Aware`接口，就调用相应的方法。
- 如果有和加载这个 Bean 的 Spring 容器相关的 `BeanPostProcessor` 对象，执行`postProcessBeforeInitialization()` 方法
- 如果Bean实现了`InitializingBean`接口，执行`afterPropertiesSet()`方法。
- 如果 Bean 在配置文件中的定义包含 init-method 属性，执行指定的方法。
- 如果有和加载这个 Bean的 Spring 容器相关的 `BeanPostProcessor` 对象，执行`postProcessAfterInitialization()` 方法
- 当要销毁 Bean 的时候，如果 Bean 实现了 `DisposableBean` 接口，执行 `destroy()` 方法。
- 当要销毁 Bean 的时候，如果 Bean 在配置文件中的定义包含 destroy-method 属性，执行指定的方法。

##### 说说自己对于 Spring MVC 了解?

MVC 是一种设计模式,Spring MVC 是一款很优秀的 MVC 框架。Spring MVC 可以帮助我们进行更简洁的Web层的开发，并且它天生与 Spring 框架集成。Spring MVC 下我们一般把后端项目分为
Service层（处理业务）、Dao层（数据库操作）、Entity层（实体类）、Controller层(控制层，返回数据给前台页面)。

+ 用户发起请求
+ 控制器接收请求 -》 调用业务逻辑 -》 然后派发给页面
+ 然后交给model层处理返回一个处理结果
+ 渲染视图后返回给用户

详细流程：

1. 客户端（浏览器）发送请求，直接请求到 `DispatcherServlet`。
2. `DispatcherServlet` 根据请求信息调用 `HandlerMapping`，解析请求对应的 `Handler`。
3. 解析到对应的 `Handler`（也就是我们平常说的 `Controller` 控制器）后，开始由 `HandlerAdapter` 适配器处理。
4. `HandlerAdapter` 会根据 `Handler `来调用真正的处理器开处理请求，并处理相应的业务逻辑。
5. 处理器处理完业务后，会返回一个 `ModelAndView` 对象，`Model` 是返回的数据对象，`View` 是个逻辑上的 `View`。
6. `ViewResolver` 会根据逻辑 `View` 查找实际的 `View`。
7. `DispaterServlet` 把返回的 `Model` 传给 `View`（视图渲染）。
8. 把 `View` 返回给请求者（浏览器）

##### Spring 框架中用到了哪些设计模式？

- **工厂设计模式** : Spring使用工厂模式通过 `BeanFactory`、`ApplicationContext` 创建 bean 对象。
- **代理设计模式** : Spring AOP 功能的实现。
- **单例设计模式** : Spring 中的 Bean 默认都是单例的。
- **模板方法模式** : Spring 中 `jdbcTemplate`、`hibernateTemplate` 等以 Template 结尾的对数据库操作的类，它们就使用到了模板模式。
- **包装器设计模式** : 我们的项目需要连接多个数据库，而且不同的客户在每次访问中根据需要会去访问不同的数据库。这种模式让我们可以根据客户的需求能够动态切换不同的数据源。
- **观察者模式:** Spring 事件驱动模型就是观察者模式很经典的一个应用。
- **适配器模式** :Spring AOP 的增强或通知(Advice)使用到了适配器模式、spring MVC 中也是用到了适配器模式适配`Controller`。

##### Spring 管理事务的方式有几种？

1. 编程式事务，在代码中硬编码。(不推荐使用)
2. 声明式事务，在配置文件中配置（推荐使用）

1. 基于XML的声明式事务
2. 基于注解的声明式事务

##### Spring 事务中的隔离级别有哪几种?

**TransactionDefinition 接口中定义了五个表示隔离级别的常量：**

- **TransactionDefinition.ISOLATION_DEFAULT:** 使用后端数据库默认的隔离级别，Mysql 默认采用的 REPEATABLE_READ隔离级别 Oracle 默认采用的
  READ_COMMITTED隔离级别.
- **TransactionDefinition.ISOLATION_READ_UNCOMMITTED:** 最低的隔离级别，允许读取尚未提交的数据变更，**可能会导致脏读、幻读或不可重复读**
- **TransactionDefinition.ISOLATION_READ_COMMITTED:** 允许读取并发事务已经提交的数据，**可以阻止脏读，但是幻读或不可重复读仍有可能发生**
- **TransactionDefinition.ISOLATION_REPEATABLE_READ:** 对同一字段的多次读取结果都是一致的，除非数据是被本身事务自己所修改，**可以阻止脏读和不可重复读，但幻读仍有可能发生。**
- **TransactionDefinition.ISOLATION_SERIALIZABLE:** 最高的隔离级别，完全服从ACID的隔离级别。所有的事务依次逐个执行，这样事务之间就完全不可能产生干扰，也就是说，**
  该级别可以防止脏读、不可重复读以及幻读**。但是这将严重影响程序的性能。通常情况下也不会用到该级别。

##### Spring 事务中哪几种事务传播行为?

**支持当前事务的情况：**

- **TransactionDefinition.PROPAGATION_REQUIRED：** 如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。
- **TransactionDefinition.PROPAGATION_SUPPORTS：** 如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行。
- **TransactionDefinition.PROPAGATION_MANDATORY：** 如果当前存在事务，则加入该事务；如果当前没有事务，则抛出异常。（mandatory：强制性）

**不支持当前事务的情况：**

- **TransactionDefinition.PROPAGATION_REQUIRES_NEW：** 创建一个新的事务，如果当前存在事务，则把当前事务挂起。
- **TransactionDefinition.PROPAGATION_NOT_SUPPORTED：** 以非事务方式运行，如果当前存在事务，则把当前事务挂起。
- **TransactionDefinition.PROPAGATION_NEVER：** 以非事务方式运行，如果当前存在事务，则抛出异常。

**其他情况：**

- **TransactionDefinition.PROPAGATION_NESTED：**
  如果当前存在事务，则创建一个事务作为当前事务的嵌套事务来运行；如果当前没有事务，则该取值等价于TransactionDefinition.PROPAGATION_REQUIRED。

#### Spring security

+ 除了不能脱离Spring，shiro的功能它都有。而且Spring Security对Oauth、OpenID也有支持,Shiro则需要自己手动实现。Spring Security的权限细粒度更高,毕竟Spring
  Security是Spring家族的。

+ **Spring Security一般流程为：**

  ①当用户登录时，前端将用户输入的用户名、密码信息传输到后台，后台用一个类对象将其封装起来，通常使用的是UsernamePasswordAuthenticationToken这个类。

  ②程序负责验证这个类对象。验证方法是调用Service根据username从数据库中取用户信息到实体类的实例中，比较两者的密码，如果密码正确就成功登陆，同时把包含着用户的用户名、密码、所具有的权限等信息的类对象放到SecurityContextHolder（安全上下文容器，类似Session）中去。

  ③用户访问一个资源的时候，首先判断是否是受限资源。如果是的话还要判断当前是否未登录，没有的话就跳到登录页面。

  ④如果用户已经登录，访问一个受限资源的时候，程序要根据url去数据库中取出该资源所对应的所有可以访问的角色，然后拿着当前用户的所有角色一一对比，判断用户是否可以访问。

#### Shiro

1、易于理解的 Java Security API；

2、简单的身份认证（登录），支持多种数据源（LDAP，JDBC，Kerberos，ActiveDirectory 等）；

3、对角色的简单的签权（访问控制），支持细粒度的签权；

4、支持一级缓存，以提升应用程序的性能；

5、内置的基于 POJO 企业会话管理，适用于 Web 以及非 Web 的环境；

6、异构客户端会话访问；

7、非常简单的加密 API；

8、不跟任何的框架或者容器捆绑，可以独立运行。

##### 优点：

> 1：Spring Security基于Spring开发，项目中如果使用Spring作为基础，配合Spring Security做权限更加方便，而Shiro需要和Spring进行整合开发  
2：Spring Security功能比Shiro更加丰富些，例如安全防护  
3：Spring Security社区资源比Shiro丰富

##### 缺点：

> 1：Shiro的配置和使用比较简单，Spring Security上手复杂  
2：Shiro依赖性低，不需要任何框架和容器，可以独立运行，而Spring Security依赖于Spring容器

## 微信支付

1. 商户系统根据用户选择的商品生成订单。
2. 用户确认支付后根据微信【统一下单 API】，向微信支付系统发出请求（我们通过httpclient 方式请求的）
3. 微信支付系统收到请求后，先生成预支付订单，然后给商户系统返回二维码连接。
4. 商户系统拿到返回值字符串，转换成对象，然后取出二维码连接生成二维码。
5. 用户通过微信“扫一扫”功能扫描二维码，微信客户端将扫码内容发送到微信支付系统。
6. 微信支付系统接收到支付请求，验证链接有效性后发起支付，请求客户确认，然后我们的微信端就会弹出需要确认支付的页面。
7. 用户输入密码，确认支付并提交授权。
8. 微信支付系统根据用户授权完成交易。
9. 微信支付系统支付成功后向微信客户端返回交易结果，并将交易结果通过短信、微信提示用户。
10. 微信支付系统通过发送异步消息通知商户系统后台支付结果，商户系统需回复接收情况，通知微信支付系统不再发送该单的通知。
11. 未收到支付通知的情况，商户系统可调用【查询订单 APP】。
12. 商户确认订单已经支付后给用户发货。

## JAVA8 新特性

+ 接口的默认方法
    + 使用 default 关键字向接口添加非抽象方法实现，也称为虚拟扩展方法
    + 不管是抽象类，还是接口，都可以通过匿名内部类的方式访问。不能通过抽象类或者接口直接创建对象。
+ Lambda表达式
+ 函数式接口
    + 函数式接口是指只包含一个抽象方法，但是可以有多个非抽象方法的接口。（也就是默认方法）。这种函数式接口可以隐式转换未lambda表达式。
    + Java8中新增了@FuncationalInterface，如果接口只包含一个抽象方法，虚拟机会自动判断该接口为函数式接口。
    + 如果接口上使用该注解，编译器发现标注了这个注解的接口有多余一个抽象方法的时候会报错。
+ 方法和构造函数的引用
    + 构造函数使用 **::** 关键词来引用
+ Lambda表达式的作用域
+ 内置函数式接口
    + Predicates

+ Optional
    - Optional 是一个容器，用来防止 NullPointException，它的值可能是null或者是不等于null

```java
public class optionalTest {
    // 修改前
    public static String getChampionName(Competition comp) {
        if (comp != null) {
            CompResult result = comp.getResult();
            if (result != null) {
                User champion = result.getChampion();
                if (champion != null) {
                    return champion.getName();
                }
            }
        }
        throw new IllegalArgumentException("The value of param comp isn't available");
    }

    // 修改后
    public static String getChampionNameNew(Competition competition) {
        return Optional.ofNullable(competition)
                .map(Competition::getResult)
                .map(CompResult::getChampion)
                .map(User::getName)
                .orElseThrow(() -> new IllegalArgumentException("The value of param comp isn't available"));
    }
}
```

+ Stream ：能应用在一组元素上一次执行的操作序列。Stream分为中间操作或者是最终操作两种，最终操作返回的是特定类型的计算结果，而中间操作返回stream本身。Stream的创建需要指定数据源。
    + Filter(过滤)
        + 过滤通过一个predicate接口过滤并只保留符合条件的元素，属于中间操作，过滤后的结果来应用其他的操作。
        + foreach是为Lambda设计的
    + Sorted(排序)
        + 排序是一个中间操作，返回的是排序后的stream。如果不指定comparator会使用默认排序。
    + Map(映射)
        - 映射是一个中间操作，将元素根据指定的function接口来依次将元素转成另外的对象。
        - map返回的类型stream类型是根据map传递进去的函数的返回值决定。
    + Match(匹配)
        - 允许检测指定的predicate是否匹配整个stream，匹配都是最终操作，返回boolean
    + Count(计数)
        - 最宠操作，返回stream元素个数，返回long
+ Parallel Stream（并行流）
    + 并行stream是在多个线程上同时执行的
    + Sequential Sort(串行排序)
    + Parallel Sort(并行排序)
    + 唯一需要做的改动就是将 `stream()` 改为`parallelStream()`。

+ Maps
    + 数组 + 链表 更新为 数组 + 红黑树
+ Date API（日期API）
    + Clock
    + Timezones
    + LocalTime/LocalDate
+ Annotations

## 设计模式

#### 通常单例模式在Java语言中，有两种构建方式：

- **饿汉方式**。指全局的单例实例在类装载时构建
    - 所谓 **“饿汉方式”** 就是说JVM在加载这个类时就马上创建此唯一的单例实例，不管你用不用，先创建了再说，如果一直没有被使用，便浪费了空间，典型的空间换时间，每次调用的时候，就不需要再判断，节省了运行时间。
- **懒汉方式**。指全局的单例实例在第一次被使用时构建。
    - 所谓 **“ 懒汉式”** 就是说单例实例在第一次被使用时构建，而不是在JVM在加载这个类时就马上创建此唯一的单例实例。

#### 数据库索引

---

##### 什么是索引？

+ 索引是一种用于快速查询和检索数据的数据结构。常见的索引结构有：B Tree ，B+Tree 和hash

##### 索引优点

+ 可以大大加快 数据的检索速度（大大减少的检索的数据量）

##### 索引的缺点

+ 创建索引和维护索引需要耗费更多的时间 : 当进行增删改的时候，数据中有索引，索引也需要动态的修改，降低sql的执行效率
+ 占用物理存储空间：索引需要使用物理文件存储，会耗费空间

##### 索引创建注意点

+ 选择合适的字段
    + 不为null的字段，如果字段被频繁查询，尽量使用
    + 被频繁查询的字段
    + 被作为条件查询的字段，被用作where条件查询的字段
    + 频繁用于连接的字段
+ 不合适创建索引的字段
    + 被频繁更新的字段应该慎重建立索引
    + 不被经常查询的字段
    + 尽可能的建立联合索引而不是单列索引，每个索引都对应着一个B+树，字段过多索引过多，占用的空间也很多，如果用联合索引，多个字段在一个索引上，可以节约空间，修改数据的效率也可以提升
    + 避免冗余索引，冗余索引指的是索引的功能相同，能够命中就肯定可以命中，eg：name，city和name这两个索引就是冗余索引。

## Redis

### 为什么要用redis

+

高性能：加入用户第一次访问数据的某些数据时，因为是从硬盘中读取的数据所以速度会比较慢，如果将用户访问的数据放在缓存中下一次访问的时候就可以直接从缓存中获取。操作缓存的过程其实就是直接操作内存，所以速度很快。当DB中的数据发生改变的时候，同步修改缓存中的数据即可。

+ 高并发：直接操作缓存能够承受的请求时远远大于直接访问DB的，所以可以考虑将数据库的部分数据转移到缓存中，这样用户部分请求会直接到缓存读取而不用经过数据库。

### 为什么要用redis而不用map/guava做缓存？

+ 缓存分为本地缓存和分布式缓存。
    + map或者guava实现的是本地缓存，主要的特点是轻量以及快速，生命周期随着jvm的销毁而结束，并且在多个实例的情况下，每个实例都需要各自保存一份缓存，缓存不具有一致性。
    + 使用redis或memcached之类的称为分布式缓存，在多实例的情况下，各实例公用一份缓存数据，数据具有一致性。确实是需要保持redis或memcached服务的高可用，整个程序架构上较为复杂。

### redis的线程模型是什么？

redis内部使用文件处理器 `file event handler`，这个文件事件处理器是单线程的，所以redis才叫做单线程的模型。它采用IO多路复用机制同时监听多个socket，根据socket上的事件来选择对应的事件处理器进行处理。

+ 文件处理器的结构包含4个部分
    + 多个socket
    + IO多路复用程序
    + 文件事件派发器
    + 事件处理器（连接应答处理器、命令请求处理器、命令回复处理器）

多个socket可能会并发产生不同的操作，每个操作对应不同的文件事件，但是IO多路复用程序会监听多个socket，会将socket产生的事件放入队列中排队，事件分派器每次从队列中取出一个事件，把该事件交给对应的事件处理器进行处理。

### redis和memcached的区别

+
    1. redis支持更丰富的数据类型（支持更复杂的应用场景）：redis不仅仅支持简单的K/V，同时还提供了list，set，zset，hash等数据结构的存储。memcached支持简单的数据类型，string。
    2. redis支持数据的持久化，可以将内存中的数据保存在磁盘中，重启的时候可以再次加载进行使用，而memcached把数据全部存在内存之中。
    3. 集群模式：memcached没有原生的集群模式，需要依靠客户端来实现往集群中分片写入数据；但是redis是支持原生cluster模式的。
    4. memcached是多线程，非阻塞IO复用的网络模型；redis使用单线程的多路IO复用模型

| 对比参数     | redis                                | memcached                         |
|----------|--------------------------------------|-----------------------------------|
| 类型       | 1. 支持内存 2.非关系型数据库                    | 1. 支持内存 2. key-value键值对形式 3. 缓存系统 |
| 数据存储类型   | String, List ,Set , Hash, Sort Set   | 1. 文本型 2. 二进制类型                   |
| 查询【操作】类型 | 1. 批量操作 2. 事务处理 3. 每个类型不同的CRUD       | 1. CRUD 2. 少量的其他命令                |
| 附加功能     | 1. 发布订阅/ 模式 2. 主从分区 3. 序列化支持 4. 脚本支持 | 1. 多线程服务支持                        |
| 网络IO模型   | 单进程模式                                | 多线程 、非阻塞IO模式                      |
| 事件库      | 自封装简易事件库AeEvent                      | LibEvent事件库                       |
| 持久化支持    | 1. RDB 2.AOF                         | 不支持                               |

### redis常见数据结构以及使用场景分析

+ String(常用命令: set,get,decr,incr,mget 等）

    + string数据结构是简单的key-value类型，可以是string也可以是数字。常规key-value缓存应用：常规计数：微波束，粉丝数等

+ Hash

    - hash是一个string类型的 field 和value的映射表，hash适合用来存储对象。

+ List

    + list就是列表，可以用来实现高性能分页，类似于微博下拉不断分页的功能

+ Set

    + 存储列表但是不希望出现重复数据时

+ Sorted Set

    + 对集合中的元素按照score进行有序排列

### redis设置过期时间

+ 对存储在redis数据库中的值设置一个过期时间。在`set Key`的时候，可以给一个 `expire time`，过期时间，通过过期时间可以指定这个key可以存活的时间。
+ 定期删除 + 懒惰删除
    + 定期删除：redis默认每隔100ms随机抽取一些设置了过期时间的key。检查是否过期，如果过期则删除。
    + 惰性删除：系统主动去查key才会被redis删掉。

### redis 内存淘汰机制（比如DB里面又20w条数据，Redis中有20w条数据，如何保证redis中都是热点数据？）

redis提供6种数据淘汰策略

+ volatie- lru：从已设置过期时间的数据集中挑选最少使用的数据淘汰
+ volatile-ttl：从已设置过期时间的数据集中挑选将要过期的数据淘汰
+ volatile-random：从已设置过期时间的数据集中任意选择数据淘汰
+ allkeys-lru：当内存不足以容纳新写入数据时，在键空间中，移除最近最少使用的key
+ allkeys-random：从数据集中任意选择数据淘汰
+ no-eviction：禁止驱逐数据，也就是说当内存不足以容纳新写入数据时，新写入操作会报错。
+ volatile-lfu：从已设置过期时间的数据集中挑选最不常使用的数据淘汰
+ allkeys-lfu：当内存不足以容纳新写入数据时，在键空间中移除最不常用的key

### redis持久化机制（怎么保证redis挂掉之后再重启数据可以进行恢复？）

很多时候需要持久化数据也就是将内存中的数据写入硬盘，大部分原因是为了之后重用数据（比如重启机器、机器故障之后的数据回复）或者是为了防止系统故障而将数据被分到一个远程位置。

redis的两种持久化操作：

+ 快照持久化（RDB）：redis可以通过创建快照来获得存储内存里面的数据在某个时间点上的副本。redis创建快照后，可以对快照进行备份，可以将快照复制到其他服务器从而具有相同数据的服务器副本。还可以将快照留在原地以重启服务器时使用。

    + 默认的持久化方式

+ AOF 持久化：与快照相比，AOF持久化的实时性更好，默认redis没有开启AOF持久化，可以通过`appendonly`
  参数开启。开启AOF后每执行一条会更嗨redis中的数据的命令，redis就会将该命令写入硬盘中的AOF文件。AOF文件的保存位置和RDB文件的位置相同，都是通过dir参数设置的，默认的文件名时 `appendonly.aof`。

    1. appendfsync always # 每次有数据修改时都会写入AOF文件，这样会严重降低redis的速度
    2. appendfsync everysec # 每秒钟同步一次， 显示地将多个命令同步到硬盘
    3. appendfsync no # 让系统决定何时进行同步

​ 为了兼顾数据和写入性能，可以考虑`everysec`
选项，让redis每秒同步一次AOF文件，redis性能几乎没受到任何影响。而且这样即使出现系统崩溃，用户最多只会丢失一秒之内产生的数据。当硬盘忙于执行写入操作的时候，redis还会放慢自己的速度以适应硬盘的最大写入速度。













