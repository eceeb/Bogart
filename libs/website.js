var request   = require('request')
var search    = require('./search')
var searchG2a = require('./searchG2a')

module.exports = function() {

	return {

		load : function(row) {
			console.log(row.url)
			request(row.url, function (error, response, body) {
				if (!error && response.statusCode == 200) {
					if (row.url.lastIndexOf('https://www.g2a.com', 0) === 0)
						searchG2a.after(row, body)
					else
						search.after(row, body)
				}
			})
		},
	}
}()
