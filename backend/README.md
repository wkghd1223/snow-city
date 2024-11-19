
```ignorelang
server {
  listen 443;
  listen [::]:443;
  server_name socket.snow-city.world;
  client_max_body_size 0;

  location / {
    proxy_pass http://snowcitysocket;
    proxy_set_header Host $host;
    proxy_set_header X-Real-IP $remote_addr;
    proxy_set_header X-Proxy-From api.snow-city.world;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    proxy_redirect off;
  }
}
upstream snowcitysocket {
        server localhost:8081;
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
```