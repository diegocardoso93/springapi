<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('enderecos', function (Blueprint $table) {
            $table->id('end_id');
            $table->string('end_tipo_logradouro', 50)->nullable();
            $table->string('end_logradouro', 200)->nullable();
            $table->integer('end_numero')->nullable();
            $table->string('end_bairro', 100)->nullable();
            // Foreign key to cidades table (nullable as not specified otherwise)
            $table->foreignId('cid_id')
                  ->nullable()
                  ->constrained('cidades', 'cid_id'); // References cid_id in cidades table
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('enderecos');
    }
};
