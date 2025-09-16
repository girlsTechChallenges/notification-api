# Usando JDK 21
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copiar pom.xml e baixar dependências para cache
COPY pom.xml .
RUN ./mvn dependency:go-offline -B

# Copiar código-fonte
COPY src ./src

# Build da aplicação
RUN ./mvn package -DskipTests

# Rodar o jar gerado
CMD ["java", "-jar", "target/*.jar"]
