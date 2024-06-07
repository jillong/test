# 亲测可行的版本搭配(其它版本可以自己尝试，我试了很多版本，大多都有问题)
maven 3.9.6
IDEA 2023
JDK17+
spring boot 3.2.5
spring-ai-bom 0.8.0-SNAPSHOT

## 注意：spring AI的开发需要使用默认的maven原仓库，配置国内镜像源会导致一些依赖无法下载

## 网上博主提供的免费共享key,获取地址: https://pgthinker.me/2023/10/03/196/

# 数据库配置
  1、PGvector
  (1) 拉取最新镜像 docker pull pgvector/pgvector:pg16
  (2) 启动容器 docker run --name pgvector -e POSTGRES_PASSWORD=pgvector -p 5432:5432 -d pgvector/pgvector:pg16
  (3) 将pgvector注入spring，spring会自动创建名为vector_store的表，将数据存储在这里
  (4) 数据库连接配置
      spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
      spring.datasource.username=postgres
      spring.datasource.password=pgvector
