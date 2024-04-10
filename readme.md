# Bidding Backend

## Overview

An auction backend application where users can sell their own items, view and bid on other user's items. These items are
created with starting and final bid amount sold in an auction in a limited time.
In addition, Bids are event streamed so that users have real-time updates of items` current price.
In order to moderate the items that are auctioned, an admin account has super privilege over users' items. However,
they cannot create items, set an auction and bid on an item.

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

## Featured Technology

* Docker
* WebSocket
* Spring Events
* Spring Security Filter
* JWT
* Role-Based Access Control
