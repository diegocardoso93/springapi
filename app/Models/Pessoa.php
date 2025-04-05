<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsToMany;
use Illuminate\Database\Eloquent\Relations\HasMany;

class Pessoa extends Model
{
    protected $table = 'pessoa';
    protected $primaryKey = 'pes_id';
    public $timestamps = false;

    protected $fillable = [
        'pes_nome',
        'pes_data_nascimento',
        'pes_sexo',
        'pes_mae',
        'pes_pai'
    ];

    protected $casts = [
        'pes_data_nascimento' => 'date',
    ];

    public function enderecos(): BelongsToMany
    {
        return $this->belongsToMany(Endereco::class, 'pessoa_endereco', 'pes_id', 'end_id');
    }

    public function lotacoes(): HasMany
    {
        return $this->hasMany(Lotacao::class, 'pes_id');
    }
}
