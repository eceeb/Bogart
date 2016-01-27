var cheerio = require('cheerio')
var mail    = require('./mail')
var emitter = require('./emitter')


module.exports = function () {

    return {

        after : function (row, body) {
            $ = cheerio.load(body)
            var priceElement = $('div.selected-price').text().trim()
            var actualPrice  = priceElement.replace(',', '.').match(/\d+\.?\d*/)
            var desiredPrice = row.seek.replace(',', '.').match(/\d+\.?\d*/)

            console.log('Gta price: ' + actualPrice)

            if (parseFloat(actualPrice) <= parseFloat(desiredPrice)) {
                row.found = true
                mail.send(row)
                emitter.emit('foundRow', row)
            }
        },
    }
}()
