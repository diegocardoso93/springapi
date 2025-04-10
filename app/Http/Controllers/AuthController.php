<?php

namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use Hash;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;
use App\Models\User;

/**
 * @OA\Info(
 *     title="API Servidores",
 *     version="1.0.0",
 *     description="API Servidores"
 * )
 *
 * @OA\Tag(
 *     name="Auth",
 *     description="Authentication related endpoints"
 * )
 */
class AuthController extends Controller
{
    // public function __construct()
    // {
    //     $this->middleware('auth:jwt', ['except' => ['login', 'register']]);
    // }

    /**
     * @OA\Post(
     *     path="/api/register",
     *     tags={"Auth"},
     *     summary="Create API User",
     *     description="Authenticates user using username and password and returns a token.",
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             required={"username","password"},
     *             @OA\Property(property="username", type="string", example="johndoe"),
     *             @OA\Property(property="password", type="string", example="secret")
     *         )
     *     ),
     *     @OA\Response(
     *         response=200,
     *         description="Successful login, returns user and token",
     *         @OA\JsonContent(
     *             @OA\Property(property="user", type="object"),
     *             @OA\Property(property="token", type="integer")
     *         )
     *     ),
     *     @OA\Response(response=401, description="Unauthorized"),
     *     @OA\Response(response=422, description="Validation error")
     * )
     */
    public function register(Request $request)
    {
        $validatedData = $request->validate([
          'username' => 'required|string|max:255|unique:users',
          'password' => 'required|string|min:6'
        ]);

        $user = User::create([
          'username' => $validatedData['username'],
          'password' => bcrypt($validatedData['password']),
        ]);

        // Create a token for the newly registered user
        // $token = JWTAuth::fromUser($user);
        
        return response()->json([
            'user' => $user
        ], 201);
    }

    /**
     * @OA\Post(
     *     path="/api/login",
     *     tags={"Auth"},
     *     summary="Login and retrieve auth token",
     *     description="Authenticates user using username and password and returns an auth token.",
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             required={"username","password"},
     *             @OA\Property(property="username", type="string", example="johndoe"),
     *             @OA\Property(property="password", type="string", example="secret")
     *         )
     *     ),
     *     @OA\Response(
     *         response=200,
     *         description="Successful login, returns token",
     *         @OA\JsonContent(
     *             @OA\Property(property="access_token", type="string"),
     *             @OA\Property(property="token_type", type="string", example="bearer"),
     *             @OA\Property(property="expires_in", type="integer"),
     *             @OA\Property(property="user", type="object")
     *         )
     *     ),
     *     @OA\Response(response=401, description="Unauthorized"),
     *     @OA\Response(response=422, description="Validation error")
     * )
     */
    public function login(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'username' => 'required|string',
            'password' => 'required|string',
        ]);

        if ($validator->fails()) {
            return response()->json($validator->errors(), 422);
        }

        $credentials = $request->only('username', 'password');
        $user = User::firstWhere(['username' => $credentials['username']]);

        if (! $user || ! Hash::check($credentials['password'], $user->password)) {
            return response()->json(['error' => 'Unauthorized'], 401);
        }

        $token = $user->createToken(
            $credentials['username'] . now(),
            ['*'], 
            now()->addMinutes(5)
        ); // token expira em 5 minutos

        return response()->json([
            'user' => $user,
            'token' => $token->plainTextToken
        ], 201);
    }

    /**
     * @OA\Post(
     *     path="/api/logout",
     *     tags={"Auth"},
     *     summary="Logout user",
     *     description="Logs out the user and invalidates the token.",
     *     security={{"sanctum":{}}},
     *     @OA\Response(response=200, description="Successfully logged out")
     * )
     */
    public function logout(Request $request)
    {
        // Revoga o token atual
        $request->user()->currentAccessToken()->delete();

        return response()->json(['message' => 'Successfully logged out'], 200);
    }

    /**
     * @OA\Post(
     *     path="/api/refresh",
     *     tags={"Auth"},
     *     summary="Refresh access token",
     *     description="Revokes the current token and returns a new one.",
     *     security={{"sanctum":{}}},
     *     @OA\Response(
     *         response=200,
     *         description="New token returned",
     *         @OA\JsonContent(
     *             @OA\Property(property="access_token", type="string"),
     *             @OA\Property(property="token_type", type="string", example="bearer"),
     *             @OA\Property(property="expires_in", type="integer"),
     *             @OA\Property(property="user", type="object")
     *         )
     *     ),
     *     @OA\Response(response=401, description="Unauthorized"),
     *     @OA\Response(response=500, description="Could not refresh token")
     * )
     */
    public function refresh(Request $request)
    {
        try {
            $user = $request->user();

            // Revoga o token atual
            $request->user()->currentAccessToken()->delete();

            // Cria um novo token com expiração
            $newToken = $user->createToken(
                $user->username . now(),
                ['*'],
                now()->addMinutes(5) // 5 minutos como no login
            );

            return response()->json([
                'access_token' => $newToken->plainTextToken,
                'token_type' => 'bearer',
                'expires_in' => 300, // 5 minutos em segundos
                'user' => $user
            ], 200);

        } catch (\Exception $e) {
            return response()->json(['error' => 'Could not refresh token', 'details' => $e->getMessage()], 500);
        }
    }
}
