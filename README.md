#预约挂号平台

## common 公共模块父节点

### common_util  

工具类模块，所有模块都可以依赖于它

#### exception 

GlobalExceptionHandler：自定义异常处理

YyghException：自定义全局异常类

#### result

Result：全局统一返回结果类

ResultCodeEnum：统一返回结果状态信息类

### rabbit_util

创建订单发送的队列、路由、交换机

### service_util  

service服务的工具包，包含service服务的公共配置类，所有service模块依赖于它

#### config

Swagger2Config：Swagger2配置信息

RedisConfig：redis缓存配置

#### utils

MD5：md5加密

---

## model 实体类模块

### enums

### model

#### cmn

#### hosp

### vo

#### cmn

---

## service 业务类模块

### service_hosp   医院相关模块

#### config

HospConfig：医院设置的配置类

#### controller

##### HospitalSetController：

医院设置管理

##### ApiController 

链接医院接口模拟系统

HospApiController

预约挂号用户端前台接口

##### HospitalController：

医院查询

将mongodb中的数据查询出来显示到前端页面

##### DepartmentController：

根据医院编号查询医院所有科室列表

##### ScheduleController：

根据医院编号 和 科室编号 查询排班规则 和 排班详细信息

#### receiver

接受下单成功的消息（rabbitmq）、并发送短信给下单者

#### mapper

HospitalSetMapper、HospitalSetMapper.xml：医院设置mapper类

#### repository

HospitalRepository、DepartmentRepository、ScheduleRepository：均继承MongoRepository接口，实现mongodb的增删改查操作

#### service

##### HospitalSetServiceImpl：

医院设置类

##### HospitalServiceImpl : 

医院信息接口类（包括上传、查询功能，包括从查询mongodb查询数据）、更新医院上线状态、医院详情信息、根据医院名称查询、根据医院编号获取所有预约挂号信息

##### DepartmentServiceImpl：

医院科室接口类（上传、查询、删除） 、根据医院编号查询医院所有科室列表、根据医院编号，科室编号 查询科室名称

##### ScheduleServiceImpl：

医院排班信息接口类（上传、查询、删除）

根据医院编号 和 科室编号 查询排班规则

根据医院编号 和 科室编号 和 工作日期 查询排班详细信息

获取可预约排班数据、根据排班id获取排班数据

根据排班id获取预约下单数据、更新排班数据 用于mq



---

### service_cmn 数据字典模块

#### config

CmnConfig：数据字典配置类

#### controller

DictController：数据字典接口

#### mapper

DictMapper：数据字典设置mapper类

#### service

DictServiceImpl：数据字典业务类

#### listener

DictListener：监听器（用来将数据库数据导出到excel中，或导入excel到数据库）

---

### service_oss 文件上传模块（阿里云oss）

#### controller

FileApiController：上传文件到阿里云oss

#### service

FileServiceImpl：获取上传文件

#### utils

ConstantOssPropertiesUtils：读取配置文件

---

### service_user 会员服务模块

#### api

UserInfoApiController：用户功能接口，（手机登录）、根据openid判断数据库是否存在微信扫码人的信息、用户认证

PatientApiController：就诊人功能接口 ，获取就诊人的列表、添加就诊人、根据用户id获取就诊人的信息、修改 和 删除 就诊人

WeixinApiController：微信扫码登录接口，实现生成二维码和登录功能

#### config

#### controller

UserController：管理员前端模块中的用户接口，用户列表（条件查询带分页）、用户锁定、用户详情、认证审批（修改用户状态）	

#### mapper

UserInfoMapper、PatientMapper

#### service

UserInfoServiceImpl：包含用户前端模块的方法以及管理员前端模块中的方法

PatientServiceImpl：获取就诊人的列表、根据id获取就诊人的信息、patient对象的其他参数的封装、

用户模块：

- 用户手机号登录接口   **//TODO 短信服务未申请，验证码固定为123456**
- 根据openid判断数据库是否存在微信扫码人的信息
- 用户认证

管理员模块：

- 用户列表（条件查询带分页）
- 用户锁定
- 用户详情
- 认证审批（修改用户状态）	

PatientServiceImpl：patientcontroller中方法的实现

### service_msm 短信发送、消息队列发送模块

MsmApiController、MsmServiceImpl 消息发送

MsmReceiver  消息接收

### service_order 订单模块

#### api

OrderApiController：用户前端接口

生成挂号订单、根据订单id查询订单的详情、订单列表、取消预约

#### config

#### mapper

OrderMapper、PaymentMapper、RefundInfoMapper

#### controller

OrderController：管理员前端获取订单信息接口

获取分页列表、获取订单状态、获取订单

#### service

OrderServiceImpl：

远程调用 user hosp

生成挂号订单

根据订单id查询订单的详情、订单列表（条件查询带分页）、获取订单

取消预约

PaymentService：

向支付记录表添加信息、更改订单状态，处理支付结果、获取支付记录

RefundInfoService：

保存退款记录

WeixinService：

生成微信支付扫描的二维码、调用微信的接口实现状态查询、退款

### service_task 定时任务模块

#### schedule

ScheduledTask：每天8点定时发送任务

### service_statistics 预约统计管理模块

#### controller

StatisticsController 获取预约统计数据

---

## service_client (封装Feign服务，实现模块间调用)

### service_cmn_client (远程调用service_cmn模块的方法)

#### client

DictFeignClient：接口，通过 @FeignClient("service-cmn") 注解绑定模块，并将方法写在这个接口里，以便能让别的模块间接调用

### service_hosp_client

### service_order_client

### service_user_client 

## service_gateway(配置网关，解决跨域问题)

pom.xml

application.properties

```properties
# 服务端口
server.port=80
# 服务名
spring.application.name=service-gateway

# nacos服务地址
spring.cloud.nacos.discovery.server-addr=127.0.0.1:8848

#使用服务发现路由
spring.cloud.gateway.discovery.locator.enabled=true

#设置路由id
spring.cloud.gateway.routes[0].id=service-hosp
#设置路由的uri
spring.cloud.gateway.routes[0].uri=lb://service-hosp
#设置路由断言,代理servicerId为auth-service的/auth/路径
spring.cloud.gateway.routes[0].predicates= Path=/*/hosp/**

#设置路由id
spring.cloud.gateway.routes[1].id=service-cmn
#设置路由的uri
spring.cloud.gateway.routes[1].uri=lb://service-cmn
#设置路由断言,代理servicerId为auth-service的/auth/路径
spring.cloud.gateway.routes[1].predicates= Path=/*/cmn/**
```
