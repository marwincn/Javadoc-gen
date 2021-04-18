# 项目说明
通过简单配置，然后运行项目就能打开一个Javadoc文档服务器。

利用了 Spring Boot 跨平台的属性，只需要 JRE 就能在 Linux 、 Mac OS 和 Windows 直接运行。

# 配置说明
application.properties:
```properties
# web服务打开的端口
server.port=8888
# 整个网页文件及下载的源码保存位置
file.location=/Users/marwin/test

# spring的配置以需要修改
spring.mvc.static-path-pattern=/**
spring.resources.static-locations=file:${file.location}
```

config.json:
```
[  // 可以配置多个项目
  {
    "projectName": "AdminSystem",                             // 项目名
    "projectUrl": "git@github.com:marwincn/AdminSystem.git",  // 项目的git仓库路径
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