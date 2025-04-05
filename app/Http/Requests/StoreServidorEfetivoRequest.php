<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

/**
 * @OA\Schema(
 *     schema="StoreServidorEfetivoRequest",
 *     type="object",
 *     required={"pes_nome", "pes_data_nascimento", "pes_sexo", "se_matricula"},
 *     @OA\Property(property="pes_nome", type="string", maxLength=255),
 *     @OA\Property(property="pes_data_nascimento", type="string", format="date"),
 *     @OA\Property(property="pes_sexo", type="string", maxLength=1),
 *     @OA\Property(property="pes_mae", type="string", maxLength=255, nullable=true),
 *     @OA\Property(property="pes_pai", type="string", maxLength=255, nullable=true),
 *     @OA\Property(property="se_matricula", type="string", maxLength=50),
 *     @OA\Property(property="endereco_ids", type="array", @OA\Items(type="integer"), nullable=true)
 * )
 */
class StoreServidorEfetivoRequest extends FormRequest
{
    public function authorize()
    {
        return true;
    }

    public function rules()
    {
        return [
            'pes_nome' => 'required|string|max:255',
            'pes_data_nascimento' => 'required|date',
            'pes_sexo' => 'required|string|max:1',
            'pes_mae' => 'nullable|string|max:255',
            'pes_pai' => 'nullable|string|max:255',
            'se_matricula' => 'required|string|max:50',
            'endereco_ids' => 'nullable|array',
            'endereco_ids.*' => 'exists:endereco,end_id',
        ];
    }
}
