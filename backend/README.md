
```ignorelang
server {
    if ($host = socket.snow-city.world) {
        return 301 https://$host$request_uri;
    } # managed by Certbot


  listen 80;
  server_name socket.snow-city.world;
  location / {
    return 307 https://socket.snow-city.world$request_uri;
  }


}
server {
  listen 443;
  listen [::]:443;
  server_name socket.snow-city.world;
  client_max_body_size 0;

  location / {
    proxy_pass http://snowcitysocket;
   #proxy_set_header Host $host;
   #proxy_set_header X-Real-IP $remote_addr;
   #proxy_set_header X-Proxy-From api.snow-city.world;
   #proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
   #proxy_redirect off;
    proxy_http_version 1.1;
    proxy_set_header Upgrade $http_upgrade;
    proxy_set_header Connection "Upgrade";
    proxy_set_header Host $host;
  }

    ssl_certificate /etc/letsencrypt/live/api.snow-city.world/fullchain.pem; # managed by Certbot
    ssl_certificate_key /etc/letsencrypt/live/api.snow-city.world/privkey.pem; # managed by Certbot
}
upstream snowcitysocket {
        server localhost:8081;
}

server {
  server_name api.snow-city.world;
  location / {
    return 307 https://api.snow-city.world$request_uri;
  }

    listen 443 ssl; # managed by Certbot
    ssl_certificate /etc/letsencrypt/live/api.snow-city.world/fullchain.pem; # managed by Certbot
    ssl_certificate_key /etc/letsencrypt/live/api.snow-city.world/privkey.pem; # managed by Certbot
    include /etc/letsencrypt/options-ssl-nginx.conf; # managed by Certbot
    ssl_dhparam /etc/letsencrypt/ssl-dhparams.pem; # managed by Certbot

}
server {
  listen 443;
  listen [::]:443;
  server_name api.snow-city.world;
  client_max_body_size 0;

  location / {
    proxy_pass http://snowcity;
    proxy_set_header Host $host;
    proxy_set_header X-Real-IP $remote_addr;
    proxy_set_header X-Proxy-From api.snow-city.world;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    proxy_redirect off;
  }
}
upstream snowcity {
        server localhost:8080;
}


server {
    if ($host = api.snow-city.world) {
        return 301 https://$host$request_uri;
    } # managed by Certbot


  listen 80;
  server_name api.snow-city.world;
    return 404; # managed by Certbot

}
```
