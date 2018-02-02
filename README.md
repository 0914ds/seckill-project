# seckill-project

项目参考慕思网秒杀课程视频做的改造,包含如下几个模块:

seckill-common 整合了dto,exceptin,model,utils等公共类  
seckill-config-server db配置信息读取服务    
seckill-eureka-server 注册/订阅服务   
seckill-killed-service  处理业务逻辑服务    
seckill-killed-web      前端数据展示   
原项目中减库存和下单通过存储过程实现,这里还是通过代码的方式实现


项目启动顺序  
1,seckill-config-server  
2,seckill-eureka-server  
3,seckill-killed-service 修改端口后可以启动多实例 springCloud 实现了loadbalance    
4,seckill-killed-web 调用seckill-killed-service服务实现数据展示