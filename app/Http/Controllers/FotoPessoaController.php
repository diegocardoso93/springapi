<?php

namespace App\Http\Controllers;

use App\Models\FotoPessoa;
use App\Models\Pessoa;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Storage;
use Illuminate\Support\Str;

/**
 * @OA\Tag(
 *     name="Fotos",
 *     description="Gerenciamento de fotos"
 * )
 */
class FotoPessoaController extends Controller
{
    /**
     * @OA\Post(
     *      path="/api/fotos/upload",
     *      operationId="uploadFotos",
     *      tags={"Fotos"},
     *      summary="Upload de múltiplas fotos",
     *      @OA\RequestBody(
     *          required=true,
     *          @OA\MediaType(
     *              mediaType="multipart/form-data",
     *              @OA\Schema(
     *                  @OA\Property(
     *                      property="files",
     *                      type="array",
     *                      @OA\Items(type="file")
     *                  ),
     *                  @OA\Property(
     *                      property="pes_id",
     *                      type="integer"
     *                  )
     *              )
     *          )
     *      ),
     *      @OA\Response(response=201, description="Upload realizado"),
     *      @OA\Response(response=400, description="Requisição inválida"),
     *      @OA\Response(response=404, description="Pessoa não encontrada"),
     *      @OA\Response(response=500, description="Erro interno")
     * )
     */
    public function upload(Request $request)
    {
        $request->validate([
            'files.*' => 'required|image|max:10240',
            'pes_id' => 'required|exists:pessoa,pes_id'
        ]);

        $pessoa = Pessoa::findOrFail($request->pes_id);
        $uploadedFiles = [];

        foreach ($request->file('files') as $file) {
            $fileName = Str::uuid() . '-' . $file->getClientOriginalName();
            $path = $file->storeAs('', $fileName, 'minio');

            $foto = FotoPessoa::create([
                'fp_data' => now()->toDateString(),
                'fp_bucket' => config('filesystems.disks.minio.bucket'),
                'fp_hash' => $fileName,
                'pes_id' => $pessoa->pes_id
            ]);

            $uploadedFiles[] = $foto->fp_hash;
        }

        return response()->json($uploadedFiles, 201);
    }

    /**
     * @OA\Get(
     *      path="/api/fotos/links/{pesId}",
     *      operationId="getFotoLinks",
     *      tags={"Fotos"},
     *      summary="Lista links das fotos",
     *      @OA\Parameter(
     *          name="pesId",
     *          in="path",
     *          required=true,
     *          @OA\Schema(type="integer")
     *      ),
     *      @OA\Response(response=200, description="Links encontrados"),
     *      @OA\Response(response=404, description="Nenhuma foto encontrada"),
     *      @OA\Response(response=500, description="Erro interno")
     * )
     */
    public function getLinks($pesId)
    {
        $fotos = FotoPessoa::where('pes_id', $pesId)->get();

        if ($fotos->isEmpty()) {
            return response()->json(['message' => 'Nenhuma foto encontrada'], 404);
        }

        $links = $fotos->map(function ($foto) {
            return [
                'url' => Storage::disk('minio')->temporaryUrl(
                    $foto->fp_hash,
                    now()->addMinutes(5)
                )
            ];
        });

        return response()->json($links);
    }
}
