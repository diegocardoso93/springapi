<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\JsonResource;

/**
 * @OA\Schema(
 *     schema="UnidadeResource",
 *     type="object",
 *     @OA\Property(property="unid_id", type="integer"),
 *     @OA\Property(property="unid_nome", type="string", maxLength=255),
 *     @OA\Property(property="unid_sigla", type="string", maxLength=50),
 *     @OA\Property(
 *         property="enderecos",
 *         type="array",
 *         @OA\Items(
 *             type="object",
 *             @OA\Property(property="end_id", type="integer")
 *         )
 *     )
 * )
 */
class UnidadeResource extends JsonResource
{
    public function toArray($request)
    {
        return [
            'unid_id' => $this->unid_id,
            'unid_nome' => $this->unid_nome,
            'unid_sigla' => $this->unid_sigla,
            'enderecos' => $this->enderecos->map(function($endereco) {
                return [
                    'end_id' => $endereco->end_id,
                ];
            }),
        ];
    }
}
