const express = require('express');
const cors = require('cors')
const serveStatic = require("serve-static")
const history = require('connect-history-api-fallback')
const path = require('path');
const app = express();
app.use(history({
    verbose: true
}))
app.use(serveStatic(path.join(__dirname, 'dist')));
const port = process.env.PORT || 3000;
app.listen(port, () => {
    console.log('hello world!')
});
app.use(cors())
