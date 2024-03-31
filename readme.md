# Bidding Backend

## Overview

An auction backend where users can sell their own items, view and bid on other user's items. These items are
created with starting and final bid amount sold in an auction in a limited time.

## Installation and Running

### Locally

1. Pull and instantiate a MySQL and a PHPAdmin image.
    ```shell
    docker-compose -f docker-compose.local.yml up -d
    ```

2. Start the application via your favorite IDE.

### Docker

1. Pull and instantiate a MySQL and a PHPAdmin image along with the containerized application.
   ```shell
   docker-compose up -d
   ```

