<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class ServidorTemporario extends Pessoa
{
    protected $table = 'servidor_temporario';
    protected $primaryKey = 'pes_id';
    public $incrementing = false;
    public $timestamps = false;

    protected $fillable = [
        'pes_id',
        'st_data_admissao',
        'st_data_demissao'
    ];

    protected $casts = [
        'st_data_admissao' => 'date',
        'st_data_demissao' => 'date',
    ];

    public function pessoa()
    {
        return $this->belongsTo(Pessoa::class, 'pes_id');
    }
}
