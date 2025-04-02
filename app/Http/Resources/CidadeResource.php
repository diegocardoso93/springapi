<?php

namespace App\Http\Resources;

use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

class CidadeResource extends JsonResource
{
    public function toArray(Request $request): array
    {
        return [
            'cidId' => $this->cid_id,
            'cidNome' => $this->cid_nome,
            'cidUf' => $this->cid_uf,
        ];
    }
}
