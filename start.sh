#!/bin/bash
export KC_SERVICE_ADDR="https://kc.mexdev.ru/auth/"
export KC_SERVICE_PORT=4443
docker-compose up -d --build
