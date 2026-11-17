FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY Calculo.java .
RUN  javac Calculo.java
EXPOSE 8090 
CMD [ "java","Calculo" ]