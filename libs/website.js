var request = require('request')
var search  = require('./search')


module.exports = function() {

	return {

		load : function(row) {

			request(row.url, function (error, response, body) {
				if (!error && response.statusCode == 200)
					search.after(row, body)
			})
		},
	}
}()
