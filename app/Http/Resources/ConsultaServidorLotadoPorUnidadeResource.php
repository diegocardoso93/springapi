<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\JsonResource;

class ConsultaServidorLotadoPorUnidadeResource extends JsonResource
{
    public function toArray($request)
    {
        return [
            'pes_id' => $this->pes_id,
            'nome' => $this->pessoa->pes_nome,
            'idade' => now()->diffInYears($this->pessoa->pes_data_nascimento),
            'unidade' => $this->lotacoes->first()->unidade->unid_nome,
            'fotografia' => $this->getFotoLink(),
        ];
    }
}
