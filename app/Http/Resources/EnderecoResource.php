<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\JsonResource;

/**
 * @OA\Schema(
 *     schema="EnderecoResource",
 *     type="object",
 *     @OA\Property(property="end_id", type="integer"),
 *     @OA\Property(property="end_tipo_logradouro", type="string", maxLength=50),
 *     @OA\Property(property="end_logradouro", type="string", maxLength=255),
 *     @OA\Property(property="end_numero", type="integer"),
 *     @OA\Property(property="end_bairro", type="string", maxLength=255),
 *     @OA\Property(property="cid_id", type="integer")
 * )
 */
class EnderecoResource extends JsonResource
{
    public function toArray($request)
    {
        return [
            'end_id' => $this->end_id,
            'end_tipo_logradouro' => $this->end_tipo_logradouro,
            'end_logradouro' => $this->end_logradouro,
            'end_numero' => $this->end_numero,
            'end_bairro' => $this->end_bairro,
            'cid_id' => $this->cid_id
        ];
    }
}

