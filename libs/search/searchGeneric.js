module.exports = function () {

    return {

        after : function (keyword, body) {

            var reg = new RegExp(keyword, "i")

            return ~body.search(reg) ? true : false
        }
    }
}()
