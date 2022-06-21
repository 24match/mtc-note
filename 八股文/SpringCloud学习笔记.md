# Spring Cloud 学习笔记

## 什么是SpringCloud?

简单来说，Spring Cloud提供了一些可以让开发者快速构建微服务应用的工具，比如配置管理、服务发现、熔断、智能路由等，这些服务可以在任何我分布式环境下很好的工作。

主要致力于解决如下问题：

+ Distributed/versioned configuration，分布式及版本配置
+ Service registration and discovery，服务注册与发现
+ Routing，服务路由
+ Service-to-service call，服务调用
+ Load balancing，负载均衡
+ Circuit Breakers，断路器
+ Global locks，全局锁
+ Leadership election and cluster state，Leader选举和集群状态
+ Distributed message，分布式消息

Spring Cloud并不是Spring团队全新研发的框架，它只是把一些比较优秀的解决微服务架构中常见问题的开源框架基于Spring Cloud规范进行了整合，通过Spring Boot这个框架进行再次封装后屏蔽掉了复杂的配置，给开发者提供良好的开箱即用的微服务开发体验。不难看出，Spring Cloud其实就是一套规范，而Spring Cloud Netflix、Spring Cloud Consul、Spring Cloud Alibaba才是Spring Cloud规范的实现。

## 版本简介

+ Angel（Release版本）
+ Brixton
+ Camden
+ Dalston
+ Edgware
+ Finchley
+ Greenwich
+ Hoxton

## Spring Cloud Alibaba基础组件

+ Sentinel，流量控制和服务降级
+ Nacos，服务注册于发现
+ Nacos，分布式配置中心
+ RocketMQ，消息驱动
+ Seate，分布式事务
+ Dubbo，RPC通信
+ OOS，阿里云对象存储

## Spring的核心复习

**IoC**

IoC(控制反转)把对象的生命周期委托到Spring容器中，而反转指的是对象的获取方式被反转了。传统意义上的对象创建方式，需要new来创建，这种方式会使代码的耦合度非常高。

当使用IoC容器后，客户端不需要new来创建对象，而是直接从IoC中获取对象。早期主要通过XML的方式来定义Bean，Spring解析XML文件，把定义的Bean装在到IoC容器中。

**DI**

DI（Dependency Inject），依赖注入，在IoC容器运行期间，动态地把某种依赖关系注入组件中。

```java
ApplicationContext context = new FileSystemXmlApplicationContext("...")
User user = context.getBean(User.class);
UserDetail userDetail = user.getUserDetail();
```

只需要在Spring的配置文件中描述Bean之间的依赖关系

```xml
<bean id="user" class="User">
	<property name="userDetail" ref="userDetail" />
</bean>
<bean id="userDetail" class="UserDetail" />
```

实现依赖的方式有三种，接口注入，构造方法注入和setter方法注入

如今基本都是基于注解来描述Bean之间的依赖关系，@Autowire，@Resource，@Inject

## Bean装配方式的升级

Spring升级到3.x后，提供JavaConfig的能力，可以完全取代XML，通过Java代码完成Bean的注入。

Java Config只需要使用@Configuration等同于XML的配置形式

通过@Bean注解将一个对象注入IoC容器中

```java
@Configuration
public class SpringConfigClass {
    @Bean
    public BeanDefine beanDefine() {
        return new BeanDefine();
    }
}
```

## Spring Boot的价值

### 如何理解约定大于配置

+ Maven目录结构的约定
+ Spring Boot默认的配置文件及配置文件中配置属性的约定
+ 对于Spring MVC的依赖，自动依赖内置的Tomcat容器
+ 对于Starter组件完成自动装配

### Spring Boot的核心

+ Starter组件，提供开箱即用的组件
+ 自动装配，自动根据上下文完成Bean的装配
+ Actuator，Spring Boot应用的监控
+ Spring Boot CLI，基于命令行工具快速构建Spring Boot应用

### Spring Boot自动装配的原理

不需要通过XML的形式或者注解把组件注入到IoC容器，但是在Controller却可以直接使用@Autowired来注入这个组件。这就是Spring Boot的自动装配机制。

#### 自动装配的实现

自动装配在Spring Boot中通过@EnableAutoConfiguration注解来开启，这个注解的启动在启动类注解@SpringBootApplication内

```java
@SpringBootApplication
public class SpringBootApplication() {
    pulbic static void main(String[]agrs) {
        SpringApplication.run(SpringBootApplication.class);
    }
}
```





