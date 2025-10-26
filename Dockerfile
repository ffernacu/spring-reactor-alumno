# ======================== ETAPA DE COMPILACIÓN ========================
# Usa una imagen oficial de Gradle con JDK 17 para compilar la aplicación.
# Se recomienda usar una versión específica para mayor control.
FROM gradle:8.8-jdk17 AS build

# Establece el directorio de trabajo dentro del contenedor.
WORKDIR /app

# Copia los archivos de configuración de Gradle para que el sistema de caché de Docker
# pueda reutilizar las dependencias si no hay cambios en ellos.
COPY build.gradle settings.gradle ./
COPY gradle gradle

# Copia el código fuente del proyecto.
COPY src ./src

# Compila la aplicación. El comando `bootJar` de Gradle se encarga de crear
# el JAR ejecutable de Spring Boot. El flag `--no-daemon` es útil para entornos de CI.
RUN gradle bootJar --no-daemon

# ========================= ETAPA DE EJECUCIÓN =========================
# Utiliza una imagen ligera de Eclipse Temurin 17 con JRE para el entorno de producción.
FROM eclipse-temurin:17-jre-jammy

# Establece el directorio de trabajo.
WORKDIR /app

# Crea un usuario no-root para mayor seguridad.
RUN groupadd --system springboot && useradd --system --gid springboot springboot
USER springboot

# Expone el puerto por defecto de Spring Boot.
EXPOSE 8080

# Define opciones para la JVM, optimizadas para entornos en contenedores.
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=80.0 -XX:InitialRAMPercentage=80.0"

# Copia el JAR ejecutable desde la etapa de compilación (`build`).
# El JAR se encuentra en el directorio `build/libs/`.
COPY --from=build /app/build/libs/*.jar /app/app.jar

# Define el comando para ejecutar la aplicación al iniciar el contenedor.
ENTRYPOINT ["java", "-jar", "app.jar"]
