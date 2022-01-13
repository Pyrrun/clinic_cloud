# build stage
FROM node:16 as build-stage
WORKDIR /app
COPY package*.json ./
COPY yarn.lock ./
COPY . .
RUN npm install
RUN npm add -D prisma
RUN npx prisma generate
RUN npx tsc

# production stage
FROM nginx:stable-alpine as production-stage
RUN mkdir /app
COPY --from=build-stage /app/dist /app
COPY nginx.conf /etc/nginx/nginx.conf
#COPY entrypoint.sh /docker-entrypoint.d/entrypoint.sh
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
