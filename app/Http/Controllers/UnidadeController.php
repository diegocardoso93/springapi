<?php

namespace App\Http\Controllers;

use App\Models\Unidade;
use App\Http\Resources\UnidadeResource;
use App\Http\Requests\StoreUnidadeRequest;
use App\Http\Requests\UpdateUnidadeRequest;
use App\Http\Requests\AddEnderecoToUnidadeRequest;
use Illuminate\Http\Request;
use Illuminate\Http\Response;

/**
 * @OA\Tag(
 *     name="Unidades",
 *     description="Operações relacionadas a unidades"
 * )
 */
class UnidadeController extends Controller
{
    /**
     * @OA\Get(
     *     path="/api/unidades",
     *     tags={"Unidades"},
     *     summary="Lista todas as unidades com paginação",
     *     description="Retorna uma lista paginada de todas as unidades.",
     *     @OA\Response(
     *         response=200,
     *         description="Lista de unidades retornada com sucesso",
     *         @OA\JsonContent(
     *             type="array",
     *             @OA\Items(ref="#/components/schemas/UnidadeResource")
     *         )
     *     ),
     *     @OA\Response(
     *         response=204,
     *         description="Nenhuma unidade encontrada"
     *     )
     * )
     */
    public function index(Request $request)
    {
        $perPage = $request->input('per_page', 10);
        $unidades = Unidade::with('enderecos')->paginate($perPage);
        
        if ($unidades->isEmpty()) {
            return response()->noContent();
        }
        
        return UnidadeResource::collection($unidades);
    }

    /**
     * @OA\Get(
     *     path="/api/unidades/{id}",
     *     tags={"Unidades"},
     *     summary="Busca uma unidade por ID",
     *     description="Retorna os detalhes de uma unidade específica com base no seu ID.",
     *     @OA\Parameter(
     *         name="id",
     *         in="path",
     *         description="ID da unidade a ser buscada",
     *         required=true,
     *         @OA\Schema(type="integer")
     *     ),
     *     @OA\Response(
     *         response=200,
     *         description="Unidade encontrada",
     *         @OA\JsonContent(ref="#/components/schemas/UnidadeResource")
     *     ),
     *     @OA\Response(
     *         response=404,
     *         description="Unidade não encontrada"
     *     )
     * )
     */
    public function show($id)
    {
        $unidade = Unidade::with('enderecos')->findOrFail($id);
        return new UnidadeResource($unidade);
    }

    /**
     * @OA\Post(
     *     path="/api/unidades",
     *     tags={"Unidades"},
     *     summary="Cria uma nova unidade",
     *     description="Cria um novo registro de unidade.",
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(ref="#/components/schemas/StoreUnidadeRequest")
     *     ),
     *     @OA\Response(
     *         response=201,
     *         description="Unidade criada com sucesso",
     *         @OA\JsonContent(ref="#/components/schemas/UnidadeResource")
     *     ),
     *     @OA\Response(
     *         response=400,
     *         description="Dados de entrada inválidos"
     *     )
     * )
     */
    public function store(StoreUnidadeRequest $request)
    {
        $unidade = Unidade::create($request->only(['unid_nome', 'unid_sigla']));
        
        if ($request->has('endereco_ids')) {
            $unidade->enderecos()->sync($request->endereco_ids);
        }
        
        return (new UnidadeResource($unidade->load('enderecos')))
            ->response()
            ->setStatusCode(Response::HTTP_CREATED);
    }

    /**
     * @OA\Put(
     *     path="/api/unidades/{id}",
     *     tags={"Unidades"},
     *     summary="Atualiza uma unidade existente",
     *     description="Atualiza os dados de uma unidade específica com base no seu ID.",
     *     @OA\Parameter(
     *         name="id",
     *         in="path",
     *         description="ID da unidade a ser atualizada",
     *         required=true,
     *         @OA\Schema(type="integer")
     *     ),
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(ref="#/components/schemas/UpdateUnidadeRequest")
     *     ),
     *     @OA\Response(
     *         response=200,
     *         description="Unidade atualizada com sucesso",
     *         @OA\JsonContent(ref="#/components/schemas/UnidadeResource")
     *     ),
     *     @OA\Response(
     *         response=400,
     *         description="Dados de entrada inválidos"
     *     ),
     *     @OA\Response(
     *         response=404,
     *         description="Unidade não encontrada"
     *     )
     * )
     */
    public function update(UpdateUnidadeRequest $request, $id)
    {
        $unidade = Unidade::findOrFail($id);
        $unidade->update($request->only(['unid_nome', 'unid_sigla']));
        
        if ($request->has('endereco_ids')) {
            $unidade->enderecos()->sync($request->endereco_ids);
        }
        
        return new UnidadeResource($unidade->load('enderecos'));
    }

    /**
     * @OA\Delete(
     *     path="/api/unidades/{id}",
     *     tags={"Unidades"},
     *     summary="Exclui uma unidade por ID",
     *     description="Remove uma unidade específica com base no seu ID.",
     *     @OA\Parameter(
     *         name="id",
     *         in="path",
     *         description="ID da unidade a ser excluída",
     *         required=true,
     *         @OA\Schema(type="integer")
     *     ),
     *     @OA\Response(
     *         response=204,
     *         description="Unidade excluída com sucesso"
     *     ),
     *     @OA\Response(
     *         response=404,
     *         description="Unidade não encontrada"
     *     )
     * )
     */
    public function destroy($id)
    {
        $unidade = Unidade::findOrFail($id);
        $unidade->delete();
        
        return response()->noContent();
    }

    /**
     * @OA\Post(
     *     path="/api/unidades/{id}/enderecos",
     *     tags={"Unidades", "Endereços"},
     *     summary="Adiciona um endereço a uma unidade existente",
     *     description="Adiciona um novo endereço à unidade especificada.",
     *     @OA\Parameter(
     *         name="id",
     *         in="path",
     *         description="ID da unidade para adicionar o endereço",
     *         required=true,
     *         @OA\Schema(type="integer")
     *     ),
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(ref="#/components/schemas/AddEnderecoToUnidadeRequest")
     *     ),
     *     @OA\Response(
     *         response=200,
     *         description="Endereço adicionado com sucesso",
     *         @OA\JsonContent(ref="#/components/schemas/UnidadeResource")
     *     ),
     *     @OA\Response(
     *         response=404,
     *         description="Unidade não encontrada"
     *     ),
     *     @OA\Response(
     *         response=400,
     *         description="Requisição inválida"
     *     )
     * )
     */
    public function addEndereco(AddEnderecoToUnidadeRequest $request, $id)
    {
        $unidade = Unidade::findOrFail($id);
        $endereco = Endereco::create($request->validated());
        
        $unidade->enderecos()->attach($endereco->end_id);
        
        return new UnidadeResource($unidade->load('enderecos'));
    }
}
