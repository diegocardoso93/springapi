<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

/**
 * @OA\Schema(
 *     schema="UpdateServidorEfetivoRequest",
 *     type="object",
 *     @OA\Property(property="pes_nome", type="string", maxLength=255, nullable=true),
 *     @OA\Property(property="pes_data_nascimento", type="string", format="date", nullable=true),
 *     @OA\Property(property="pes_sexo", type="string", maxLength=1, nullable=true),
 *     @OA\Property(property="pes_mae", type="string", maxLength=255, nullable=true),
 *     @OA\Property(property="pes_pai", type="string", maxLength=255, nullable=true),
 *     @OA\Property(property="se_matricula", type="string", maxLength=50, nullable=true),
 *     @OA\Property(
 *         property="endereco_ids",
 *         type="array",
 *         nullable=true,
 *         @OA\Items(type="integer")
 *     )
 * )
 */
class UpdateServidorEfetivoRequest extends FormRequest
{
    public function authorize()
    {
        return true;
    }

    public function rules()
    {
        return [
            'pes_nome' => 'sometimes|string|max:255',
            'pes_data_nascimento' => 'sometimes|date',
            'pes_sexo' => 'sometimes|string|max:1',
            'pes_mae' => 'nullable|string|max:255',
            'pes_pai' => 'nullable|string|max:255',
            'se_matricula' => 'sometimes|string|max:50',
            'endereco_ids' => 'nullable|array',
            'endereco_ids.*' => 'exists:endereco,end_id',
        ];
    }
}
