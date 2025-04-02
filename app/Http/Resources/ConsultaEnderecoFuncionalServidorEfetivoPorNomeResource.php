<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\JsonResource;

class ConsultaEnderecoFuncionalServidorEfetivoPorNomeResource extends JsonResource
{
    public function toArray($request)
    {
        $lotacao = $this->lotacoes->first();
        $unidade = $lotacao->unidade;
        $endereco = $unidade->endereco;
        
        return [
            'pes_id' => $this->pes_id,
            'pes_nome' => $this->pessoa->pes_nome,
            'unid_id' => $unidade->unid_id,
            'unid_nome' => $unidade->unid_nome,
            'unid_sigla' => $unidade->unid_sigla,
            'end_id' => $endereco->end_id,
            'end_tipo_logradouro' => $endereco->end_tipo_logradouro,
            'end_logradouro' => $endereco->end_logradouro,
            'end_numero' => $endereco->end_numero,
            'end_bairro' => $endereco->end_bairro,
            'cid_id' => $endereco->cidade->cid_id,
            'cid_nome' => $endereco->cidade->cid_nome,
            'cid_uf' => $endereco->cidade->cid_uf,
        ];
    }
}
