# Use the latest stable official PHP FPM image (e.g., 8.3)
FROM php:8.3-fpm

# Set working directory
WORKDIR /var/www/html

# Install system dependencies needed for extensions
# - libpq-dev: for PostgreSQL driver (pdo_pgsql)
# - libzip-dev, zip, unzip: for zip extension and Composer
# - git: for Composer dependencies from git
# - libpng-dev, libjpeg-dev, libfreetype6-dev: for GD extension
RUN apt-get update && apt-get install -y \
    git \
    unzip \
    zip \
    libzip-dev \
    libpq-dev \
    libpng-dev \
    libjpeg62-turbo-dev \
    libfreetype6-dev \
    && rm -rf /var/lib/apt/lists/*

# Install required PHP extensions for Laravel
RUN docker-php-ext-configure gd --with-freetype --with-jpeg \
    && docker-php-ext-install -j$(nproc) gd \
    && docker-php-ext-install \
    pdo \
    pdo_pgsql \
    zip \
    bcmath \
    pcntl \
    exif \
    opcache

# Install Composer globally
COPY --from=composer:latest /usr/bin/composer /usr/bin/composer

# Copy existing application directory contents (if any, otherwise it will be mounted)
# COPY . /var/www/html

# Set permissions for storage and bootstrap/cache (important!)
# We'll run chown after mounting the volume in docker-compose startup or manually initially.
# RUN chown -R www-data:www-data /var/www/html/storage /var/www/html/bootstrap/cache

# Expose port 9000 and start php-fpm server
EXPOSE 9000
CMD ["php-fpm"]
