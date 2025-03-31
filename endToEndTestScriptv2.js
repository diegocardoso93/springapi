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
  }
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
    console.log('Logged in successfully. Token:', data.token);
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
}

async function main() {
  try {
    const api = new ApiClient(BASE_URL);
    await api.login(TEST_CREDENTIALS);

    const unit = await api.post('/api/unidades', {
      unidNome: TEST_DATA.unit.name,
      unidSigla: TEST_DATA.unit.sigla
    });
    console.log('Unidade criada:', unit);
    const unidId = unit.unidId;

    const efetivo = await api.post('/api/servidor-efetivo', {
      pesNome: TEST_DATA.efetivo.nome,
      pesDataNascimento: new Date().toISOString(),
      pesSexo: TEST_DATA.efetivo.sexo,
      pesMae: TEST_DATA.efetivo.mae,
      pesPai: TEST_DATA.efetivo.pai,
      seMatricula: TEST_DATA.efetivo.matricula
    });
    console.log('Servidor efetivo criado:', efetivo);
    const efetPesId = efetivo.pesId;

    const temporario = await api.post('/api/servidor-temporario', {
      pesNome: TEST_DATA.temporario.nome,
      pesDataNascimento: new Date().toISOString(),
      pesSexo: TEST_DATA.temporario.sexo,
      pesMae: TEST_DATA.temporario.mae,
      pesPai: TEST_DATA.temporario.pai,
      stDataAdmissao: new Date().toISOString(),
      stDataDemissao: null
    });
    console.log('Servidor temporário criado:', temporario);
    const tempPesId = temporario.pesId;

    const lotacaoEfetivo = await api.post('/api/lotacoes', {
      pesId: efetPesId,
      unidId: unidId,
      lotDataLotacao: new Date().toISOString(),
      lotDataRemocao: null,
      lotPortaria: 'Port. 001'
    });
    console.log('Lotação do servidor efetivo criada:', lotacaoEfetivo);

    const lotacaoTemporario = await api.post('/api/lotacoes', {
      pesId: tempPesId,
      unidId: unidId,
      lotDataLotacao: new Date().toISOString(),
      lotDataRemocao: null,
      lotPortaria: 'Port. 002'
    });
    console.log('Lotação do servidor temporario criada:', lotacaoTemporario);

    console.log('As operações foram concluídas com sucesso.');
  } catch (error) {
    console.error('Erro ao execução o teste:', error);
    throw error;
  }
}

main().catch(() => console.error('A execução do teste falhou'));
