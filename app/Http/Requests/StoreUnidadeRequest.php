<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

/**
 * @OA\Schema(
 *     schema="StoreUnidadeRequest",
 *     type="object",
 *     required={"unid_nome", "unid_sigla"},
 *     @OA\Property(property="unid_nome", type="string", maxLength=255),
 *     @OA\Property(property="unid_sigla", type="string", maxLength=50),
 *     @OA\Property(
 *         property="endereco_ids", 
 *         type="array", 
 *         @OA\Items(type="integer"),
 *         nullable=true
 *     )
 * )
 */
class StoreUnidadeRequest extends FormRequest
{
    public function authorize()
    {
        return true;
    }

    public function rules()
    {
        return [
            'unid_nome' => 'required|string|max:255',
            'unid_sigla' => 'required|string|max:50',
            'endereco_ids' => 'nullable|array',
            'endereco_ids.*' => 'exists:endereco,end_id',
        ];
    }
}
