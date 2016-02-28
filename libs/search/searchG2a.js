var cheerio = require('cheerio')

module.exports = function () {

    return {

        after : function (seek, body) {

            $ = cheerio.load(body)
            var priceElement = $('div.selected-price').text().trim()
            var actualPrice  = priceElement.replace(',', '.').match(/\d+\.?\d*/)
            var desiredPrice = seek.replace(',', '.').match(/\d+\.?\d*/)

            // This logging is for email notification via papertrail
            actualPrice || console.log('Gta price: null')

            return parseFloat(actualPrice) <= parseFloat(desiredPrice)
        }
    }
}()
