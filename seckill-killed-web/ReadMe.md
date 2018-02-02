here is some records about problems during getting this module(seckill-killed-web) started

it seems that springBoot can not support render .jsp file very well.but you insist to work a 
project based on springBoot and .jsp,some information should be noted:
    
1,two dependencies should be added to pom.xml   
   		<dependency>
   			<groupId>javax.servlet</groupId>
   			<artifactId>jstl</artifactId>
   		</dependency>
   		<!--添加tomcat对jsp的支持-->
   		<dependency>
   		    <groupId>org.apache.tomcat.embed</groupId>
   		    <artifactId>tomcat-embed-jasper</artifactId>
   		</dependency>   
2,selecting spring Version  
3, spring.mvc.view.prefix=/WEB-INF/jsp/ 
   spring.mvc.view.suffix=.jsp  
   OR   
   spring.view.prefix=/WEB-INF/jsp/ 
   spring.view.suffix=.jsp  
4,"< packaging >war</ packaging >"    
5,if you are working with IntellijIdea,then make sure, this module must run up by  
  typing command "mvn spring-boot:run" in Terminal.

