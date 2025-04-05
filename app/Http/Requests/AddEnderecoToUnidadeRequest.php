<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

/**
 * @OA\Schema(
 *     schema="AddEnderecoToUnidadeRequest",
 *     type="object",
 *     required={"end_tipo_logradouro", "end_logradouro", "end_numero", "end_bairro", "cid_id"},
 *     @OA\Property(property="end_tipo_logradouro", type="string", maxLength=50),
 *     @OA\Property(property="end_logradouro", type="string", maxLength=255),
 *     @OA\Property(property="end_numero", type="integer"),
 *     @OA\Property(property="end_bairro", type="string", maxLength=255),
 *     @OA\Property(property="cid_id", type="integer")
 * )
 */
class AddEnderecoToUnidadeRequest extends FormRequest
{
    public function authorize()
    {
        return true;
    }

    public function rules()
    {
        return [
            'end_tipo_logradouro' => 'required|string|max:50',
            'end_logradouro' => 'required|string|max:255',
            'end_numero' => 'required|integer',
            'end_bairro' => 'required|string|max:255',
            'cid_id' => 'required|exists:cidade,cid_id',
        ];
    }
}
