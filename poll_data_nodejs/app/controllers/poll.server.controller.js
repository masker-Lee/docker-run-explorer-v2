import nis from '../utils/nisRequest';
import addressUtil from '../utils/address';
import messageUtil from '../utils/message';
import jsonUtil from '../utils/jsonUtil';
import timeUtil from '../utils/timeUtil';
import { BroadcastedPoll, NEMVoting } from "nem-voting";
import { NEMLibrary, Address, NetworkTypes } from "nem-library";
import cache from '../cache/appCache';

var myCache = cache.appCache

const LISTSIZE = 30; //list size

module.exports = {

	/**
     * get poll list
     */
	pollList: (req, res, next) => {
		let mykeys = myCache.keys();
		// let value = myCache.mget(mykeys); 
		let valueList = [];
		for(let i in mykeys){
			let value = myCache.get(mykeys[i]);
			valueList.push(value);
		}
		res.json(valueList);
	},

	/**
     * get poll detail from poll address
     */
	poll: (req, res, next) => {
		try {
			let address = req.body.address;
			if(!address){
				res.json([]);
				return;
			}
			console.log("poll address:" + address)
			let doc = myCache.get(address)
			const pollAddress = new Address(address);
			BroadcastedPoll.fromAddress(pollAddress)
				.map((poll) => {
					return poll;
				})
				.subscribe((data) => {
					if(!data){
						res.json({});
						return;
					}
					let poll = {};
					data = data.data;
					poll.title = data.formData.title;
					poll.type = data.formData.type;
					poll.doe = data.formData.doe;
					poll.creator = doc.creator;
					poll.address = doc.address;
					poll.description = data.description;
					poll.strings = data.options;
					if(data.whitelist){
						let whitelistArr = [];
						for(let i in data.whitelist)
							whitelistArr.push(data.whitelist[i].value);
						poll.whitelist = whitelistArr
					}
					if(data.formData.multiple==true)
						poll.multiple = 1;
					else
						poll.multiple = 0;
					res.json(poll);
				});
		} catch (e) {
			console.error(e);
		}
	},

	/**
     * query result
     */
	pollResult: (req, res, next) => {
		try {
			let address = req.body.address;
			if(!address){
				res.json([]);
				return;
			}
			
			const pollAddress = new Address(address);
			BroadcastedPoll.fromAddress(pollAddress)
				.switchMap((poll) => {
					return poll.getResults();
				})
				.subscribe((results) => {
					res.json(results);
				});
		} catch (e) {
			console.error(e);
		}
	},

	/**
     * return the poi poll voters 
     */
	pollResultVoters: (req, res, next) => {
		try {
			let address = req.body.address;
			let strings = req.body.strings;
			if(!address || !strings){
				res.json([]);
				return;
			}
			// strings = JSON.stringify(strings)
			let labels = eval(strings)
			let indexMap = new Map();
			for(let i in labels)
				indexMap.set(labels[i], i);
				console.log("label:" + labels)
				console.log("indexMap:" + JSON.stringify(indexMap))
			const pollAddress = new Address(address);
			BroadcastedPoll.fromAddress(pollAddress)
			    .switchMap((poll) => {
			        return poll.getVoters();
			    })
			    .subscribe((voters) => {
			    	let voterArr = [];
			    	// init arrary
			    	for(let i in labels)
						voterArr[i] = [];
					// push voters into arrary
			    	for(let i in voters){
						let vote = voters[i];
			    		if(!indexMap.has(vote.option))
							continue;
						
			    		let index = indexMap.get(vote.option);
						let length = voterArr[index].length;
			    		voterArr[index][length] = {};
			    		voterArr[index][length].addr = vote.address;
			    		voterArr[index][length].poi = vote.importance;
			    		voterArr[index][length].block = vote.block;
			    		voterArr[index][length].fmtPOI = (vote.importance*100).toFixed(5)+"%";
					}
			    	res.json(voterArr);
			    });
		} catch (e) {
			console.error(e);
		}
	}
}

