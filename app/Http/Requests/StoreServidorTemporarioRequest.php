<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

/**
 * @OA\Schema(
 *     schema="StoreServidorTemporarioRequest",
 *     type="object",
 *     required={"pes_nome", "pes_data_nascimento", "pes_sexo", "st_data_admissao"},
 *     @OA\Property(property="pes_nome", type="string", maxLength=255),
 *     @OA\Property(property="pes_data_nascimento", type="string", format="date"),
 *     @OA\Property(property="pes_sexo", type="string", maxLength=1),
 *     @OA\Property(property="pes_mae", type="string", maxLength=255, nullable=true),
 *     @OA\Property(property="pes_pai", type="string", maxLength=255, nullable=true),
 *     @OA\Property(property="st_data_admissao", type="string", format="date"),
 *     @OA\Property(property="st_data_demissao", type="string", format="date", nullable=true)
 * )
 */
class StoreServidorTemporarioRequest extends FormRequest
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
            'st_data_admissao' => 'required|date',
            'st_data_demissao' => 'nullable|date|after_or_equal:st_data_admissao',
        ];
    }
}
