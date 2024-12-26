# 스프링 부트 + 도커 컴포즈 실습 프로젝트

- 이 프로젝트는 스프링부트 웹 애플리케이션과 도커 컴포즈 실습 프로젝트다.
- 이 프로젝트는 스프링 부트 3.1 이상에서 지원하는 **Docker Compose Support**의존성을 사용하였다.

## 프로젝트 의존성
- spring-web
- Docker Compose Support
- MySQL Driver
- Spring Data JPA
- Lombok
- Spring Boot DevTools

### Docker Compose Support 의존성
- Docker Compose Support 의존성은 Spring Boot 애플리케이션을 컨테이너 환경에서 쉽게 실행하고 구성할 수 있도록 지원하는 기능을 제공한다.
- Spring Boot 애플리케이션이 Docker Compose를 통해 여러 서비스와 함께 실행될 때, 필요한 설정과 환경 구성을 자동화하거나 간소화할 수 있다.
- Spring Boot에서 Docker Compose Support는 **spring-boot-docker-compose 스타터**를 통해 제공되며, 이는 Spring Boot 3.1 이상 버전에서 사용할 수 있다.

#### 특징
- 자동 서비스 디스커버리
  - Docker Compose의 서비스 이름을 기반으로 Spring Boot 애플리케이션의 데이터베이스, 메시지 브로커 등의 서비스 URL과 포트를 자동으로 구성한다
  - 예를 들어, db라는 MySQL 서비스가 Docker Compose에 정의되어 있다면, Spring Boot는 이를 자동으로 탐지하여 연결할 수 있다.
- 환경 변수 관리
  - Docker Compose에서 설정된 환경 변수(예: SPRING_DATASOURCE_URL, SPRING_DATASOURCE_USERNAME)를 자동으로 읽어와 Spring Boot 애플리케이션의 설정에 반영한다.
- 개발 환경 통합
  - Docker Compose를 활용한 개발 환경에서 빠르게 여러 서비스를 실행하고 상호작용할 수 있도록 지원한다.
- 간단한 설정
  - 복잡한 Docker Compose 설정 없이도 Spring Boot 애플리케이션이 관련 서비스를 간단히 인식하고 사용할 수 있다.
 
## 프로젝트의 파일 구성

### compose.yaml

- compose.yaml은 스프링 부트 애플리케이션 실행에 필요한 하나 이상의 서비스를 도커 컴포즈로 실행해주는 설정파일이다. (스프링 부트 애플리케이션은 컨테이너 기반으로 실행되지 않는다.)
- 스프링 부트 애플리케이션을 실행하면 Docker Compose Support는 compose.ㅁyml에 정의된 서비스를 자동으로 탐지하고, 컨테이너를 생성하고 실행한다.
- 스프링 부트 애플리케이션에서는 별도의 설정없이 Docker Compose 서비스를 자동으로 인식하고 연결을 설정한다.
- 동작 방식
  - Docker Compose Support는 Docker 데몬과 통신하여 compose.yaml로 정의된 컨테이너의 상태를 확인한다.
  - 컨테이너의 서비스 이름과 포트를 Spring Boot 애플리케이션 컨텍스트에 주입한다. 예를 들어, MySQL 서비스의 URL은 jdbc:mysql://db:3306과 같이 자동 구성됩니다.
  - SPRING_PROFILES_ACTIVE 환경 변수에 따라 개발, 테스트, 프로덕션 환경을 구분해 실행할 수 있다.

```yml
services:
  db:
    container_name: mysql_db
    image: 'mysql:latest'
    environment:
      - 'MYSQL_ROOT_PASSWORD=1234'
      - 'MYSQL_DATABASE=mydb'
      - 'MYSQL_USER=user'
      - 'MYSQL_PASSWORD=1234'
    ports:
      - '3306'
    volumes:
      - mysql_data:/var/lib/mysql
    networks:
      - backend-network
volumes:
  mysql_data:
networks:
  backend-network:
```

### docker-compose.yaml

- docker-compose.yaml은 스프링 부트 애플리케이션 실행에 필요한 하나 이상의 서비스와 스프링  부트 애플맄이션을 도커 컴포즈로 실행해주는 설정파일이다. (스프링 부트 애플리케이션도 컨테이너 기반으로 실행된다.)
- docker-compose.yaml을 이용해서 도커 컴포즈를 실행하기 전에 스프링 부트 애플리케이션은 이미 패키징되어 있어야 한다.
  
```yml
services:
  db:
    container_name: mysql_db
    image: 'mysql:latest'
    environment:
      - 'MYSQL_ROOT_PASSWORD=1234'
      - 'MYSQL_DATABASE=mydb'
      - 'MYSQL_USER=user'
      - 'MYSQL_PASSWORD=1234'
    ports:
      - '3306:3306'
    volumes:
      - mysql_data:/var/lib/mysql
    networks:
      - backend-network
  app:
    container_name: todo_app
    build:
      context: .
      dockerfile: Dockerfile
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql_db:3306/mydb
      SPRING_DATASOURCE_USERNAME: user
      SPRING_DATASOURCE_PASSWORD: 1234
    ports:
      - "8080:8080"
    depends_on:
      - db
    networks:
      - backend-network
    restart: on-failure
volumes:
  mysql_data:
networks:
  backend-network:
```

#### 실행하기
```bash
# github에서 저장소를 복제한다.
git clone https://github.com/eungsu/demo-docker-compose.git

# 프로젝트 폴더로 이동한다.
cd demo-docker-compose

# 스프링 부트 프로젝트를 패키징한다.
mvnw clean package -DskipTests

# docker compose를 실행한다.
docker-compose -f docker-compose.yaml up --build -d
```

### Dockerfile

- 이 Dockerfile은 스프링 부트 애플리케이션을 도커 이미지로 빌드한다.
- docker-compose.yaml에서 이 Dockerfile을 읽어서 도커 이미지를 빌드한 다음 컨테이너를 실행한다.

```Dockerfile
# Base image
FROM openjdk:17-jdk-alpine

# Application directory
WORKDIR /app

# Copy the Spring Boot JAR file
COPY target/demo-docker-compose-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

```



