# Springapi

Autor: Diego Cardoso  

Fazer download da branch php:
```
git clone -b php https://github.com/diegocardoso93/springapi.git
```

Para rodar a aplicação:
```
docker compose up -d --build
docker compose exec app composer install
docker compose exec app chown -R www-data:www-data storage bootstrap/cache
```

Acessar a página: `http://localhost:8000/swagger-ui/index.html`  
Para testar sem problemas de CORS, colar o conteúdo inteiro do arquivo `endToEndTestScript.js` no console javascript.
