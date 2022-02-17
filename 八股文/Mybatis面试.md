### 什么是Mybatis

1. Mybatis是一个半ORM（对象关系映射）框架，内部封装了JDBC，开发时只需要关注SQL本身，可以编写原生sql，控制sql的执行性能，灵活度高。
2. Mybatis可以使用XML或注解来配置和映射原生信息，将POJO映射成数据库中的记录，避免了所有JDBC代码和手动设置参数以及获取结果集。
3. 通过xml文件或注解的方式将要执行的各种statement配置起来，并通过java对象和statement中sql的动态参数进行映射生成最终执行的sql语句，最后由Mybatis框架执行sql并将结果映射为java对象并返回。

### Mybatis的优点

1. 基于SQL语句编程，灵活，SQL写在xml中，接触sql与程序代码的耦合，便于统一管理；
2. 与JDBC相比，减少了50%以上的代码量，消除了JDBC大量冗余的代码，不需要手动关闭连接；
3. 支持与各种支持JDBC的数据库兼容
4. 提供映射标签，支持对象与数据库的ORM字段关系映射；提供对象映射标签，支持对象关系组件维护。

### Mybatis框架的缺点

1. SQL语句的编写工作量较大，尤其是字段多、关联表多以及判断比较多的时候，XML的可读性不高。
2. SQL语句依赖于数据库，项目的移植性比较差。

### Mybatis的适用场景

1. 专注于SQL本身，是一个足够灵活的DAO层解决方案。
2. 对性能要求较高，需求变化较多

### Mybatis与Hibernate的区别

1. Mybatis和Hibernate不同，不是ORM框架
2. Hibernate对象/关系映射能力强，数据无关性好，对于关系模型要求高的软件，使用hibernate可以节省很多代码，提高效率。

### #{}和${}的区别是什么？

1. {} 是预编译处理，${}是字符串替换；
2. Mybatis在处理#{}时，会将#{}替换成？号，调用PreparedStatement的set方法来赋值；
3. Mybatis在处理${}时，会将${}替换成变量的值
4. 使用#{}可以有效的防止SQL注入，提高系统安全性

### 实体类中的属性和表中字段不一致如何处理?

1. 通过查询的sql语句中定义字段别名,让字段名的别名和是体类属性一直
2. 通过映射字段和实体类属性名来一一对应

### 模糊查询like语句应该怎么写?

1. 在java代码中添加sql通配符
2. 在sql语句中拼接通配符，会引起sql注入

### Mybatis是如何分页的？分页插件的原理是什么

Mybatis使用RowBounds对象进行分页，它是针对ResultSet结果集执行的内存分页，而非物理分页。可以在sql内直接书写带有物理分页的参数来完成物理分页功能，也可以使用分页插件来完成物理分页。

分页插件的基本原理是使用Mybatis提供的插件接口，实现自定义插件，在插件的拦截方法内拦截待执行的sql，然后重写sql，根据dialect方言，添加对应的物理分页语句和物理分页参数。

### 如何获取自动生成的键值？

insert方法总是会返回一个int值，这个值代表的是插入的行数。

```xml
<insert id="xxxx" usergeneratekeys="true" keyproperty="id">
	insert into names (name) values (#{name})
</insert>
```

### Mybatis实现一对多有几种方式，如何操作？

有联合查询和嵌套查询。

> 联合查询是几个表联合查询，只查询一次，通过resultMap里面的collection节点配置一对多的类；

> 嵌套查询是先查一个表，然后再根据这个表里面的接过的外键id，再到另外一个表中去查询数据，同样是通过配置collection，单另外一个表的查询通过select节点配置。

### Mybatis是否支持延迟加载？如果支持，实现原理是什么？

Mybatis仅支持association关联对象和collection关联集合对象的延迟加载，association指的就是一对一，collection指的就是一对多查询。

> 在Mybatis配置文件中，可以配置是否启用延迟加载lazyLoadingEnabled=true|false。

**实现原理**：使用<kbd>CGLIB</kbd>创建目标对象的代理对象，当调用目标方法时，进入拦截器方法，比如调用<kbd>a.getB().getName()</kbd>，拦截器<kbd>invoke()</kbd>方法发现a.getB()是null值，那么就会单独发送事先保存好的查询关联B对象的sql，把B查询上来，然后调用<kbd>a.setB(b)</kbd>，于是a的对象b属性就有值了，接着完成<kbd>a.getB().getName()</kbd>方法的调用。这就是延迟加载的基本原理。

### Mybatis的一级、二级缓存

一级缓存：基于PerpetualCache的HashMap本地缓存，其存储作用域为session，当Session flush或close之后，该Session的所有Cache就将清空，默认打开一级缓存。

二级缓存：一级缓存的机制相同，不同在于作用域为Mapper（namespace），并且可自定义存储源，比如Ehcache。默认不打开二级缓存，使用二级缓存实现类需要实现序列化接口，可在映射文件中进行配置。

缓存数据的更新机制：当一个作用域被C/U/D操作之后，默认该作用域下所有的select中的缓存都会被clear。

### 什么是Mybatis的接口绑定？有哪些实现方式？

接口绑定：就是再Mybatis中任意定义接口，然后将接口的方法与XML中的SQL语句进行绑定，只需要直接调用接口方法。

两种方式

> 通过注解绑定，就是在接口的方法上面加上 @Select、@Update等注解，里面包含Sql语句来绑定
>
> 通过xml里面写SQL来绑定, 在这种情况下,要指定xml映射文件里面的namespace必须为接口的全路径名

### 使用Mybatis的mapper接口调用时有哪些要求？

1. Mapper接口方法名和mapper.xml中定义的每个sql的id相同；

2. Mapper接口方法的输入参数类型和mapper.xml中定义的每个sql 的parameterType的类型相同；

3. Mapper接口方法的输出参数类型和mapper.xml中定义的每个sql的resultType的类型相同；

4. Mapper.xml文件中的namespace即是mapper接口的类路径。

### 简述Mybatis的插件运行原理，以及如何编写一个插件

Mybatis仅可以编写针对ParameterHandler、ResultSetHandler、StatementHandler、Executor这4种接口的插件，Mybatis使用JDK的动态代理，为需要拦截的接口生成代理对象以实现接口方法拦截功能，每当执行这4种接口对象的方法时，就会进入拦截方法，具体就是InvocationHandler的invoke()方法，当然，只会拦截那些你指定需要拦截的方法。

编写插件：实现Mybatis的Interceptor接口并复写intercept()方法，然后在给插件编写注解，指定要拦截哪一个接口的哪些方法即可，记住，别忘了在配置文件中配置你编写的插件。

### Mybatis如何执行批处理？

使用 BatchExecutor 完成批处理。