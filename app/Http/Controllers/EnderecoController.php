<?php

namespace App\Http\Controllers;

use App\Models\Endereco;
use App\Http\Resources\EnderecoResource;
use Illuminate\Http\Request;

/**
 * @OA\Schema(
 *     schema="Endereco",
 *     type="object",
 *     @OA\Property(property="end_id", type="integer", readOnly=true),
 *     @OA\Property(property="end_tipo_logradouro", type="string"),
 *     @OA\Property(property="end_logradouro", type="string"),
 *     @OA\Property(property="end_numero", type="integer"),
 *     @OA\Property(property="end_bairro", type="string"),
 *     @OA\Property(property="cid_id", type="integer")
 * )
 * @OA\Tag(
 *     name="Endereços",
 *     description="Operações relacionadas a endereços"
 * )
 */
class EnderecoController extends Controller
{
    /**
     * @OA\Get(
     *      path="/api/enderecos/{id}",
     *      operationId="getEndereco",
     *      tags={"Endereços"},
     *      summary="Busca endereço por ID",
     *      security={{"sanctum": {}}},
     *      @OA\Parameter(
     *          name="id",
     *          in="path",
     *          required=true,
     *          @OA\Schema(type="integer")
     *      ),
     *      @OA\Response(response=200, description="Endereço encontrado"),
     *      @OA\Response(response=404, description="Endereço não encontrado")
     * )
     */
    public function show($id)
    {
        $endereco = Endereco::findOrFail($id);
        return new EnderecoResource($endereco);
    }

    /**
     * @OA\Post(
     *      path="/api/enderecos",
     *      operationId="createEndereco",
     *      tags={"Endereços"},
     *      summary="Cria novo endereço",
     *      security={{"sanctum": {}}},
     *      @OA\RequestBody(
     *          required=true,
     *          @OA\JsonContent(ref="#/components/schemas/Endereco")
     *      ),
     *      @OA\Response(response=201, description="Endereço criado"),
     *      @OA\Response(response=400, description="Dados inválidos")
     * )
     */
    public function store(Request $request)
    {
        $data = $request->validate([
            'end_tipo_logradouro' => 'required|string',
            'end_logradouro' => 'required|string',
            'end_numero' => 'required|integer',
            'end_bairro' => 'required|string',
            'cid_id' => 'required|exists:cidade,cid_id'
        ]);

        $endereco = Endereco::create($data);
        return new EnderecoResource($endereco);
    }

    /**
     * @OA\Put(
     *      path="/api/enderecos/{id}",
     *      operationId="updateEndereco",
     *      tags={"Endereços"},
     *      summary="Atualiza endereço",
     *      security={{"sanctum": {}}},
     *      @OA\Parameter(
     *          name="id",
     *          in="path",
     *          required=true,
     *          @OA\Schema(type="integer")
     *      ),
     *      @OA\RequestBody(
     *          required=true,
     *          @OA\JsonContent(ref="#/components/schemas/Endereco")
     *      ),
     *      @OA\Response(response=200, description="Endereço atualizado"),
     *      @OA\Response(response=400, description="Dados inválidos"),
     *      @OA\Response(response=404, description="Endereço não encontrado")
     * )
     */
    public function update(Request $request, $id)
    {
        $endereco = Endereco::findOrFail($id);
        
        $data = $request->validate([
            'end_tipo_logradouro' => 'required|string',
            'end_logradouro' => 'required|string',
            'end_numero' => 'required|integer',
            'end_bairro' => 'required|string',
            'cid_id' => 'required|exists:cidade,cid_id'
        ]);

        $endereco->update($data);
        return new EnderecoResource($endereco);
    }

    /**
     * @OA\Delete(
     *      path="/api/enderecos/{id}",
     *      operationId="deleteEndereco",
     *      tags={"Endereços"},
     *      summary="Exclui endereço",
     *      security={{"sanctum": {}}},
     *      @OA\Parameter(
     *          name="id",
     *          in="path",
     *          required=true,
     *          @OA\Schema(type="integer")
     *      ),
     *      @OA\Response(response=204, description="Endereço excluído"),
     *      @OA\Response(response=404, description="Endereço não encontrado")
     * )
     */
    public function destroy($id)
    {
        $endereco = Endereco::findOrFail($id);
        $endereco->delete();
        return response()->noContent();
    }
}
