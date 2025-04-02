<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

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
