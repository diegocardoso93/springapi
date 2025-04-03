# Springapi

Autor: Diego Cardoso  

Para rodar a aplicação:
```
docker compose up -d --build
docker-compose exec app composer install
docker-compose exec app chown -R www-data:www-data storage bootstrap/cache
docker-compose exec app php artisan migrate
```

Acessar a página: `http://localhost:8080/swagger-ui/index.html`  
Para testar sem problemas de CORS, colar o conteúdo do arquivo `endToEndTestScript.js` no console.
