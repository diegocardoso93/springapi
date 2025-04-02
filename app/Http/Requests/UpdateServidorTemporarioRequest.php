<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

class UpdateServidorTemporarioRequest extends FormRequest
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
            'st_data_admissao' => 'sometimes|date',
            'st_data_demissao' => 'nullable|date|after_or_equal:st_data_admissao',
        ];
    }
}