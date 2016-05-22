var searchG2a     = require('./searchG2a')
var searchGeneric = require('./searchGeneric')

module.exports = function () {

    return {

        after : function (doc, body) {
            if (~doc.url.search('www.g2a.com'))
                return searchG2a.after(doc.seek, body) ? doc.found = true : false

            if (!doc.endlessSearch)
                return searchGeneric.after(doc.seek, body) ? doc.found = true : false

            var actuallyFound = searchGeneric.after(doc.seek, body)
            if (!doc.found && actuallyFound)
                return doc.found = true

            if (doc.found && !actuallyFound)
                return doc.found = false

            return false
        }
    }
}()
