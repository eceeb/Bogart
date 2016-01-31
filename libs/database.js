var mongodb = require('mongodb')
var dbUrl   = process.env.MONGOLAB_URI


var select = function (interval, callback) {

	mongodb.MongoClient.connect(dbUrl, function (err, db) {
		if (err) throw err

		var searches = db.collection('searches')
		var cursor   = searches.find({ interval : { $eq: interval } })
		cursor.each(function(err, doc) {
			if ( err || doc == null)
				db.close()
			else
				callback(doc)
		})
	})
}

var update = function (row) {

	mongodb.MongoClient.connect(dbUrl, function (err, db) {
		if (err) throw err

		var searches = db.collection('searches')
		searches.update({_id : row._id}, row, function (err, result) {
			db.close()
		})
	})
}

module.exports = {
    select: select,
    update: update
}
