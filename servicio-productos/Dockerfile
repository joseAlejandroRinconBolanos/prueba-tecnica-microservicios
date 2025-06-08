# Usa una imagen oficial de Java 21
FROM eclipse-temurin:21-jdk-alpine

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el .jar generado al contenedor
COPY target/*.jar app.jar

# Expone el puerto que tu microservicio usa (ajusta si es diferente)
EXPOSE 8081

# Comando que se ejecutará al iniciar el contenedor
ENTRYPOINT ["java", "-jar", "app.jar"]
