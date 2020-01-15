### 项目名称：  
springboot+多数据源+Redis+Redisson

### 项目描述：  
本项目用来实现springboot整合多数据源，整合redis，整合Redisson，进行lua脚本测试，进行Redisson开发测试  

#### 多数据源整合-log：  
2020-01-15：配置多个数据源的配置文件，目前为，通过在service层方法上，设置目标注解，进行切换数据源，但是存在一个问题，无法在同一个service层中进行数据源的切换

推荐文章：  
(1)[(1条消息)【SpringBoot】动态调用双数据源（使用参数实现，解决同名Bean问题）_唐Ian的博客-CSDN博客](https://blog.csdn.net/tanglei6636/article/details/81459820)

#### 整合redis-log：  
2020-01-15：配置springboot整合redis的配置类，实现key的序列化

推荐文章：  


#### 整合Redisson-log：  
暂无

推荐文章：  
(1)[redisson/redisson Wiki](https://github.com/redisson/redisson/wiki/目录)
(2)[(1条消息)SpringBoot整合redisson分布式锁_congge-CSDN博客](https://blog.csdn.net/zhangcongyi420/article/details/89980469)

#### lua脚本测试：
2020-01-15：实现list结构下，list中单个元素解析为lua的一个对象，实现table数据类型，对象数据类型的认识

推荐文章：  
(1)[Lua的泛型for循环 - allen_tony的博客](https://blog.csdn.net/allen_tony/article/details/78559057)  
(2)[redis-cli --eval 执行 lua 脚本 传参问题 - SegmentFault 思否](https://segmentfault.com/q/1010000017843054)  
(3)[redis学习笔记之十三：Lua脚本开发 - u010800970的专栏 - CSDN博客](https://blog.csdn.net/u010800970/article/details/81834965)  
