
// Executar no console da página http://localhost:8000/swagger-ui/index.html

const BASE_URL = 'http://localhost:8000';
const TEST_CREDENTIALS = {
  username: 'johndoe',
  password: 'secret'
};
const TEST_DATA = {
  unit: {
    name: 'Unidade 1',
    sigla: 'Unid1'
  },
  efetivo: {
    nome: 'Efetivo 1',
    sexo: 'M',
    mae: 'Mae Efetivo 1',
    pai: 'Pai Efetivo 1',
    matricula: 'MAT001'
  },
  temporario: {
    nome: 'Temporario 1',
    sexo: 'F',
    mae: 'Mae Temporario 1',
    pai: 'Pai Temporario 1'
  },

  fotoBase64: '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'
};

function getImageFromBase64(base64String) {
  const byteCharacters = atob(base64String);
  const byteNumbers = new Array(byteCharacters.length);
  for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
  }
  const byteArray = new Uint8Array(byteNumbers);
  
  return new Blob([byteArray], { type: 'image/png' });
}

class ApiClient {
  constructor(baseUrl) {
    this.baseUrl = baseUrl;
    this.token = null;
  }

  async login(credentials) {
    try {
      await fetch(`${this.baseUrl}/api/register`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(credentials)
      });
    } catch(e) {}

    const response = await fetch(`${this.baseUrl}/api/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(credentials)
    });

    if (!response.ok) {
      throw new Error(`Login failed: ${response.status}`);
    }

    const data = await response.json();
    this.token = data.token;
    console.log('Logado com sucesso. Token:', data.token);
    return data;
  }

  async request(url, method = 'GET', body = null) {
    const headers = {
      'Content-Type': 'application/json',
      ...(this.token && { Authorization: `Bearer ${this.token}` })
    };

    const config = {
      method,
      headers,
      ...(body && { body: JSON.stringify(body) })
    };

    const response = await fetch(`${this.baseUrl}${url}`, config);

    if (!response.ok) {
      const error = await response.json();
      throw new Error(`A requisição à API falhou: ${JSON.stringify(error)}`);
    }

    return response.json();
  }

  get(url, data) {
    return this.request(url, 'GET', data);
  }

  post(url, data) {
    return this.request(url, 'POST', data);
  }

  async postFormData(url, formData) {
    const headers = {
      // 'Content-Type': 'multipart/form-data',
      ...(this.token && { Authorization: `Bearer ${this.token}` })
    };

    const response = await fetch(`${this.baseUrl}${url}`, {
      method: 'POST',
      headers,
      body: formData,
    });

    if (!response.ok) {
      const error = await response.json();
      throw new Error(`Upload failed: ${JSON.stringify(error)}`);
    }

    return response.json();
  }

  async uploadFoto(pesId) {
    const formData = new FormData();
    formData.append('files', getImageFromBase64(TEST_DATA.fotoBase64), `foto${pesId}.png`);
    formData.append('pes_id', pesId.toString());

    return this.postFormData('/api/fotos/upload', formData);
  }
}

function camelToSnake(str) {
  return str.replace(/[A-Z]/g, letter => `_${letter.toLowerCase()}`);
}

function convertKeysToSnakeCase(obj) {
  if (Array.isArray(obj)) {
    return obj.map(convertKeysToSnakeCase);
  } else if (typeof obj === 'object' && obj !== null) {
    return Object.keys(obj).reduce((acc, key) => {
      acc[camelToSnake(key)] = convertKeysToSnakeCase(obj[key]);
      return acc;
    }, {});
  }
  return obj;
}

async function main() {
  try {
    const api = new ApiClient(BASE_URL);
    await api.login(TEST_CREDENTIALS);

    const cidade = await api.post('/api/cidades', convertKeysToSnakeCase({
      cid_nome: "Cidade 1",
      cid_uf: "UF"
    }));
    const cidId = cidade.cid_id;
    console.log('Cidade criada:', cidade);

    const unidade = await api.post('/api/unidades', convertKeysToSnakeCase({
      unid_nome: TEST_DATA.unit.name,
      unid_sigla: TEST_DATA.unit.sigla,
    }));
    const unidId = unidade.unid_id;
    console.log('Unidade criada:', unidade);

    const enderecoUnidade = await api.post(`/api/unidades/${unidId}/enderecos`, convertKeysToSnakeCase({
      end_tipo_logradouro: "Rua",
      end_logradouro: "Fictícia Unidade",
      end_numero: 10,
      end_bairro: "Fictício Unidade",
      cid_id: cidId
    }));
    console.log('Endereço da unidade criado:', enderecoUnidade);

    const efetivo = await api.post('/api/servidores-efetivos', convertKeysToSnakeCase({
      pes_nome: TEST_DATA.efetivo.nome,
      pes_data_nascimento: new Date('2000-01-01').toISOString(),
      pes_sexo: TEST_DATA.efetivo.sexo,
      pes_mae: TEST_DATA.efetivo.mae,
      pes_pai: TEST_DATA.efetivo.pai,
      se_matricula: TEST_DATA.efetivo.matricula,
    }));
    const efetPesId = efetivo.pes_id;
    await api.uploadFoto(efetPesId);
    console.log('Upload da imagem do servidor efetivo executado com sucesso.');

    const enderecoEfetivo = await api.post(`/api/servidores-efetivos/${efetPesId}/enderecos`, convertKeysToSnakeCase({
      end_tipo_logradouro: "Rua",
      end_logradouro: "Fictício Efetivo",
      end_numero: 10,
      end_bairro: "Fictício Efetivo",
      cid_id: cidId
    }));
    console.log('Endereço de servidor efetivo criado:', enderecoEfetivo);

    const temporario = await api.post('/api/servidores-temporarios', convertKeysToSnakeCase({
      pes_nome: TEST_DATA.temporario.nome,
      pes_data_nascimento: new Date('2008-01-01').toISOString(),
      pes_sexo: TEST_DATA.temporario.sexo,
      pes_mae: TEST_DATA.temporario.mae,
      pes_pai: TEST_DATA.temporario.pai,
      st_data_admissao: new Date().toISOString(),
      st_data_demissao: null
    }));
    const tempPesId = temporario.pes_id;

    console.log('Servidor temporário criado:', temporario);

    const lotacaoEfetivo = await api.post('/api/lotacoes', convertKeysToSnakeCase({
      pes_id: efetPesId,
      unid_id: unidId,
      lot_data_lotacao: new Date().toISOString(),
      lot_data_remocao: null,
      lot_portaria: 'Port. 001'
    }));
    console.log('Lotação do servidor efetivo criada:', lotacaoEfetivo);

    const lotacaoTemporario = await api.post('/api/lotacoes', convertKeysToSnakeCase({
      pes_id: tempPesId,
      unid_id: unidId,
      lot_data_lotacao: new Date().toISOString(),
      lot_data_remocao: null,
      lot_portaria: 'Port. 002'
    }));
    console.log('Lotação do servidor temporario criada:', lotacaoTemporario);

    const servidoresEfetivosByUnidId = await api.get(`/api/servidores-efetivos/unidade/${unidId}`);
    console.log(`Resultado da consulta de servidores efetivos lotados na unidade de ID ${unidId}:`, servidoresEfetivosByUnidId);

    const enderecosFuncionais = await api.get('/api/servidores-efetivos/endereco-funcional?nome=Efet');
    console.log('Resultado da consulta da lista de endereços funcionais:', enderecosFuncionais);

    console.log('Todas as operações foram concluídas com sucesso.');
  } catch (error) {
    console.error('Erro na execução do teste:', error);
    throw error;
  }
}

main().catch(() => console.error('A execução do teste falhou'));