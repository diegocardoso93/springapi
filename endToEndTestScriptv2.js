
// Executar no console da página http://localhost:8080/swagger-ui/index.html

const BASE_URL = 'http://localhost:8080';
const TEST_CREDENTIALS = {
  username: 'ADMIN_USER',
  password: 'PASSWORD'
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
  avatarUrl: 'https://img.freepik.com/free-psd/3d-rendering-hair-style-avatar-design_23-2151869121.jpg' // Simplified URL
};

class ApiClient {
  constructor(baseUrl) {
    this.baseUrl = baseUrl;
    this.token = null;
  }

  async login(credentials) {
    const response = await fetch(`${this.baseUrl}/api/auth/login`, {
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

  post(url, data) {
    return this.request(url, 'POST', data);
  }

  async postFormData(url, formData) {
    const headers = {
      ...(this.token && { Authorization: `Bearer ${this.token}` })
    };

    const response = await fetch(`${this.baseUrl}${url}`, {
      method: 'POST',
      headers,
      body: formData
    });

    if (!response.ok) {
      const error = await response.json();
      throw new Error(`Upload failed: ${JSON.stringify(error)}`);
    }

    return response.json();
  }

  async uploadAvatar(pesId) {
    const imageResponse = await fetch(TEST_DATA.avatarUrl);
    if (!imageResponse.ok) {
      throw new Error(`Failed to fetch image: ${imageResponse.status}`);
    }

    const imageBlob = await imageResponse.blob();
    const formData = new FormData();
    formData.append('files', imageBlob, `avatar${pesId}.jpg`);
    formData.append('pesId', pesId.toString());

    return this.postFormData('/api/fotos/upload', formData);
  }
}

async function main() {
  try {
    const api = new ApiClient(BASE_URL);
    await api.login(TEST_CREDENTIALS);

    const cidade = await api.post('/api/cidades', {
      cidNome: "Cidade 1",
      cidUf: "UF"
    });
    const cidId = cidade.cidId;

    const enderecoUnidade = await api.post('/api/enderecos', {
      endTipoLogradouro: "Rua",
      endLogradouro: "Fictícia Unidade",
      endNumero: 10,
      endBairro: "Fictício Unidade",
      cidadeId: cidId
    });
    const endIdUnidade = enderecoUnidade.endId;

    const unidade = await api.post('/api/unidades', {
      unidNome: TEST_DATA.unit.name,
      unidSigla: TEST_DATA.unit.sigla,
      enderecoIds: [endIdUnidade]
    });
    const unidId = unidade.unidId;

    const enderecoEfetivo = await api.post('/api/enderecos', {
      endTipoLogradouro: "Rua",
      endLogradouro: "Fictícia Efetivo",
      endNumero: 10,
      endBairro: "Fictício Efetivo",
      cidadeId: cidId
    });
    const endIdEfetivo = enderecoEfetivo.endId;

    const efetivo = await api.post('/api/servidor-efetivo', {
      pesNome: TEST_DATA.efetivo.nome,
      pesDataNascimento: new Date().toISOString(),
      pesSexo: TEST_DATA.efetivo.sexo,
      pesMae: TEST_DATA.efetivo.mae,
      pesPai: TEST_DATA.efetivo.pai,
      seMatricula: TEST_DATA.efetivo.matricula,
      enderecoIds: [endIdEfetivo]
    });
    const efetPesId = efetivo.pesId;
    await api.uploadAvatar(efetPesId);
    console.log('Upload da imagem do efetivo executada com sucesso.');

    const temporario = await api.post('/api/servidor-temporario', {
      pesNome: TEST_DATA.temporario.nome,
      pesDataNascimento: new Date().toISOString(),
      pesSexo: TEST_DATA.temporario.sexo,
      pesMae: TEST_DATA.temporario.mae,
      pesPai: TEST_DATA.temporario.pai,
      stDataAdmissao: new Date().toISOString(),
      stDataDemissao: null
    });
    const tempPesId = temporario.pesId;

    console.log('Servidor temporário criado:', { temporario });

    const lotacaoEfetivo = await api.post('/api/lotacoes', {
      pesId: efetPesId,
      unidId: unidId,
      lotDataLotacao: new Date().toISOString(),
      lotDataRemocao: null,
      lotPortaria: 'Port. 001'
    });
    console.log('Lotação do servidor efetivo criada:', { lotacaoEfetivo });

    const lotacaoTemporario = await api.post('/api/lotacoes', {
      pesId: tempPesId,
      unidId: unidId,
      lotDataLotacao: new Date().toISOString(),
      lotDataRemocao: null,
      lotPortaria: 'Port. 002'
    });
    console.log('Lotação do servidor temporario criada:', { lotacaoTemporario });

    console.log('Todas as operações foram concluídas com sucesso.');
  } catch (error) {
    console.error('Erro na execução do teste:', error);
    throw error;
  }
}

main().catch(() => console.error('A execução do teste falhou'));
