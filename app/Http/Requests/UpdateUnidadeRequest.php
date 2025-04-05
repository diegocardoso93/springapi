<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

/**
 * @OA\Schema(
 *     schema="UpdateUnidadeRequest",
 *     type="object",
 *     required={},
 *     @OA\Property(property="unid_nome", type="string", maxLength=255, nullable=true),
 *     @OA\Property(property="unid_sigla", type="string", maxLength=50, nullable=true),
 *     @OA\Property(
 *         property="endereco_ids", 
 *         type="array", 
 *         @OA\Items(type="integer"),
 *         nullable=true
 *     )
 * )
 */
class UpdateUnidadeRequest extends FormRequest
{
    public function authorize()
    {
        return true;
    }

    public function rules()
    {
        return [
            'unid_nome' => 'sometimes|string|max:255',
            'unid_sigla' => 'sometimes|string|max:50',
            'endereco_ids' => 'nullable|array',
            'endereco_ids.*' => 'exists:endereco,end_id',
        ];
    }
}
