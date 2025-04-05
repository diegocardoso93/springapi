<?php

namespace App\Http\Resources;

use App\Models\FotoPessoa;
use Illuminate\Http\Resources\Json\JsonResource;
use Storage;

/**
 * @OA\Schema(
 *     schema="ConsultaServidorLotadoPorUnidadeResource",
 *     type="object",
 *     @OA\Property(property="pes_id", type="integer"),
 *     @OA\Property(property="nome", type="string", maxLength=255),
 *     @OA\Property(property="idade", type="integer"),
 *     @OA\Property(property="unidade", type="string", maxLength=255),
 *     @OA\Property(property="fotografia", type="string", format="uri")
 * )
 */
class ConsultaServidorLotadoPorUnidadeResource extends JsonResource
{
    public function toArray($request)
    {
        $fotos = FotoPessoa::where('pes_id', $this->pes_id)->get();
        $foto = $fotos->isNotEmpty() ? Storage::disk('minio')->temporaryUrl(
            $fotos->get(0)->fp_hash,
            now()->addMinutes(5)
        ) : null;

        return [
            'pes_id' => $this->pes_id,
            'nome' => $this->pessoa->pes_nome,
            'idade' => now()->diffInYears($this->pessoa->pes_data_nascimento),
            'unidade' => $this->lotacoes->first()->unidade->unid_nome,
            'fotografia' => $foto,
        ];
    }
}
