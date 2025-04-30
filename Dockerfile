# Estágio de build
FROM gradle:8.4.0-jdk17 AS build
WORKDIR /app

# Desativa cache problemático e configurações extras
ENV GRADLE_OPTS="-Dorg.gradle.daemon=false -Dorg.gradle.caching=false"
ENV GRADLE_USER_HOME=/app/.gradle

# Copia apenas os arquivos necessários primeiro para aproveitar cache Docker
COPY build.gradle .
COPY settings.gradle .
COPY src ./src

# Força a versão do Jackson Core para uma compatível
RUN sed -i "s/jackson-core:.*/jackson-core:2.15.2'/g" build.gradle || \
    echo "Não foi possível ajustar versão do Jackson, continuando..."

# Build da aplicação
RUN gradle --no-daemon bootJar && \
    rm -rf /app/.gradle

# Estágio de execução
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar wishlist.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "wishlist.jar"]