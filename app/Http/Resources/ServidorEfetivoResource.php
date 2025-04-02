<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\JsonResource;

class ServidorEfetivoResource extends JsonResource
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
            'se_matricula' => $this->se_matricula,
            'enderecos' => $this->pessoa->enderecos->map(function($endereco) {
                return [
                    'end_id' => $endereco->end_id,
                ];
            }),
        ];
    }
}
