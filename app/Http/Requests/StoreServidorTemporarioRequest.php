<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

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
