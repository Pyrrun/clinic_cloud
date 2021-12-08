# build stage
FROM node:12 as build-stage
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# production stage
FROM nginx:stable-alpine as production-stage
RUN mkdir /app
COPY --from=build-stage /app/dist /app
COPY nginx.conf /etc/nginx/nginx.conf
COPY entrypoint.sh /docker-entrypoint.d/entrypoint.sh
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
