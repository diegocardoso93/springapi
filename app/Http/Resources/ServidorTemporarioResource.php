<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\JsonResource;

class ServidorTemporarioResource extends JsonResource
{
    public function toArray($request)
    {
        return [
            'pes_id' => $this->pes_id,
            'pes_nome' => $this->pessoa->pes_nome,
            'pes_data_nascimento' => $this->pessoa->pes_data_nascimento,
            'pes_sexo' => $this->pessoa->pes_sexo,
            'pes_mae' => $this->pessoa->pes_mae,
            'pes_pai' => $this->pessoa->pes_pai,
            'st_data_admissao' => $this->st_data_admissao,
            'st_data_demissao' => $this->st_data_demissao,
            'enderecos' => $this->pessoa->enderecos->map(function($endereco) {
                return [
                    'end_id' => $endereco->end_id,
                ];
            }),
        ];
    }
}
