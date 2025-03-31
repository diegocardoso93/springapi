// Executar no console da página http://localhost:8080/swagger-ui/index.html

const BASE_URL = "http://localhost:8080";

let res = await fetch(`${BASE_URL}/api/auth/login`, {
  method: 'post',
  body: JSON.stringify({username: 'ADMIN_USER', password: 'PASSWORD'}),
  headers: {'content-type': 'application/json'}
});
let json = await res.json();
let jwtToken = json.token;
console.log(json.token);

async function apiPost(url, data) {
  let res = await fetch(`${BASE_URL}${url}`, {
    method: 'post',
    body: JSON.stringify(data),
    headers: {'content-type': 'application/json', 'Authorization': `Bearer ${jwtToken}`}
  });
  return await res.json();
}

// inserir unidades
let response = await apiPost('/api/unidades', {unidNome: 'Unidade 1', unidSigla: 'Unid1'});
console.log(response);
