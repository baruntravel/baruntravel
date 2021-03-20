FROM node:14

WORKDIR /home/baruntravele

COPY . .
RUN npm install
EXPOSE 3000

CMD ["npm", "run", "--host 0.0.0.0", "start"]
