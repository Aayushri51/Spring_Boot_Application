# ---------- Build Stage ----------
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# ----------- Runtime Stage ----------
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java","-jar","app.jar"]
#-----------nginx stage----------
# Remove the default NGINX page
RUN rm -rf /usr/share/nginx/html/*

# Copy your website
COPY nginx/index.html /usr/share/nginx/html/

# Expose HTTP port
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]