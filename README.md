# 项目说明
通过简单配置，然后运行项目就能打开一个Javadoc文档服务器。

利用了 Spring Boot 跨平台的特性，只需要 JRE 就能在 Linux 、 Mac OS 和 Windows 直接运行。

# 配置说明
application.properties:
```properties
# web服务打开的端口
server.port=8888
# 整个网页文件及下载的源码保存位置
file.location=/Users/marwin/test

# spring的配置不需要修改
spring.mvc.static-path-pattern=/**
spring.resources.static-locations=file:${file.location}
```

config.json:
```
[  // 可以配置多个项目
  {
    "projectName": "Javadoc-gen",                             // 项目名
    "projectUrl": "git@github.com:marwincn/Javadoc-gen.git",  // 项目的git仓库路径
    "modules": [ // 每个项目可以配置多个module
      {
        "moduleName": "main",           // module名，可以任意取名
        "modulePath": "src/main/java",  // module在项目下的相对路径
        "packages": [                   // 需要生成Javadoc的包
          "cn.marwin"
        ]
      }
    ]
  }
]
```

# 运行

配置好配置文件后，下载maven依赖，直接运行Application类中的main函数。等项目运行结束后登录浏览器访问: 

* 单module：`http://localhost:8888/{projectName}/index.html`

* 多module：`http://localhost:8888/{projectName}/{moduleName}/index.html`

也可以直接访问`http://localhost:8888`首页查看所有生成的JavaDoc目录。

## 打包运行

配置好配置文件后，在项目根目录执行`mvn clean package`打包项目，在target目录下得到`Javadoc-gen-1.0-SNAPSHOT.jar`文件；

将jar包上传到环境，执行：
```shell script
nohup java -jar Javadoc-gen-1.0-SNAPSHOT.jar > Javadoc-gen.log &
```