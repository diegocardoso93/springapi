<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

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
