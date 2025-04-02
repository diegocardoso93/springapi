<?php

namespace App\Http\Controllers;

use App\Models\ServidorTemporario;
use App\Http\Resources\ServidorTemporarioResource;
use App\Http\Requests\StoreServidorTemporarioRequest;
use App\Http\Requests\UpdateServidorTemporarioRequest;
use Illuminate\Http\Request;
use Illuminate\Http\Response;

/**
 * @OA\Tag(
 *     name="Servidores Temporários",
 *     description="Operações relacionadas a servidores temporários"
 * )
 */
class ServidorTemporarioController extends Controller
{
    /**
     * @OA\Get(
     *     path="/api/servidores-temporarios",
     *     tags={"Servidores Temporários"},
     *     summary="Lista todos os servidores temporários com paginação",
     *     description="Retorna uma lista paginada de todos os servidores temporários.",
     *     @OA\Response(
     *         response=200,
     *         description="Lista de servidores temporários retornada com sucesso",
     *         @OA\JsonContent(
     *             type="array",
     *             @OA\Items(ref="#/components/schemas/ServidorTemporarioResource")
     *         )
     *     ),
     *     @OA\Response(
     *         response=204,
     *         description="Nenhum servidor temporário encontrado"
     *     )
     * )
     */
    public function index(Request $request)
    {
        $perPage = $request->input('per_page', 10);
        $servidores = ServidorTemporario::with('pessoa')->paginate($perPage);
        
        if ($servidores->isEmpty()) {
            return response()->noContent();
        }
        
        return ServidorTemporarioResource::collection($servidores);
    }

    /**
     * @OA\Get(
     *     path="/api/servidores-temporarios/{id}",
     *     tags={"Servidores Temporários"},
     *     summary="Busca um servidor temporário por ID",
     *     description="Retorna os detalhes de um servidor temporário específico com base no seu ID.",
     *     @OA\Parameter(
     *         name="id",
     *         in="path",
     *         description="ID do servidor temporário a ser buscado",
     *         required=true,
     *         @OA\Schema(type="integer")
     *     ),
     *     @OA\Response(
     *         response=200,
     *         description="Servidor temporário encontrado",
     *         @OA\JsonContent(ref="#/components/schemas/ServidorTemporarioResource")
     *     ),
     *     @OA\Response(
     *         response=404,
     *         description="Servidor temporário não encontrado"
     *     )
     * )
     */
    public function show($id)
    {
        $servidor = ServidorTemporario::with('pessoa')->findOrFail($id);
        return new ServidorTemporarioResource($servidor);
    }

    /**
     * @OA\Post(
     *     path="/api/servidores-temporarios",
     *     tags={"Servidores Temporários"},
     *     summary="Cria um novo servidor temporário",
     *     description="Cria um novo registro de servidor temporário.",
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(ref="#/components/schemas/StoreServidorTemporarioRequest")
     *     ),
     *     @OA\Response(
     *         response=201,
     *         description="Servidor temporário criado com sucesso",
     *         @OA\JsonContent(ref="#/components/schemas/ServidorTemporarioResource")
     *     ),
     *     @OA\Response(
     *         response=400,
     *         description="Dados de entrada inválidos"
     *     )
     * )
     */
    public function store(StoreServidorTemporarioRequest $request)
    {
        // First create the Pessoa record
        $pessoa = Pessoa::create($request->only([
            'pes_nome',
            'pes_data_nascimento',
            'pes_sexo',
            'pes_mae',
            'pes_pai'
        ]));
        
        // Then create the ServidorTemporario record
        $servidor = ServidorTemporario::create(array_merge(
            ['pes_id' => $pessoa->pes_id],
            $request->only(['st_data_admissao', 'st_data_demissao'])
        ));
        
        return (new ServidorTemporarioResource($servidor->load('pessoa')))
            ->response()
            ->setStatusCode(Response::HTTP_CREATED);
    }

    /**
     * @OA\Put(
     *     path="/api/servidores-temporarios/{id}",
     *     tags={"Servidores Temporários"},
     *     summary="Atualiza um servidor temporário existente",
     *     description="Atualiza os dados de um servidor temporário específico com base no seu ID.",
     *     @OA\Parameter(
     *         name="id",
     *         in="path",
     *         description="ID do servidor temporário a ser atualizado",
     *         required=true,
     *         @OA\Schema(type="integer")
     *     ),
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(ref="#/components/schemas/UpdateServidorTemporarioRequest")
     *     ),
     *     @OA\Response(
     *         response=200,
     *         description="Servidor temporário atualizado com sucesso",
     *         @OA\JsonContent(ref="#/components/schemas/ServidorTemporarioResource")
     *     ),
     *     @OA\Response(
     *         response=400,
     *         description="Dados de entrada inválidos"
     *     ),
     *     @OA\Response(
     *         response=404,
     *         description="Servidor temporário não encontrado"
     *     )
     * )
     */
    public function update(UpdateServidorTemporarioRequest $request, $id)
    {
        $servidor = ServidorTemporario::findOrFail($id);
        
        // Update Pessoa data
        $servidor->pessoa->update($request->only([
            'pes_nome',
            'pes_data_nascimento',
            'pes_sexo',
            'pes_mae',
            'pes_pai'
        ]));
        
        // Update ServidorTemporario data
        $servidor->update($request->only([
            'st_data_admissao',
            'st_data_demissao'
        ]));
        
        return new ServidorTemporarioResource($servidor->load('pessoa'));
    }

    /**
     * @OA\Delete(
     *     path="/api/servidores-temporarios/{id}",
     *     tags={"Servidores Temporários"},
     *     summary="Exclui um servidor temporário por ID",
     *     description="Remove um servidor temporário específico com base no seu ID.",
     *     @OA\Parameter(
     *         name="id",
     *         in="path",
     *         description="ID do servidor temporário a ser excluído",
     *         required=true,
     *         @OA\Schema(type="integer")
     *     ),
     *     @OA\Response(
     *         response=204,
     *         description="Servidor temporário excluído com sucesso"
     *     ),
     *     @OA\Response(
     *         response=404,
     *         description="Servidor temporário não encontrado"
     *     )
     * )
     */
    public function destroy($id)
    {
        $servidor = ServidorTemporario::findOrFail($id);
        
        // First delete the ServidorTemporario record
        $servidor->delete();
        
        // Then delete the associated Pessoa record
        $servidor->pessoa->delete();
        
        return response()->noContent();
    }
}
