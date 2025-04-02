<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

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
