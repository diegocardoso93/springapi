<?php

namespace App\Services;

use App\Models\ServidorEfetivo;
use App\Models\Endereco;
use Illuminate\Support\Facades\DB;

class ServidorEfetivoService
{
    public function findServidoresEfetivosLotadosPorUnidade($unidadeId)
    {
        return ServidorEfetivo::whereHas('lotacoes', function($query) use ($unidadeId) {
            $query->where('unid_id', $unidadeId);
        })->with(['pessoa', 'lotacoes.unidade'])->get();
    }

    public function findEnderecoFuncionalByServidorNomeContaining($nome)
    {
        return ServidorEfetivo::whereHas('pessoa', function($query) use ($nome) {
            $query->where('pes_nome', 'like', "%{$nome}%");
        })->with(['pessoa', 'lotacoes.unidade.endereco.cidade'])->get();
    }
}
