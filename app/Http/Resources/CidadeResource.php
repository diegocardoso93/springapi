<?php

namespace App\Http\Resources;

use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

/**
 * @OA\Schema(
 *     schema="CidadeResource",
 *     type="object",
 *     @OA\Property(property="cid_id", type="integer"),
 *     @OA\Property(property="cid_nome", type="string", maxLength=255),
 *     @OA\Property(property="cid_uf", type="string", maxLength=2)
 * )
 */
class CidadeResource extends JsonResource
{
    public function toArray(Request $request): array
    {
        return [
            'cid_id' => $this->cid_id,
            'cid_nome' => $this->cid_nome,
            'cid_uf' => $this->cid_uf,
        ];
    }
}
