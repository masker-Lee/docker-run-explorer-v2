import http from 'http';
import express from 'express';
import bodyParser from 'body-parser';
import config from './config';
import { NEMLibrary, NetworkTypes } from "nem-library";
import pollIndexSchedule from '../schedule/pollIndexSchedule';

module.exports = () => {
	console.log('init express...');
	let app = express();
	app.use(bodyParser.urlencoded({ extended: false }));
	app.use(bodyParser.json());
	//include the route
	require('../routes/poll.server.route')(app);

	app.all("*",function(req,res,next){
		//设置允许跨域的域名，*代表允许任意域名跨域
		res.header("Access-Control-Allow-Origin","*");
		//允许的header类型
		res.header("Access-Control-Allow-Headers","content-type");
		res.header("Content-Type", "application/json;charset=utf-8");
		// res.header("Access-Control-Allow-Headers", "X-Requested-With");
		//跨域允许的请求方式 
		res.header("Access-Control-Allow-Methods","DELETE,PUT,POST,GET,OPTIONS");
		if (req.method.toLowerCase() == 'options')
			res.send(200);  //让options尝试请求快速结束
		else
			next();
	})

	app.get('/', (req, res) => {
		res.redirect('/blocklist.html');
	});

	app.use((req, res, next) => {
		res.status(404);
		try {
			return res.json('Not Found');
		} catch (e) {
			console.error('404 set header after sent');
		}
	});
	app.use((err, req, res, next) => {
		if(err) {
			return next();
		}
		res.status(500);
		try {
			return res.json(err.message || 'server error');
		} catch (e) {
			console.error('500 set header after sent');
		}
	});

	let server = http.createServer(app);
	// init NEMLibrary 
	if(config.network==68){
		NEMLibrary.bootstrap(NetworkTypes.MAIN_NET);}
	else{
		NEMLibrary.bootstrap(NetworkTypes.TEST_NET);}

	// schedule fetch poll index
	pollIndexSchedule.schedulePollIndex();
	
	process.on('uncaughtException', function(e) {
	　　console.log(e);
	});
	
	server.listen(config.port, function(){
		console.log('app started, listening on port:', config.port);
	});

	return app;
};