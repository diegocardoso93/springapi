<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\AuthController;
use App\Http\Controllers\CidadeController;
use App\Http\Controllers\EnderecoController;
use App\Http\Controllers\FotoPessoaController;
use App\Http\Controllers\LotacaoController;
use App\Http\Controllers\ServidorEfetivoController;
use App\Http\Controllers\ServidorTemporarioController; // Added
use App\Http\Controllers\UnidadeController; // Added

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider and all of them will
| be assigned to the "api" middleware group. Make something great!
|
*/

// Route::post('/login', [AuthController::class, 'login']);

// Public routes
Route::post('register', [AuthController::class, 'register']);
Route::post('login', [AuthController::class, 'login']);


Route::get('show', [FotoPessoaController::class, 'showUploadedImages']);

Route::group(['middleware' => ['auth:sanctum']], function () {
    Route::post('logout', [AuthController::class, 'logout']);
    Route::post('refresh', [AuthController::class, 'refresh']);

    Route::prefix('/cidades')->group(function () {
        Route::get('/', [CidadeController::class, 'index']);
        Route::get('/{id}', [CidadeController::class, 'show']);
        Route::post('/', [CidadeController::class, 'store']);
        Route::put('/{id}', [CidadeController::class, 'update']);
        Route::delete('/{id}', [CidadeController::class, 'destroy']);
    });

    Route::prefix('/enderecos')->group(function () {
        Route::get('/{id}', [EnderecoController::class, 'show']);
        Route::post('/', [EnderecoController::class, 'store']);
        Route::put('/{id}', [EnderecoController::class, 'update']);
        Route::delete('/{id}', [EnderecoController::class, 'destroy']);
    });

    Route::prefix('/fotos')->group(function () {
        Route::post('/upload', [FotoPessoaController::class, 'upload']);
        Route::get('/links/{pesId}', [FotoPessoaController::class, 'getLinks']);
    });

    Route::prefix('/lotacoes')->group(function () {
        Route::get('/', [LotacaoController::class, 'index']);
        Route::get('/{id}', [LotacaoController::class, 'show']);
        Route::post('/', [LotacaoController::class, 'store']);
        Route::put('/{id}', [LotacaoController::class, 'update']);
        Route::delete('/{id}', [LotacaoController::class, 'destroy']);
    });

    Route::prefix('/servidores-efetivos')->group(function () {
        Route::get('/unidade/{unidadeId}', [ServidorEfetivoController::class, 'getByUnidade']);
        Route::get('/endereco-funcional', [ServidorEfetivoController::class, 'getEnderecoFuncionalByNome']);

        Route::get('/', [ServidorEfetivoController::class, 'index']);
        Route::post('/', [ServidorEfetivoController::class, 'store']);
        Route::get('/{id}', [ServidorEfetivoController::class, 'show']);
        Route::put('/{id}', [ServidorEfetivoController::class, 'update']);
        Route::delete('/{id}', [ServidorEfetivoController::class, 'destroy']);
        
        Route::post('/{id}/enderecos', [ServidorEfetivoController::class, 'addEndereco']);
    });

    Route::prefix('/servidores-temporarios')->group(function () {
        Route::get('/', [ServidorTemporarioController::class, 'index']);
        Route::post('/', [ServidorTemporarioController::class, 'store']);
        Route::get('/{id}', [ServidorTemporarioController::class, 'show']);
        Route::put('/{id}', [ServidorTemporarioController::class, 'update']);
        Route::delete('/{id}', [ServidorTemporarioController::class, 'destroy']);
    });

    Route::prefix('/unidades')->group(function () {
        Route::get('/', [UnidadeController::class, 'index']);
        Route::post('/', [UnidadeController::class, 'store']);
        Route::get('/{id}', [UnidadeController::class, 'show']);
        Route::put('/{id}', [UnidadeController::class, 'update']);
        Route::delete('/{id}', [UnidadeController::class, 'destroy']);
        Route::post('/{id}/enderecos', [UnidadeController::class, 'addEndereco']);
    });
});
