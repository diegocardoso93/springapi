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
        Schema::create('servidores_efetivos', function (Blueprint $table) {
            $table->foreignId('pes_id')
                  ->primary()
                  ->constrained('pessoas', 'pes_id');
            $table->string('se_matricula', 20)->nullable();
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('servidores_efetivos');
    }
};
