<?php

namespace App\Http\Controllers\API;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Validator;
use App\Models\User;

class AuthController extends Controller
{
    /**
     * Create a new AuthController instance.
     * Apply jwt middleware to all methods except login (and register if you have it)
     *
     * @return void
     */
    public function __construct()
    {
        // Apply the 'jwt' guard middleware to protect methods
        // Allow access to 'login' and 'register' without a token
        $this->middleware('auth:jwt', ['except' => ['login', 'register']]);
    }

    /**
     * Get a JWT via given credentials.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\JsonResponse
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

        // Use the 'jwt' guard for attempting authentication
        $credentials = $request->only('username', 'password');

        // Because we added the username() method to the User model,
        // Auth::attempt will now look for the 'username' field.
        if (! $token = Auth::guard('jwt')->attempt($credentials)) {
            return response()->json(['error' => 'Unauthorized'], 401);
        }

        // If authentication successful, return the token
        return $this->respondWithToken($token);
    }

    /**
     * Get the authenticated User.
     *
     * @return \Illuminate\Http\JsonResponse
     */
    public function me()
    {
        // Use the 'jwt' guard to get the authenticated user
        try {
            $user = Auth::guard('jwt')->userOrFail();
             return response()->json($user);
        } catch (\Tymon\JWTAuth\Exceptions\UserNotDefinedException $e) {
             return response()->json(['error' => 'User not found or token invalid'], 404);
        }
    }

    /**
     * Log the user out (Invalidate the token).
     *
     * @return \Illuminate\Http\JsonResponse
     */
    public function logout()
    {
        Auth::guard('jwt')->logout(); // Invalidate the token

        return response()->json(['message' => 'Successfully logged out']);
    }

    /**
     * Refresh a token.
     * Requires a valid (even if expired) token to be sent.
     *
     * @return \Illuminate\Http\JsonResponse
     */
    public function refresh()
    {
        try {
            // Attempt to refresh the token using the 'jwt' guard
            $newToken = Auth::guard('jwt')->refresh();
             return $this->respondWithToken($newToken);
        } catch (\Tymon\JWTAuth\Exceptions\TokenInvalidException $e) {
             return response()->json(['error' => 'Token is invalid'], 401);
        } catch (\Tymon\JWTAuth\Exceptions\JWTException $e) {
             // This can happen if the token cannot be refreshed (e.g., refresh TTL expired)
             return response()->json(['error' => 'Could not refresh token', 'details' => $e->getMessage()], 500);
        }
    }

    /**
     * Get the token array structure.
     *
     * @param  string $token
     * @return \Illuminate\Http\JsonResponse
     */
    protected function respondWithToken($token)
    {
        return response()->json([
            'access_token' => $token,
            'token_type' => 'bearer',
             // Get token TTL from config (in minutes) and convert to seconds
            'expires_in' => Auth::guard('jwt')->factory()->getTTL() * 60,
            'user' => Auth::guard('jwt')->user() // Optionally return user info on login
        ]);
    }
}
