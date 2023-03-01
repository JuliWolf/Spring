## Настройка сертификата

1. Сформировать сертификат
2. Добавить сертификат в папку resourses
3. В файле `application.properties` прописать настройки
```
server.ssl.key-store=classpath:ssl/serverkeystore.jks
server.ssl.key-store-type=JKS
server.ssl.key-store-password=topsecret
server.ssl.key-alias=tcserver
```

