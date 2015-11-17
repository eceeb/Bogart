var mongodb = require('mongodb')
var emitter = require('./emitter')
var dbUrl   = process.env.MONGOLAB_URI


var db = function() {

	// TODO consider using a callback
	emitter.on('foundRow', function (doc) {
		db.update(doc)
	})

	return  {

		select : function (interval, callback) {

			mongodb.MongoClient.connect(dbUrl, function (err, db) {
				if (err) throw err

				var searches = db.collection('searches')
				searches.find({ interval : { $eq: interval } }).toArray(function (err, docs) {
					db.close()
					callback(docs)
				})
			})
		},

		update : function(row) {

			mongodb.MongoClient.connect(dbUrl, function (err, db) {
				if (err) throw err

				var searches = db.collection('searches')
				searches.update({_id : row._id}, row, function (err, result) {
					db.close()
				})
			})
		}
	}
}()

module.exports = db
