<?php

namespace App\Http\Controllers;

use App\Models\Cidade;
use App\Http\Resources\CidadeResource;
use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\ResourceCollection;

/**
 * @OA\Schema(
 *     schema="Cidade",
 *     type="object",
 *     @OA\Property(property="cid_id", type="integer", readOnly=true),
 *     @OA\Property(property="cid_nome", type="string"),
 *     @OA\Property(property="cid_uf", type="string", maxLength=2)
 * )
 * @OA\Tag(
 *     name="Cidades",
 *     description="Gerenciamento de cidades"
 * )
 */
class CidadeController extends Controller
{
    /**
     * @OA\Post(
     *      path="/api/cidades",
     *      operationId="createCidade",
     *      tags={"Cidades"},
     *      summary="Cria uma nova cidade",
     *      @OA\RequestBody(
     *          required=true,
     *          @OA\JsonContent(ref="#/components/schemas/Cidade")
     *      ),
     *      @OA\Response(
     *          response=201,
     *          description="Cidade criada com sucesso",
     *          @OA\JsonContent(ref="#/components/schemas/Cidade")
     *      ),
     *      @OA\Response(response=400, description="Dados inválidos")
     * )
     */
    public function create(Request $request)
    {
        $data = $request->validate([
            'cid_nome' => 'required|string',
            'cid_uf' => 'required|string|size:2'
        ]);

        $cidade = Cidade::create($data);
        return new CidadeResource($cidade, 201);
    }

    /**
     * @OA\Get(
     *      path="/api/cidades/{id}",
     *      operationId="getCidade",
     *      tags={"Cidades"},
     *      summary="Busca cidade por ID",
     *      @OA\Parameter(
     *          name="id",
     *          in="path",
     *          required=true,
     *          @OA\Schema(type="integer")
     *      ),
     *      @OA\Response(response=200, description="Cidade encontrada"),
     *      @OA\Response(response=404, description="Cidade não encontrada")
     * )
     */
    public function show($id)
    {
        $cidade = Cidade::findOrFail($id);
        return new CidadeResource($cidade);
    }

    /**
     * @OA\Get(
     *      path="/api/cidades",
     *      operationId="listCidades",
     *      tags={"Cidades"},
     *      summary="Lista todas as cidades",
     *      @OA\Response(response=200, description="Lista de cidades")
     * )
     */
    public function index(Request $request)
    {
        $perPage = $request->input('size', 10);
        $cidades = Cidade::paginate($perPage);
        return CidadeResource::collection($cidades);
    }

    /**
     * @OA\Put(
     *      path="/api/cidades/{id}",
     *      operationId="updateCidade",
     *      tags={"Cidades"},
     *      summary="Atualiza uma cidade",
     *      @OA\Parameter(
     *          name="id",
     *          in="path",
     *          required=true,
     *          @OA\Schema(type="integer")
     *      ),
     *      @OA\RequestBody(
     *          required=true,
     *          @OA\JsonContent(ref="#/components/schemas/Cidade")
     *      ),
     *      @OA\Response(response=200, description="Cidade atualizada"),
     *      @OA\Response(response=404, description="Cidade não encontrada"),
     *      @OA\Response(response=400, description="Dados inválidos")
     * )
     */
    public function update(Request $request, $id)
    {
        $cidade = Cidade::findOrFail($id);
        
        $data = $request->validate([
            'cid_nome' => 'required|string',
            'cid_uf' => 'required|string|size:2'
        ]);

        $cidade->update($data);
        return new CidadeResource($cidade);
    }

    /**
     * @OA\Delete(
     *      path="/api/cidades/{id}",
     *      operationId="deleteCidade",
     *      tags={"Cidades"},
     *      summary="Exclui uma cidade",
     *      @OA\Parameter(
     *          name="id",
     *          in="path",
     *          required=true,
     *          @OA\Schema(type="integer")
     *      ),
     *      @OA\Response(response=204, description="Cidade excluída"),
     *      @OA\Response(response=404, description="Cidade não encontrada")
     * )
     */
    public function destroy($id)
    {
        $cidade = Cidade::findOrFail($id);
        $cidade->delete();
        return response()->noContent();
    }
}
