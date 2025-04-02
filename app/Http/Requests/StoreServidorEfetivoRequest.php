<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

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
