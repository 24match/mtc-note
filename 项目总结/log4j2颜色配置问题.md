### 为什么使用log4j2后, 输出的日志没有颜色? 

> 在2.10版本以后，Log4j2默认关闭了Jansi（一个支持输出ANSI颜色的类库）

1. IDEA中，点击右上角->Edit Configurations，在VM options中添加(服务启动的地方)

> -Dlog4j.skipJansi=false

2. log4j2.yml的配置类使用

> pattern: "%d{yyyy-MM-dd HH:mm:ss.SSS} %highlight{%-5level} [%t] %highlight{%c{1.}.%M(%L)}: %msg%n"

