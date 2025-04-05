<?php

namespace App\Http\Controllers;

use App\Models\Endereco;
use App\Models\Pessoa;
use App\Models\ServidorEfetivo;
use App\Http\Resources\ServidorEfetivoResource;
use App\Http\Resources\ConsultaServidorLotadoPorUnidadeResource;
use App\Http\Resources\ConsultaEnderecoFuncionalServidorEfetivoPorNomeResource;
use App\Http\Requests\StoreServidorEfetivoRequest;
use App\Http\Requests\UpdateServidorEfetivoRequest;
use App\Http\Requests\AddEnderecoToServidorEfetivoRequest;
use DB;
use Illuminate\Http\Request;
use Illuminate\Http\Response;

class ServidorEfetivoController extends Controller
{
    /**
     * @OA\Get(
     *     path="/api/servidores-efetivos",
     *     tags={"Servidores Efetivos"},
     *     summary="Lista todos os servidores efetivos",
     *     description="Retorna uma lista paginada de todos os servidores efetivos.",
     *     security={{"sanctum": {}}},
     *     @OA\Response(
     *         response=200,
     *         description="Lista de servidores efetivos retornada com sucesso",
     *         @OA\JsonContent(
     *             type="array",
     *             @OA\Items(ref="#/components/schemas/ServidorEfetivoResource")
     *         )
     *     ),
     *     @OA\Response(
     *         response=204,
     *         description="Nenhum servidor efetivo encontrado"
     *     )
     * )
     */
    public function index(Request $request)
    {
        $perPage = $request->input('per_page', 10);
        $servidores = ServidorEfetivo::with(['pessoa', 'pessoa.enderecos'])->paginate($perPage);
        
        if ($servidores->isEmpty()) {
            return response()->noContent();
        }
        
        return ServidorEfetivoResource::collection($servidores);
    }

    /**
     * @OA\Get(
     *     path="/api/servidores-efetivos/{id}",
     *     tags={"Servidores Efetivos"},
     *     summary="Busca um servidor efetivo por ID",
     *     description="Retorna os detalhes de um servidor efetivo específico com base no seu ID.",
     *     security={{"sanctum": {}}},
     *     @OA\Parameter(
     *         name="id",
     *         in="path",
     *         description="ID do servidor efetivo a ser buscado",
     *         required=true,
     *         @OA\Schema(type="integer")
     *     ),
     *     @OA\Response(
     *         response=200,
     *         description="Servidor efetivo encontrado",
     *         @OA\JsonContent(ref="#/components/schemas/ServidorEfetivoResource")
     *     ),
     *     @OA\Response(
     *         response=404,
     *         description="Servidor efetivo não encontrado"
     *     )
     * )
     */
    public function show($id)
    {
        $servidor = ServidorEfetivo::with(['pessoa', 'pessoa.enderecos'])->findOrFail($id);
        return new ServidorEfetivoResource($servidor);
    }

    /**
     * @OA\Post(
     *     path="/api/servidores-efetivos",
     *     tags={"Servidores Efetivos"},
     *     summary="Cria um novo servidor efetivo",
     *     description="Cria um novo registro de servidor efetivo.",
     *     security={{"sanctum": {}}},
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(ref="#/components/schemas/StoreServidorEfetivoRequest")
     *     ),
     *     @OA\Response(
     *         response=201,
     *         description="Servidor efetivo criado com sucesso",
     *         @OA\JsonContent(ref="#/components/schemas/ServidorEfetivoResource")
     *     ),
     *     @OA\Response(
     *         response=400,
     *         description="Dados de entrada inválidos"
     *     )
     * )
     */
    public function store(StoreServidorEfetivoRequest $request)
    {
        $validated = $request->validated();

        $response = DB::transaction(function () use ($validated) {
            $pessoa = Pessoa::create([
                'pes_nome'             => $validated['pes_nome'],
                'pes_data_nascimento'  => $validated['pes_data_nascimento'],
                'pes_sexo'             => $validated['pes_sexo'],
                'pes_mae'              => $validated['pes_mae'],
                'pes_pai'              => $validated['pes_pai'],
            ]);
    
            $servidor = ServidorEfetivo::create([
                'pes_id'       => $pessoa->pes_id,
                'se_matricula' => $validated['se_matricula'],
            ]);
    
            if (!empty($validated['endereco_ids'])) {
                $pessoa->enderecos()->sync($validated['endereco_ids']);
            }
    
            return response()->json($servidor->load('pessoa.enderecos'), 201);
        });

        return $response;
    }

    /**
     * @OA\Put(
     *     path="/api/servidores-efetivos/{id}",
     *     tags={"Servidores Efetivos"},
     *     summary="Atualiza um servidor efetivo existente",
     *     description="Atualiza os dados de um servidor efetivo específico com base no seu ID.",
     *     security={{"sanctum": {}}},
     *     @OA\Parameter(
     *         name="id",
     *         in="path",
     *         description="ID do servidor efetivo a ser atualizado",
     *         required=true,
     *         @OA\Schema(type="integer")
     *     ),
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(ref="#/components/schemas/UpdateServidorEfetivoRequest")
     *     ),
     *     @OA\Response(
     *         response=200,
     *         description="Servidor efetivo atualizado com sucesso",
     *         @OA\JsonContent(ref="#/components/schemas/ServidorEfetivoResource")
     *     ),
     *     @OA\Response(
     *         response=400,
     *         description="Dados de entrada inválidos"
     *     ),
     *     @OA\Response(
     *         response=404,
     *         description="Servidor efetivo não encontrado"
     *     )
     * )
     */
    public function update(UpdateServidorEfetivoRequest $request, $id)
    {
        $servidor = ServidorEfetivo::findOrFail($id);
        $servidor->update($request->validated());
        
        if ($request->has('endereco_ids')) {
            $servidor->pessoa->enderecos()->sync($request->endereco_ids);
        }
        
        return new ServidorEfetivoResource($servidor);
    }

    /**
     * @OA\Delete(
     *     path="/api/servidores-efetivos/{id}",
     *     tags={"Servidores Efetivos"},
     *     summary="Exclui um servidor efetivo por ID",
     *     description="Remove um servidor efetivo específico com base no seu ID.",
     *     security={{"sanctum": {}}},
     *     @OA\Parameter(
     *         name="id",
     *         in="path",
     *         description="ID do servidor efetivo a ser excluído",
     *         required=true,
     *         @OA\Schema(type="integer")
     *     ),
     *     @OA\Response(
     *         response=204,
     *         description="Servidor efetivo excluído com sucesso"
     *     ),
     *     @OA\Response(
     *         response=404,
     *         description="Servidor efetivo não encontrado"
     *     )
     * )
     */
    public function destroy($id)
    {
        $servidor = ServidorEfetivo::findOrFail($id);
        $servidor->delete();
        
        return response()->noContent();
    }

    /**
     * @OA\Post(
     *     path="/api/servidores-efetivos/{id}/enderecos",
     *     tags={"Servidores Efetivos", "Endereços"},
     *     summary="Adiciona um endereço a um servidor efetivo existente",
     *     description="Adiciona um novo endereço à servidor efetivo especificada.",
     *     security={{"sanctum": {}}},
     *     @OA\Parameter(
     *         name="id",
     *         in="path",
     *         description="ID do servidor efetivo para adicionar o endereço",
     *         required=true,
     *         @OA\Schema(type="integer")
     *     ),
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(ref="#/components/schemas/AddEnderecoToServidorEfetivoRequest")
     *     ),
     *     @OA\Response(
     *         response=200,
     *         description="Endereço adicionado com sucesso",
     *         @OA\JsonContent(ref="#/components/schemas/ServidorEfetivoResource")
     *     ),
     *     @OA\Response(
     *         response=404,
     *         description="Servidor Efetivo não encontrado"
     *     ),
     *     @OA\Response(
     *         response=400,
     *         description="Requisição inválida"
     *     )
     * )
     */
    public function addEndereco(AddEnderecoToServidorEfetivoRequest $request, $id)
    {
        $servidor = ServidorEfetivo::findOrFail($id);
        $endereco = Endereco::create($request->validated());

        $servidor->pessoa->enderecos()->attach($endereco->end_id);

        return new ServidorEfetivoResource($servidor->load('pessoa.enderecos'));
    }

    /**
     * @OA\Get(
     *     path="/api/servidores-efetivos/unidade/{unidadeId}",
     *     tags={"Busca"},
     *     summary="Lista os servidores efetivos por ID da unidade",
     *     description="Retorna uma lista de servidores efetivos pertencentes a uma unidade específica.",
     *     security={{"sanctum": {}}},
     *     @OA\Parameter(
     *         name="unidadeId",
     *         in="path",
     *         description="ID da unidade para buscar os servidores efetivos",
     *         required=true,
     *         @OA\Schema(type="integer")
     *     ),
     *     @OA\Response(
     *         response=200,
     *         description="Lista de servidores efetivos da unidade retornada com sucesso",
     *         @OA\JsonContent(
     *             type="array",
     *             @OA\Items(ref="#/components/schemas/ConsultaServidorLotadoPorUnidadeResource")
     *         )
     *     ),
     *     @OA\Response(
     *         response=204,
     *         description="Nenhum servidor efetivo encontrado para esta unidade"
     *     ),
     *     @OA\Response(
     *         response=404,
     *         description="Unidade não encontrada"
     *     )
     * )
     */
    public function getByUnidade($unidadeId)
    {
        $servidores = ServidorEfetivo::whereHas('lotacoes', function($query) use ($unidadeId) {
            $query->where('unid_id', $unidadeId);
        })->with(['pessoa', 'lotacoes.unidade'])->paginate(10);
        
        if ($servidores->isEmpty()) {
            return response()->noContent();
        }
        
        return ConsultaServidorLotadoPorUnidadeResource::collection($servidores);
    }

    /**
     * @OA\Get(
     *     path="/api/servidores-efetivos/endereco-funcional",
     *     tags={"Busca"},
     *     summary="Consulta endereço funcional de servidores efetivos por nome (parcial ou completo)",
     *     description="Retorna uma lista de endereços de unidades de servidores efetivos cujo nome corresponde ao parâmetro de busca.",
     *     security={{"sanctum": {}}},
     *     @OA\Parameter(
     *         name="nome",
     *         in="query",
     *         description="Nome (ou parte do nome) do servidor efetivo a ser buscado",
     *         required=true,
     *         @OA\Schema(type="string")
     *     ),
     *     @OA\Response(
     *         response=200,
     *         description="Lista de endereços funcionais de servidores efetivos encontrados por nome retornada com sucesso",
     *         @OA\JsonContent(
     *             type="array",
     *             @OA\Items(ref="#/components/schemas/ConsultaEnderecoFuncionalServidorEfetivoPorNomeResource")
     *         )
     *     ),
     *     @OA\Response(
     *         response=204,
     *         description="Nenhum servidor efetivo encontrado com este nome"
     *     )
     * )
     */
    public function getEnderecoFuncionalByNome(Request $request)
    {
        $nome = $request->query('nome');
        $servidores = ServidorEfetivo::whereHas('pessoa', function($query) use ($nome) {
            $query->where('pes_nome', 'like', "%{$nome}%");
        })->with(['pessoa', 'lotacoes.unidade.enderecos'])->paginate(10);

        if ($servidores->isEmpty()) {
            return response()->noContent();
        }

        return ConsultaEnderecoFuncionalServidorEfetivoPorNomeResource::collection($servidores);
    }
}
