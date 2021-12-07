require("dotenv").config();
const express = require("express");
const cookieParser = require("cookie-parser");
const router = require("./routes/index");
const cors = require("cors");
const { Client } = require("pg");

const client = new Client({
  connectionString: process.env.PG_CONNECTION_STRING,
  ssl: { rejectUnauthorized: false },
});

client.connect((err) => {
  if (err) console.error("DB connection error", err.stack);
  else console.log("DB connected");
});

const corsOptions = {
  origin: process.env.CLIENT_URL,
  optionsSuccessStatus: 200,
  credentials: true,
};

const app = express();

app.use(cors(corsOptions));
app.use(express.json());
app.use(cookieParser());
app.use("/", router);

const port = process.env.AUTH_SERVER_PORT || 5555;
app.listen(port, () => {
  console.log(`App running on port ${port}`);
});

module.exports = app;
