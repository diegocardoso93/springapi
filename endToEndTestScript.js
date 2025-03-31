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

// inserir efetivo
response = await apiPost('/api/servidores-efetivos', {
  "pesNome": "Nome Efetivo1",
  "pesDataNascimento": "2000-03-31",
  "pesSexo": "M",
  "pesMae": "Mae",
  "pesPai": "Pai",
  "seMatricula": "MAT001",
  "unidadeId": 1
});

console.log(response);
let pesIdEfetivo = response.pesId;

async function uploadAvatarImage(pesId) {
  let avatarImgUrl = 'https://img.freepik.com/free-psd/3d-rendering-hair-style-avatar-design_23-2151869121.jpg?t=st=1743362366~exp=1743365966~hmac=4bedb9435f0bb42676ae37065caea18ca315f4aa63cefeb7cf3593b4c4b1d7de&w=300';
  const imageRes = await fetch(avatarImgUrl, {
    mode: 'no-cors',
    headers: {'Access-Control-Allow-Origin': '*'}
  });
  let image = await imageRes.blob();
  const formData = new FormData();

  formData.append('files', image, `avatar${pesId}.jpg`);
  formData.append('pesId', pesId);

  try {
    let res = await fetch(`${BASE_URL}/api/fotos/upload`, {
      method: 'POST',
      body: formData,
      headers: {'Authorization': `Bearer ${jwtToken}`}
    });

    if (res.ok) {
      console.log('Foto enviada com sucesso!');
    } else {
      console.error('envio de foto falou:', res.status, res.statusText);
    }
  } catch (error) {
      console.error('Erro ao executar a requisição:', error);
  }
}

await uploadAvatarImage(pesIdEfetivo);



// // inserir temporario
// response = await apiPost('/api/servidores-efetivos', {
//   "pesNome": "Nome Temporario1",
//   "pesDataNascimento": "2000-03-31",
//   "pesSexo": "M",
//   "pesMae": "Mae",
//   "pesPai": "Pai",
//   "seMatricula": "MAT002",
//   "unidadeId": 1
// });

// console.log(response);
// let pesIdETemporario = response.pesId;

