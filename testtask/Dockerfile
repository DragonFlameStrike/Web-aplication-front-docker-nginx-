FROM node:lts-alpine as build-stage
WORKDIR /app
COPY ["package.json", "package-lock.json*","./"]
RUN npm install
COPY . .
RUN npm run build
FROM nginx:stable-alpine as production-stage
RUN cd /usr/share/nginx/html/
RUN mkdir /root/category/
RUN cd
COPY --from=build-stage /app/src/main/resources/root/categories/category.html /usr/share/nginx/html/root/categories/
COPY --from=build-stage /app/src/main/resources/index.html /usr/share/nginx/html/
COPY --from=build-stage /app/src/main/resources/* /usr/share/nginx/html/
RUN rm /etc/nginx/conf.d/default.conf
COPY nginx.conf /etc/nginx/conf.d/
EXPOSE 8081
RUN ls /usr/share/nginx/html/root/
RUN ls /usr/share/nginx/html/root/categories

CMD ["nginx", "-g","daemon off;"]
