<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsToMany;

class Unidade extends Model
{
    protected $table = 'unidade';
    protected $primaryKey = 'unid_id';
    public $timestamps = false;

    protected $fillable = [
        'unid_nome',
        'unid_sigla'
    ];

    public function enderecos(): BelongsToMany
    {
        return $this->belongsToMany(Endereco::class, 'unidade_endereco', 'unid_id', 'end_id');
    }
}
