<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\JsonResource;

class LotacaoResource extends JsonResource
{
    public function toArray($request)
    {
        return [
            'lot_id' => $this->lot_id,
            'lot_data_lotacao' => $this->lot_data_lotacao,
            'lot_data_remocao' => $this->lot_data_remocao,
            'lot_portaria' => $this->lot_portaria,
            'pes_id' => $this->pes_id,
            'unid_id' => $this->unid_id,
            'pessoa' => $this->whenLoaded('pessoa', function () {
                return [
                    'pes_id' => $this->pessoa->pes_id,
                    'pes_nome' => $this->pessoa->pes_nome
                ];
            }),
            'unidade' => $this->whenLoaded('unidade', function () {
                return [
                    'unid_id' => $this->unidade->unid_id,
                    'unid_nome' => $this->unidade->unid_nome
                ];
            })
        ];
    }
}
