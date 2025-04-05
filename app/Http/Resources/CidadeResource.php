<?php

namespace App\Http\Resources;

use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

/**
 * @OA\Schema(
 *     schema="CidadeResource",
 *     type="object",
 *     @OA\Property(property="cidId", type="integer"),
 *     @OA\Property(property="cidNome", type="string", maxLength=255),
 *     @OA\Property(property="cidUf", type="string", maxLength=2)
 * )
 */
class CidadeResource extends JsonResource
{
    public function toArray(Request $request): array
    {
        return [
            'cidId' => $this->cidId,
            'cidNome' => $this->cidNome,
            'cidUf' => $this->cidUf,
        ];
    }
}
