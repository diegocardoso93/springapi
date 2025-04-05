<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class PessoaEndereco extends Model
{
    protected $table = 'pessoa_endereco';
    public $incrementing = false;
    public $timestamps = false;

    protected $fillable = [
        'pes_id',
        'end_id'
    ];
}
