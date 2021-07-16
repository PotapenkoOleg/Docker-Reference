FROM node:16-alpine3.12
EXPOSE 3000
RUN mkdir /opt/testapp
WORKDIR /opt/testapp
COPY index.js ./
COPY package.json ./
RUN npm install
CMD [ "node", "./index.js" ]