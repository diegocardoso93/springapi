<?php

namespace App\Http\Controllers;

use App\Models\Lotacao;
use App\Http\Resources\LotacaoResource;
use Illuminate\Http\Request;

/**
 * @OA\Schema(
 *     schema="Lotacao",
 *     type="object",
 *     @OA\Property(property="lot_id", type="integer", readOnly=true),
 *     @OA\Property(property="lot_data_lotacao", type="string", format="date"),
 *     @OA\Property(property="lot_data_remocao", type="string", format="date"),
 *     @OA\Property(property="lot_portaria", type="string"),
 *     @OA\Property(property="pes_id", type="integer"),
 *     @OA\Property(property="unid_id", type="integer")
 * )
 * @OA\Tag(
 *     name="Lotações",
 *     description="Operações relacionadas a lotações"
 * )
 */
class LotacaoController extends Controller
{
    /**
     * @OA\Get(
     *      path="/api/lotacoes",
     *      operationId="listLotacoes",
     *      tags={"Lotações"},
     *      summary="Listar todas as lotações",
     *      security={{"sanctum": {}}},
     *      @OA\Response(
     *          response=200,
     *          description="Lista de lotações",
     *          @OA\JsonContent(
     *              type="array",
     *              @OA\Items(ref="#/components/schemas/Lotacao")
     *          )
     *      )
     * )
     */
    public function index(Request $request)
    {
        $perPage = $request->input('size', 10);
        $lotacoes = Lotacao::with(['pessoa', 'unidade'])->paginate($perPage);
        return LotacaoResource::collection($lotacoes);
    }

    /**
     * @OA\Get(
     *      path="/api/lotacoes/{id}",
     *      operationId="getLotacao",
     *      tags={"Lotações"},
     *      summary="Buscar lotação por ID",
     *      security={{"sanctum": {}}},
     *      @OA\Parameter(
     *          name="id",
     *          in="path",
     *          required=true,
     *          @OA\Schema(type="integer")
     *      ),
     *      @OA\Response(response=200, description="Lotação encontrada"),
     *      @OA\Response(response=404, description="Lotação não encontrada")
     * )
     */
    public function show($id)
    {
        $lotacao = Lotacao::with(['pessoa', 'unidade'])->findOrFail($id);
        return new LotacaoResource($lotacao);
    }

    /**
     * @OA\Post(
     *      path="/api/lotacoes",
     *      operationId="createLotacao",
     *      tags={"Lotações"},
     *      summary="Criar nova lotação",
     *      security={{"sanctum": {}}},
     *      @OA\RequestBody(
     *          required=true,
     *          @OA\JsonContent(ref="#/components/schemas/Lotacao")
     *      ),
     *      @OA\Response(response=201, description="Lotação criada"),
     *      @OA\Response(response=400, description="Dados inválidos")
     * )
     */
    public function store(Request $request)
    {
        $data = $request->validate([
            'lot_data_lotacao' => 'required|date',
            'lot_data_remocao' => 'nullable|date',
            'lot_portaria' => 'required|string',
            'pes_id' => 'required|exists:pessoa,pes_id',
            'unid_id' => 'required|exists:unidade,unid_id'
        ]);

        $lotacao = Lotacao::create($data);
        return new LotacaoResource($lotacao);
    }

    /**
     * @OA\Put(
     *      path="/api/lotacoes/{id}",
     *      operationId="updateLotacao",
     *      tags={"Lotações"},
     *      summary="Atualizar lotação",
     *      security={{"sanctum": {}}},
     *      @OA\Parameter(
     *          name="id",
     *          in="path",
     *          required=true,
     *          @OA\Schema(type="integer")
     *      ),
     *      @OA\RequestBody(
     *          required=true,
     *          @OA\JsonContent(ref="#/components/schemas/Lotacao")
     *      ),
     *      @OA\Response(response=200, description="Lotação atualizada"),
     *      @OA\Response(response=400, description="Dados inválidos"),
     *      @OA\Response(response=404, description="Lotação não encontrada")
     * )
     */
    public function update(Request $request, $id)
    {
        $lotacao = Lotacao::findOrFail($id);
        
        $data = $request->validate([
            'lot_data_lotacao' => 'required|date',
            'lot_data_remocao' => 'nullable|date',
            'lot_portaria' => 'required|string',
            'pes_id' => 'required|exists:pessoa,pes_id',
            'unid_id' => 'required|exists:unidade,unid_id'
        ]);

        $lotacao->update($data);
        return new LotacaoResource($lotacao);
    }

    /**
     * @OA\Delete(
     *      path="/api/lotacoes/{id}",
     *      operationId="deleteLotacao",
     *      tags={"Lotações"},
     *      summary="Excluir lotação",
     *      security={{"sanctum": {}}},
     *      @OA\Parameter(
     *          name="id",
     *          in="path",
     *          required=true,
     *          @OA\Schema(type="integer")
     *      ),
     *      @OA\Response(response=204, description="Lotação excluída"),
     *      @OA\Response(response=404, description="Lotação não encontrada")
     * )
     */
    public function destroy($id)
    {
        $lotacao = Lotacao::findOrFail($id);
        $lotacao->delete();
        return response()->noContent();
    }
}
