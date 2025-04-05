<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\JsonResource;

/**
 * @OA\Schema(
 *     schema="ServidorEfetivoResource",
 *     type="object",
 *     @OA\Property(property="pes_id", type="integer"),
 *     @OA\Property(property="pes_nome", type="string"),
 *     @OA\Property(property="pes_data_nascimento", type="string", format="date"),
 *     @OA\Property(property="pes_sexo", type="string", maxLength=1),
 *     @OA\Property(property="pes_mae", type="string", nullable=true),
 *     @OA\Property(property="pes_pai", type="string", nullable=true),
 *     @OA\Property(property="se_matricula", type="string"),
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
