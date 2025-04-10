<?php

namespace App\Http\Resources;

use App\Models\Cidade;
use App\Models\Endereco;
use App\Models\Unidade;
use Illuminate\Http\Resources\Json\JsonResource;

/**
 * @OA\Schema(
 *     schema="ConsultaEnderecoFuncionalServidorEfetivoPorNomeResource",
 *     type="object",
 *     @OA\Property(property="pes_id", type="integer"),
 *     @OA\Property(property="pes_nome", type="string", maxLength=255),
 *     @OA\Property(property="unid_id", type="integer"),
 *     @OA\Property(property="unid_nome", type="string", maxLength=255),
 *     @OA\Property(property="unid_sigla", type="string", maxLength=50),
 *     @OA\Property(property="end_id", type="integer"),
 *     @OA\Property(property="end_tipo_logradouro", type="string", maxLength=50),
 *     @OA\Property(property="end_logradouro", type="string", maxLength=255),
 *     @OA\Property(property="end_numero", type="integer"),
 *     @OA\Property(property="end_bairro", type="string", maxLength=255),
 *     @OA\Property(property="cid_id", type="integer"),
 *     @OA\Property(property="cid_nome", type="string", maxLength=255),
 *     @OA\Property(property="cid_uf", type="string", maxLength=2)
 * )
 */
class ConsultaEnderecoFuncionalServidorEfetivoPorNomeResource extends JsonResource
{
    public function toArray($request)
    {
        $lotacao = $this->lotacoes->first();
        $unidade = $lotacao->unidade ?? new Unidade();
        $endereco = ($unidade->endereco ?? new Endereco())->first();
        $cidade = $endereco->cidade ?? new Cidade();
        
        return [
            'pes_id' => $this->pes_id,
            'pes_nome' => $this->pessoa->pes_nome,
            'unid_id' => $unidade->unid_id,
            'unid_nome' => $unidade->unid_nome,
            'unid_sigla' => $unidade->unid_sigla,
            'end_id' => $endereco->end_id,
            'end_tipo_logradouro' => $endereco->end_tipo_logradouro,
            'end_logradouro' => $endereco->end_logradouro,
            'end_numero' => $endereco->end_numero,
            'end_bairro' => $endereco->end_bairro,
            'cid_id' => $cidade->cid_id,
            'cid_nome' => $cidade->cid_nome,
            'cid_uf' => $cidade->cid_uf,
        ];
    }
}
