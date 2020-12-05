import config from '../config/config';
import { BroadcastedPoll, PollIndex, PollConstants } from "nem-voting";
import { NEMLibrary, Address, NetworkTypes, AccountHttp } from "nem-library";
import schedule from 'node-schedule';

import cache from '../cache/appCache';

var myCache = cache.appCache

var domain = require('domain')

const pollPoolAddress = new Address(config.pollAccount);
var lastPollId = undefined //the pollindeAll use
var lastIdNow = 0 //the lastest lastId , pollindex use

let schedulePollIndex = () => {
	console.log("poll schedule is running");

	var d = domain.create()
	d.on('error',err => {
		console.log(err);
		d.run(pollIndexAll)
	})
	d.run(pollIndexAll)

	let rule = new schedule.RecurrenceRule();
	// rule.minute = [1, 6, 11, 16, 21, 26, 31, 36, 41, 46, 51, 56]; //fetch pollIndex every 5 mins
	rule.second = [1];//fetch pollIndex every 1 mins
	schedule.scheduleJob(rule, () => {
		pollIndex();
	});
}

/**
 * fetch poll index from blockchain
 */
let pollIndex = () => {
	PollIndex.fromAddress(pollPoolAddress)
	    .subscribe((results) => {
			console.log("pollIndex is running")
	    	if(!results && !results.headers)
				return;
			var lastId = Number(results.lastId)
			if(lastIdNow == lastId) return
			//update the lastIdNow
			lastIdNow = lastId
			
			for(let i in results.headers){
				let header = results.headers[i]
				if(myCache.has(header.address.value))
					continue;
				let savePollIndex = {};
				savePollIndex.creator = header.creator.value;
				savePollIndex.address = header.address.value;
				savePollIndex.title = header.title;
				savePollIndex.type = header.type;
				savePollIndex.doe = header.doe;
				myCache.set(savePollIndex.address,savePollIndex);
			}
	    });
}

/**
 * fetch all poll index from blockchain
 */
function pollIndexAll() {
			PollIndex.fromAddress(pollPoolAddress,lastPollId)
			.subscribe((results) => {
				if(!results && !results.headers){
					return;
				}
				lastPollId = Number(results.lastId)
				console.log("poll all last id:" + lastPollId)
				
				for(let i in results.headers){
					let header = results.headers[i]
					if(myCache.has(header.address.value))
						continue;
					let savePollIndex = {};
					savePollIndex.creator = header.creator.value;
					savePollIndex.address = header.address.value;
					savePollIndex.title = header.title;
					savePollIndex.type = header.type;
					savePollIndex.doe = header.doe;
					myCache.set(savePollIndex.address,savePollIndex);
				}
				
				//the last poll id is 924993
				if(lastPollId > 924993)	
					pollIndexAll()
			})
}


module.exports = {
	schedulePollIndex
}
